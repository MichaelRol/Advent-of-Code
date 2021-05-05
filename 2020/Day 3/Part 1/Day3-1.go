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
	index := 0
	jump := 3
	trees := 0

	lines := readInput("../input.txt")

	// Input is grid of trees, moving 1 down 3 across each time, how many trees do you encounter.
	for _, line := range lines {
		if line == "" {
			continue
		}
		if string(line[index]) == "#" {
			trees++
		}
		index = incIndex(index, jump)
	}
	fmt.Print("Answer: ")
	fmt.Println(trees)
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
