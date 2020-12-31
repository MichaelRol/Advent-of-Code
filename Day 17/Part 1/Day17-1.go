// Basically a 3-D expanding game of life
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
	inputGrid := readInput("../input.txt")
	size := 20
	grid := generateGrid(size)
	// Place input in the middle of grid
	for x, line := range inputGrid {
		for y, active := range line {
			offsetX := (len(grid) - len(inputGrid)) / 2
			offsetY := (len(grid[0]) - len(line)) / 2
			grid[x+offsetX][y+offsetY][len(grid[0][0])/2] = active
		}
	}
	for cycle := 0; cycle < 6; cycle++ {
		grid = cycleGrid(grid, size)
	}
	fmt.Print("Answer: ")
	fmt.Println(countActive(grid, size))
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func countActive(grid [][][]int, size int) int {
	count := 0
	for x := 0; x < size; x++ {
		for y := 0; y < size; y++ {
			for z := 0; z < size; z++ {
				if grid[x][y][z] == 1 {
					count++
				}
			}
		}
	}
	return count
}
func cycleGrid(grid [][][]int, size int) [][][]int {
	newGrid := copyInt3d(grid)
	for x := 0; x < size; x++ {
		for y := 0; y < size; y++ {
			for z := 0; z < size; z++ {
				activeCount := activeNeighbours(grid, x, y, z, size)
				if grid[x][y][z] == 1 {
					if activeCount == 2 || activeCount == 3 {
						newGrid[x][y][z] = 1
					} else {
						newGrid[x][y][z] = 0
					}
				} else if grid[x][y][z] == 0 {
					if activeCount == 3 {
						newGrid[x][y][z] = 1
					} else {
						newGrid[x][y][z] = 0
					}
				}
			}
		}
	}

	return newGrid
}

func activeNeighbours(grid [][][]int, x, y, z, size int) int {
	activeCount := 0
	for i := -1; i <= 1; i++ {
		for j := -1; j <= 1; j++ {
			for k := -1; k <= 1; k++ {
				if i == 0 && j == 0 && k == 0 {
					continue
				}
				if x+i < 0 || x+i >= size || y+j < 0 || y+j >= size || z+k < 0 || z+k >= size {
					continue
				}
				if grid[x+i][y+j][z+k] == 1 {
					activeCount++
				}
			}
		}
	}
	return activeCount
}

func copyInt3d(src [][][]int) [][][]int {
	dst := make([][][]int, len(src))
	for i := range src {
		dst[i] = copyInt2d(src[i])
	}
	return dst
}

func copyInt2d(src [][]int) [][]int {
	dst := make([][]int, len(src))
	for i := range src {
		dst[i] = copyInt1d(src[i])
	}
	return dst
}

func copyInt1d(src []int) []int {
	dst := make([]int, len(src))
	copy(dst, src)
	return dst
}

func generateGrid(size int) [][][]int {
	var grid = make([][][]int, size) // x axis
	for x := 0; x < size; x++ {
		grid[x] = make([][]int, size) // y axis
		for y := 0; y < size; y++ {
			grid[x][y] = make([]int, size) // z axis
			for z := 0; z < size; z++ {
				grid[x][y][z] = 0
			}
		}
	}
	return grid
}

func readInput(filename string) [][]int {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	lines := strings.Split(text, "\n")
	var inputGrid [][]int
	for _, line := range lines {
		if line == "" {
			continue
		}
		var lineOfChars []int
		for _, char := range line {
			if string(char) == "." {
				lineOfChars = append(lineOfChars, 0)
			} else if string(char) == "#" {
				lineOfChars = append(lineOfChars, 1)
			} else {
				fmt.Println("Invalid character")
				os.Exit(2)
			}
		}
		inputGrid = append(inputGrid, lineOfChars)
	}
	return inputGrid
}
