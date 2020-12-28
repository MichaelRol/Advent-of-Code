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
	lines := readInput("../input.txt")

	// Runs through input moving along different amounts with each run
	// Multiply number of trees encountered with each run
	total := 1
	for j := 1; j <= 7; j += 2 {
		jump := j
		index := 0
		trees := 0
		for _, line := range lines {
			if line == "" {
				continue
			}
			if string(line[index]) == "#" {
				trees++
			}
			index = incIndex(index, jump)
		}
		total *= trees
	}
	index := 0
	trees := 0
	// Finally moves down 2 lines and 1 across each time
	for i := 0; i < len(lines); i += 2 {
		if string(lines[i][index]) == "#" {
			trees++
		}
		index = incIndex(index, 1)
	}
	total *= trees

	fmt.Print("Answer: ")
	fmt.Println(total)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func incIndex(index, jump int) int {
	index = (index + jump) % 31
	return index
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
