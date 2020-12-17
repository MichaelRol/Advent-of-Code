package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"
)

func main() {
	inputGrid := readInput("../input.txt")
	size := 20
	grid := generateGrid(size)
	for x, line := range inputGrid {
		for y, active := range line {
			offsetX := (len(grid) - len(inputGrid)) / 2
			offsetY := (len(grid[0]) - len(line)) / 2
			grid[x+offsetX][y+offsetY][len(grid[0][0])/2][len(grid[0][0][0])/2] = active
		}
	}
	for cycle := 0; cycle < 6; cycle++ {
		grid = cycleGrid(grid, size)
	}
	fmt.Println(countActive(grid, size))
}

func countActive(grid [][][][]int, size int) int {
	count := 0
	for x := 0; x < size; x++ {
		for y := 0; y < size; y++ {
			for z := 0; z < size; z++ {
				for w := 0; w < size; w++ {
					if grid[x][y][z][w] == 1 {
						count++
					}
				}
			}
		}
	}
	return count
}
func cycleGrid(grid [][][][]int, size int) [][][][]int {
	newGrid := copyInt4d(grid)
	for x := 0; x < size; x++ {
		for y := 0; y < size; y++ {
			for z := 0; z < size; z++ {
				for w := 0; w < size; w++ {
					activeCount := activeNeighbours(grid, x, y, z, w, size)
					if grid[x][y][z][w] == 1 {
						if activeCount == 2 || activeCount == 3 {
							newGrid[x][y][z][w] = 1
						} else {
							newGrid[x][y][z][w] = 0
						}
					} else if grid[x][y][z][w] == 0 {
						if activeCount == 3 {
							newGrid[x][y][z][w] = 1
						} else {
							newGrid[x][y][z][w] = 0
						}
					}
				}
			}
		}
	}

	return newGrid
}

func activeNeighbours(grid [][][][]int, x, y, z, w, size int) int {
	activeCount := 0
	for i := -1; i <= 1; i++ {
		for j := -1; j <= 1; j++ {
			for k := -1; k <= 1; k++ {
				for l := -1; l <= 1; l++ {
					if i == 0 && j == 0 && k == 0 && l == 0 {
						continue
					}
					if x+i < 0 || x+i >= size || y+j < 0 || y+j >= size || z+k < 0 || z+k >= size || w+l < 0 || w+l >= size {
						continue
					}
					if grid[x+i][y+j][z+k][w+l] == 1 {
						activeCount++
					}
				}
			}
		}
	}
	return activeCount
}

func copyInt4d(src [][][][]int) [][][][]int {
	dst := make([][][][]int, len(src))
	for i := range src {
		dst[i] = copyInt3d(src[i])
	}
	return dst
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

func generateGrid(size int) [][][][]int {
	var grid = make([][][][]int, size) // x axis
	for x := 0; x < size; x++ {
		grid[x] = make([][][]int, size) // y axis
		for y := 0; y < size; y++ {
			grid[x][y] = make([][]int, size) // z axis
			for z := 0; z < size; z++ {
				grid[x][y][z] = make([]int, size) // w axis
				for w := 0; w < size; w++ {
					grid[x][y][z][w] = 0
				}
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
