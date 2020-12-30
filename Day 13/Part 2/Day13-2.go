// Task is to find a time such that the first bus in the input departs at that time
// and then each bus in the input then departs at each subsequent minute depending on
// its position in the input, i.e. first bus leaves at t, 2nd at t+1, 3rd at t+2 etc.
// If input bus is * it means that minute is skipped.
package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strconv"
	"strings"
	"time"
)

func main() {
	start := time.Now()
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
	fmt.Print("Answer: ")
	fmt.Println(part2)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
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
