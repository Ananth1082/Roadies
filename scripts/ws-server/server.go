package main

import (
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
	userId int64
	lat    float64
	long   float64
}

func UnMarshalLocation(data string) (*Location, error) {
	var err error
	tokens := strings.Split(data, ",")
	if len(tokens) != 3 {
		return nil, errors.New("Invalid data format")
	}

	loc := new(Location)
	loc.userId, err = strconv.ParseInt(tokens[0], 10, 64)
	if err != nil {
		return nil, err
	}
	loc.lat, err = strconv.ParseFloat(tokens[1], 64)
	if err != nil {
		return nil, err
	}
	loc.long, err = strconv.ParseFloat(tokens[2], 64)
	if err != nil {
		return nil, err
	}
	return loc, nil
}

func MarshalLocation(loc Location) string {
	return fmt.Sprintf("%d,%f,%f\n", loc.userId, loc.lat, loc.long)
}

func locstoString(locs []Location) string {
	data := ""
	for _, loc := range locs {
		data += MarshalLocation(loc)
	}
	return data
}

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
			} else if strings.HasPrefix(msg, "GET ") {
				sid, err := strconv.ParseInt(strings.Split(msg, " ")[1], 10, 64)
				if err != nil {
					log.Fatal(err)
					return
				}
				locs := getSquadsCurLoc(db, sid)
				websocket.Message.Send(ws, locstoString(locs))
			} else if strings.HasPrefix(msg, "POST ") {
				data := strings.Split(msg, " ")[1]
				loc, _ := UnMarshalLocation(data)
				addUserLoc(db, *loc)
			} else {
				websocket.Message.Send(ws, "Invalid Command")
			}
		}
	}).ServeHTTP(c.Response(), c.Request())
	return nil
}

func main() {
	e := echo.New()
	e.Debug = true
	e.Use(middleware.Logger())
	e.Use(middleware.Recover())
	e.Static("/", "./public")
	e.GET("/ws", locationHandler)
	e.Logger.Fatal(e.Start(":8080"))
}
