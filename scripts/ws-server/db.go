package main

import (
	"database/sql"
	"log"
	"time"

	_ "github.com/go-sql-driver/mysql"
)

func dbConn() *sql.DB {
	db, err := sql.Open("mysql", "root:root_password@/roadies_db")
	if err != nil {
		panic(err)
	}
	db.SetConnMaxLifetime(time.Hour * 1)
	db.SetMaxOpenConns(10)
	db.SetMaxIdleConns(10)
	return db
}

func addUserLoc(db *sql.DB, loc Location) {
	res, err := db.Exec("INSERT INTO location(latitude,longitude,user_id,time) VALUES(?,?,?,?)", loc.lat, loc.long, loc.userId, time.Now())
	log.Println("Inserting location: ", res)
	if err != nil {
		log.Fatal(err)
	}
}

func getSquadsCurLoc(db *sql.DB, squadId int64) []Location {
	res, err := db.Query(`
	SELECT us.user_id ,l.latitude, l.longitude
	FROM location l
	JOIN user_squad us ON l.user_id = us.user_id
	WHERE us.squad_id = ?
  AND l.time = (
		SELECT MAX(time)
		FROM location
		WHERE user_id = l.user_id
  );
	`, squadId)
	log.Println("Getting current users location")
	if err != nil {
		log.Fatal("Error fetching info: ", err)
	}
	locations := make([]Location, 0, 10)
	for res.Next() {
		loc := new(Location)
		res.Scan(&loc.userId, &loc.lat, &loc.long)
		locations = append(locations, *loc)
	}
	return locations
}
