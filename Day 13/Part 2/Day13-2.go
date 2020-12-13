package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {
	busTimes := readLines("../input.txt")

	part2, step := 0, 1

	for i, bus := range busTimes {
		if bus == 0 {
			continue
		}
		for (part2+i)%bus != 0 {
			part2 += step
		}
		step *= bus
	}

	fmt.Println(part2)
}

func readLines(filename string) []int {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	lines := strings.Split(text, "\n")

	busTimeString := strings.Split(lines[1], ",")
	var busTimes []int
	for _, busTime := range busTimeString {
		busTimeInt, err := strconv.Atoi(busTime)
		if err != nil {
			busTimes = append(busTimes, 0)
			continue
		}
		busTimes = append(busTimes, busTimeInt)
	}
	return busTimes
}
