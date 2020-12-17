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
	xs, ys, zs, ws := 20, 20, 20, 20
	grid := generateGrid(xs, ys, zs, ws)
	for x, line := range inputGrid {
		for y, active := range line {
			offsetX := (len(grid) - len(inputGrid)) / 2
			offsetY := (len(grid[0]) - len(line)) / 2
			grid[x+offsetX][y+offsetY][len(grid[0][0])/2][len(grid[0][0][0])/2] = active
		}
	}
	for cycle := 0; cycle < 6; cycle++ {
		grid = cycleGrid(grid, xs, ys, zs, ws)
	}
	fmt.Println(countActive(grid, xs, ys, zs, ws))
}

func countActive(grid [][][][]int, xs, ys, zs, ws int) int {
	count := 0
	for x := 0; x < xs; x++ {
		for y := 0; y < ys; y++ {
			for z := 0; z < zs; z++ {
				for w := 0; w < ws; w++ {
					if grid[x][y][z][w] == 1 {
						count++
					}
				}
			}
		}
	}
	return count
}
func cycleGrid(grid [][][][]int, xs, ys, zs, ws int) [][][][]int {
	newGrid := copyInt4d(grid)
	for x := 0; x < xs; x++ {
		for y := 0; y < ys; y++ {
			for z := 0; z < zs; z++ {
				for w := 0; w < ws; w++ {
					activeCount := 0
					for i := -1; i <= 1; i++ {
						for j := -1; j <= 1; j++ {
							for k := -1; k <= 1; k++ {
								for l := -1; l <= 1; l++ {
									if i == 0 && j == 0 && k == 0 && l == 0 {
										continue
									}
									if x+i < 0 || x+i >= xs || y+j < 0 || y+j >= ys || z+k < 0 || z+k >= zs || w+l < 0 || w+l >= ws {
										continue
									}
									if grid[x+i][y+j][z+k][w+l] == 1 {
										activeCount++
									}
								}
							}
						}
					}
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

func generateGrid(xs, ys, zs, ws int) [][][][]int {
	var grid = make([][][][]int, xs) // x axis
	for x := 0; x < xs; x++ {
		grid[x] = make([][][]int, ys) // y axis
		for y := 0; y < ys; y++ {
			grid[x][y] = make([][]int, zs) // z axis
			for z := 0; z < zs; z++ {
				grid[x][y][z] = make([]int, ws) // w axis
				for w := 0; w < ws; w++ {
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
