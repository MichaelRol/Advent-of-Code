package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"
)

func main() {
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
	fmt.Println(count)
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
				for x := i - 1; x >= 0; x-- {
					if chairs[x][j] == 2 {
						count++
						break
					}
					if chairs[x][j] == 1 {
						break
					}
				}
				for x := i + 1; x < len(chairs); x++ {
					if chairs[x][j] == 2 {
						count++
						break
					}
					if chairs[x][j] == 1 {
						break
					}
				}
				for y := j - 1; y >= 0; y-- {
					if chairs[i][y] == 2 {
						count++
						break
					}
					if chairs[i][y] == 1 {
						break
					}
				}
				for y := j + 1; y < len(chairs[0]); y++ {
					if chairs[i][y] == 2 {
						count++
						break
					}
					if chairs[i][y] == 1 {
						break
					}
				}
				for x := 1; x < len(chairs); x++ {
					if i+x >= len(chairs) || j+x >= len(chairs[0]) {
						break
					}
					if chairs[i+x][j+x] == 2 {
						count++
						break
					}
					if chairs[i+x][j+x] == 1 {
						break
					}
				}
				for x := 1; x < len(chairs); x++ {
					if i-x < 0 || j-x < 0 {
						break
					}
					if chairs[i-x][j-x] == 2 {
						count++
						break
					}
					if chairs[i-x][j-x] == 1 {
						break
					}
				}
				for x := 1; x < len(chairs); x++ {
					if i+x >= len(chairs) || j-x < 0 {
						break
					}
					if chairs[i+x][j-x] == 2 {
						count++
						break
					}
					if chairs[i+x][j-x] == 1 {
						break
					}
				}
				for x := 1; x < len(chairs); x++ {
					if i-x < 0 || j+x >= len(chairs[0]) {
						break
					}
					if chairs[i-x][j+x] == 2 {
						count++
						break
					}
					if chairs[i-x][j+x] == 1 {
						break
					}
				}
				if chairs[i][j] == 1 && count == 0 {
					newChairs[i][j] = 2
				} else if chairs[i][j] == 2 && count >= 5 {
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
