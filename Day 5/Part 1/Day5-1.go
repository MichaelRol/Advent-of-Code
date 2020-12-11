package main

import (
	"fmt"
	"io/ioutil"
	"log"
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

	fmt.Println(getMax(seatIDs))
}

func calcSeatPos(seatCode string) (row, col int) {
	rowID := seatCode[:7]
	colID := seatCode[7:]

	row = 0
	col = 0

	for index, letter := range colID {
		if string(letter) == "R" {
			col += 1 << (len(colID) - index - 1)
		}
	}

	for index, letter := range rowID {
		if string(letter) == "B" {
			row += 1 << (len(rowID) - index - 1)
		}
	}

	return row, col
}

func calcSeatID(row, col int) int {
	return row*8 + col
}

func getMax(values []int) int {
	max := values[0]
	for _, value := range values {
		if value > max {
			max = value
		}
	}
	return max
}
