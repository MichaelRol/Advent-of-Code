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
	passwords := readInput("../input.txt")

	count := 0

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
	// Splits each line of input into password, a letter and min/max values.
	var splitString []string = strings.Split(fullString, ": ")
	var password = splitString[1]

	splitString = strings.Split(splitString[0], " ")
	var letter = splitString[1]

	splitString = strings.Split(splitString[0], "-")
	var min, err1 = strconv.Atoi(splitString[0])
	var max, err2 = strconv.Atoi(splitString[1])

	if err1 != nil {
		fmt.Println(err1)
		os.Exit(2)
	}

	if err2 != nil {
		fmt.Println(err2)
		os.Exit(2)
	}

	// count how many times the letter appears in password
	letterCount := strings.Count(password, letter)

	// is the number of times the letter appears in the password between the min and max values
	if letterCount >= min && letterCount <= max {
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
