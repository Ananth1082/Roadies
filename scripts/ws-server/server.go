package main

import (
	"encoding/json"
	"errors"
	"fmt"
	"io"
	"log"
	"strconv"
	"strings"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"golang.org/x/net/websocket"
)

var db = dbConn()

type Location struct {
	UserId int64
	Lat    float64
	Long   float64
}

func UnMarshalLocation(data string) (*Location, error) {
	var err error
	tokens := strings.Split(data, ",")
	if len(tokens) != 3 {
		return nil, errors.New("Invalid data format")
	}

	loc := new(Location)
	loc.UserId, err = strconv.ParseInt(tokens[0], 10, 64)
	if err != nil {
		return nil, err
	}
	loc.Lat, err = strconv.ParseFloat(tokens[1], 64)
	if err != nil {
		return nil, err
	}
	loc.Long, err = strconv.ParseFloat(tokens[2], 64)
	if err != nil {
		return nil, err
	}
	return loc, nil
}

// func MarshalLocation(loc Location) string {
// 	return fmt.Sprintf("%d,%f,%f\n", loc.UserId, loc.Lat, loc.Long)
// }

// func locstoString(locs []Location) string {
// 	data := ""
// 	for _, loc := range locs {
// 		data += MarshalLocation(loc)
// 	}
// 	return data
// }

func locationHandler(c echo.Context) error {
	websocket.Handler(func(ws *websocket.Conn) {
		defer ws.Close()
		for {
			msg := ""
			err := websocket.Message.Receive(ws, &msg)
			if errors.Is(err, io.EOF) {
				c.Logger().Error(err)
				return
			}
			if err != nil {
				c.Logger().Error(err)
			}
			if msg == "BYE" {
				return
			} else {
				tokens := strings.SplitN(msg, ",", 2)
				data, _ := UnMarshalLocation(tokens[1])
				addUserLoc(db, *data)
				sid, _ := strconv.ParseInt(tokens[0], 10, 64)
				locs := getSquadsCurLoc(db, sid)
				log.Println("Locs: ", locs)
				ljson, err := json.Marshal(locs)
				if err != nil {
					log.Fatal(err)
				}
				fmt.Println("Locations :", ljson)
				websocket.Message.Send(ws, ljson)
			}
		}
	}).ServeHTTP(c.Response(), c.Request())
	return nil
}

func RestLocationHandler(c echo.Context) error {

	var req struct {
		SquadId int64   `json:"squad_id"`
		UserId  int64   `json:"user_id"`
		Lat     float64 `json:"lat"`
		Long    float64 `json:"long"`
	}

	err := c.Bind(&req)
	if err != nil {
		return err
	}

	addUserLoc(db, Location{UserId: req.UserId, Lat: req.Lat, Long: req.Long})
	locs := getSquadsCurLoc(db, req.SquadId)
	return c.JSON(200, locs)
}

func main() {
	e := echo.New()
	e.Debug = true
	e.Use(middleware.CORS())
	e.Use(middleware.Logger())
	e.Use(middleware.Recover())
	e.Static("/", "./public")
	e.GET("/ws", locationHandler)
	e.POST("/rest/location", RestLocationHandler)
	e.Logger.Fatal(e.Start(":8081"))
}
