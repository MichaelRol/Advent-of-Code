// Processes each instruction which can move boat a fixed amount north, east, south or west.
// It can also turn the boat by a number of degrees or move the boat forwards in the direction
// it is facing.
package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"math"
	"os"
	"strconv"
	"strings"
	"time"
)

type instruct struct {
	action string
	value  int
}

func main() {
	start := time.Now()
	instructions := readLines("../input.txt")
	direction := 90
	north := 0
	east := 0

	for _, instruction := range instructions {
		direction, north, east = processInstruct(instruction, direction, north, east)
	}
	fmt.Print("Answer: ")
	fmt.Println(manhattenDist(north, east))
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func manhattenDist(north, east int) float64 {
	return math.Abs(float64(north)) + math.Abs(float64(east))
}

func processInstruct(instruction instruct, direction, north, east int) (newDirection, newNorth, newEast int) {
	switch instruction.action {
	case "N":
		north += instruction.value
	case "S":
		north -= instruction.value
	case "E":
		east += instruction.value
	case "W":
		east -= instruction.value
	case "R":
		direction = (direction + instruction.value) % 360
	case "L":
		direction = ((direction-instruction.value)%360 + 360) % 360
	case "F":
		if direction == 0 {
			north += instruction.value
		} else if direction == 90 {
			east += instruction.value
		} else if direction == 180 {
			north -= instruction.value
		} else if direction == 270 {
			east -= instruction.value
		} else {
			fmt.Println("Invalid Direction")
			fmt.Println(direction)
			os.Exit(2)
		}
	default:
		fmt.Println("Invalid Action")
		os.Exit(2)
	}

	return direction, north, east
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
