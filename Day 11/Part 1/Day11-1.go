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
	chairs := readLines("../input.txt")

	newChairs := evolveSystem(chairs)

	for !match(chairs, newChairs) {
		chairs = newChairs
		newChairs = evolveSystem(chairs)
	}
	count := 0
	for _, line := range chairs {
		for _, num := range line {
			if num == 2 {
				count++
			}
		}
	}
	fmt.Print("Answer: ")
	fmt.Println(count)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func match(chairs, newChairs [][]int) bool {
	for i := range chairs {
		for j := range chairs[0] {
			if chairs[i][j] != newChairs[i][j] {
				return false
			}
		}
	}
	return true
}

func evolveSystem(chairs [][]int) [][]int {
	var newChairs [][]int
	for _, line := range chairs {
		newLine := make([]int, len(line))
		copy(newLine, line)
		newChairs = append(newChairs, newLine)
	}
	for i := 0; i < len(chairs); i++ {
		for j := 0; j < len(chairs[0]); j++ {
			if chairs[i][j] == 0 {
				newChairs[i][j] = 0
			} else {
				count := 0
				for x := -1; x <= 1; x++ {
					for y := -1; y <= 1; y++ {
						if (x == 0 && y == 0) || i+x < 0 || j+y < 0 || i+x >= len(chairs) || j+y >= len(chairs[0]) {
							continue
						}
						if chairs[i+x][j+y] == 2 {
							count++
						}
					}
				}
				if chairs[i][j] == 1 && count == 0 {
					newChairs[i][j] = 2
				} else if chairs[i][j] == 2 && count >= 4 {
					newChairs[i][j] = 1
				}
			}
		}
	}
	return newChairs
}

func readLines(filename string) [][]int {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	lines := strings.Split(text, "\n")
	chairs := make([][]int, len(lines))

	for i := 0; i < len(lines); i++ {
		for j := 0; j < len(lines[0]); j++ {
			if string(lines[i][j]) == "." {
				chairs[i] = append(chairs[i], 0)
			} else if string(lines[i][j]) == "L" {
				chairs[i] = append(chairs[i], 1)
			} else if string(lines[i][j]) == "#" {
				chairs[i] = append(chairs[i], 2)
			}
		}
	}
	return chairs
}
