package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"math"
	"os"
	"strconv"
	"strings"
)

type instruct struct {
	action string
	value  int
}

func main() {
	instructions := readLines("../input.txt")
	sNorth := 0
	sEast := 0
	wNorth := 1
	wEast := 10

	for _, instruction := range instructions {
		sNorth, sEast, wNorth, wEast = processInstruct(instruction, sNorth, sEast, wNorth, wEast)
	}
	fmt.Println(manhattenDist(sNorth, sEast))
}

func manhattenDist(north, east int) float64 {
	return math.Abs(float64(north)) + math.Abs(float64(east))
}

func processInstruct(instruction instruct, sNorth, sEast, wNorth, wEast int) (newSNorth, newSEast, newWNorth, newWEast int) {
	switch instruction.action {
	case "N":
		wNorth += instruction.value
	case "S":
		wNorth -= instruction.value
	case "E":
		wEast += instruction.value
	case "W":
		wEast -= instruction.value
	case "R":
		if instruction.value == 90 {
			newWNorth = -wEast
			wEast = wNorth
			wNorth = newWNorth
		} else if instruction.value == 180 {
			wNorth = -wNorth
			wEast = -wEast
		} else if instruction.value == 270 {
			newWNorth = wEast
			wEast = -wNorth
			wNorth = newWNorth
		} else {
			fmt.Println("Rotation Invalid")
			os.Exit(2)
		}
	case "L":
		if instruction.value == 90 {
			newWNorth = wEast
			wEast = -wNorth
			wNorth = newWNorth
		} else if instruction.value == 180 {
			wNorth = -wNorth
			wEast = -wEast
		} else if instruction.value == 270 {
			newWNorth = -wEast
			wEast = wNorth
			wNorth = newWNorth
		} else {
			fmt.Println("Rotation Invalid")
			os.Exit(2)
		}
	case "F":
		sNorth += wNorth * instruction.value
		sEast += wEast * instruction.value
	default:
		fmt.Println("Invalid Action")
		os.Exit(2)
	}

	return sNorth, sEast, wNorth, wEast
}

func readLines(filename string) []instruct {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	lines := strings.Split(text, "\n")

	var instructions []instruct

	for _, line := range lines {
		number, err := strconv.Atoi(line[1:])
		if err != nil {
			fmt.Println(err)
			continue
		}
		instructions = append(instructions, instruct{action: line[:1], value: number})
	}

	return instructions
}
