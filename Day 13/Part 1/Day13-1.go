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
	myTime, busTimes := readLines("../input.txt")

	shortestWait := 1000000
	var busID int
	for _, busTime := range busTimes {
		x := busTime - (myTime % busTime)
		if x < shortestWait {
			shortestWait = x
			busID = busTime
		}
	}

	fmt.Println(busID * shortestWait)
}

func readLines(filename string) (time int, busTimes []int) {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	lines := strings.Split(text, "\n")

	time, err = strconv.Atoi(lines[0])
	if err != nil {
		fmt.Println("Invald time")
		os.Exit(2)
	}

	busTimeString := strings.Split(lines[1], ",")

	for _, busTime := range busTimeString {
		busTimeInt, err := strconv.Atoi(busTime)
		if err != nil {
			continue
		}
		busTimes = append(busTimes, busTimeInt)
	}
	return time, busTimes
}
