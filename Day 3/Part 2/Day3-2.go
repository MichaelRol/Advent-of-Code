package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

func main() {

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

	total := 1
	for j := 1; j <= 7; j += 2 {
		jump := j
		index := 0
		trees := 0
		for i := 0; i < len(lines); i++ {
			if string(lines[i][index]) == "#" {
				trees++
			}
			index = incIndex(index, jump)
		}
		total *= trees
	}
	index := 0
	trees := 0
	for i := 0; i < len(lines); i += 2 {
		if string(lines[i][index]) == "#" {
			trees++
		}
		index = incIndex(index, 1)
	}
	total *= trees

	fmt.Println(total)

}

func incIndex(index int, jump int) int {
	index = (index + jump) % 31
	return index
}
