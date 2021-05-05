package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	valid := 0

	// Each section of input is a passport made up of multiple fields
	lines := readInput("../input.txt")

	// For each passport check if there are the correct number of fields
	// In this case it is 8 or 7 as cid is option.
	for _, line := range lines {
		data := strings.Fields(line)
		for j := 0; j < len(data); j++ {
			data[j] = data[j][0:3]
		}

		if len(data) == 8 {
			valid++
		}
		if len(data) == 7 {
			if !Contains(data, "cid") {
				valid++
			}
		}
	}
	fmt.Print("Answer: ")
	fmt.Println(valid)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func Contains(a []string, x string) bool {
	for _, n := range a {
		if x == n {
			return true
		}
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
	lines := strings.Split(text, "\n\n")

	return lines
}
