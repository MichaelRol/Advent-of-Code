package main

import (
	"fmt"
	"io/ioutil"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	seats := readInput("../input.txt")

	var seatIDs []int
	for _, seat := range seats {
		seatIDs = append(seatIDs, calcSeatID(calcSeatPos(seat)))
	}

	fmt.Print("Answer: ")
	fmt.Println(getMax(seatIDs))
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

// Seat positions calculated by binary space partioning
// Front/Back is 0/1 and Left/Right is 0/1
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

func readInput(filename string) []string {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		panic(err)
	}
	text := string(content)
	lines := strings.Split(text, "\n")

	return lines
}
