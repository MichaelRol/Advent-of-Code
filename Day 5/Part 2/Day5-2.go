package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"sort"
	"strings"
)

func main() {

	content, err := ioutil.ReadFile("../input.txt")
	if err != nil {
		log.Fatal(err)
	}

	text := string(content)
	seats := strings.Split(text, "\n")

	var seatIDs []int
	for _, seat := range seats {
		seatIDs = append(seatIDs, calcSeatID(calcSeatPos(seat)))
	}

	fmt.Println(findSeat(seatIDs))
}

func calcSeatPos(seatCode string) (int, int) {
	rowID := seatCode[:7]
	colID := seatCode[7:]

	row := 0
	col := 0

	for index, letter := range colID {

		if string(letter) == "R" {
			col = col + 1<<(len(colID)-index-1)
		}
	}

	for index, letter := range rowID {
		if string(letter) == "B" {
			row = row + 1<<(len(rowID)-index-1)
		}
	}

	return row, col
}

func calcSeatID(row int, col int) int {
	return row*8 + col
}

func findSeat(seats []int) int {
	sort.Ints(seats)
	for i, seat := range seats {
		if seats[i+1] != seat+1 {
			return seat + 1
		}
	}
	return 0
}
