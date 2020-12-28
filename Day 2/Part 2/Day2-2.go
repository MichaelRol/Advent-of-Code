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
	var count int = 0
	passwords := readInput("../input.txt")

	for _, password := range passwords {
		if password == "" {
			continue
		}
		if isPassValid(password) {
			count++
		}
	}

	fmt.Print("Answer: ")
	fmt.Println(count)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func isPassValid(fullString string) bool {
	// splits input line into a password, a letter and two positions a and b
	var splitString []string = strings.Split(fullString, ": ")
	var pass = splitString[1]

	splitString = strings.Split(splitString[0], " ")
	var letter = splitString[1]

	splitString = strings.Split(splitString[0], "-")
	var a, err1 = strconv.Atoi(splitString[0])
	var b, err2 = strconv.Atoi(splitString[1])

	if err1 != nil {
		fmt.Println(err1)
		os.Exit(2)
	}

	if err2 != nil {
		fmt.Println(err2)
		os.Exit(2)
	}

	// checks if the letter is in position a or b, but not both
	if (string(pass[a-1]) == letter && string(pass[b-1]) != letter) || (string(pass[a-1]) != letter && string(pass[b-1]) == letter) {
		return true
	}
	return false
}

func readInput(filename string) []string {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}
	text := string(content)
	lines := strings.Split(text, "\n")

	return lines
}
