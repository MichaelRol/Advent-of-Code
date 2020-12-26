package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"time"
)

func main() {
	start := time.Now()
	index := 0
	jump := 3
	trees := 0

	file, err := os.Open("../input.txt")

	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var lines []string

	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		fmt.Println(err)
	}

	for i := 0; i < len(lines); i++ {
		if string(lines[i][index]) == "#" {
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
