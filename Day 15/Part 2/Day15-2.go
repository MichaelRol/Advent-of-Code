package main

import (
	"fmt"
)

func main() {
	input := []int{8, 11, 0, 19, 1, 2}
	lastSpoke := make(map[int]int)
	for turn, number := range input {
		if turn == len(input)-1 {
			break
		}
		lastSpoke[number] = turn
	}
	lastNumber := input[len(input)-1]

	for turn := len(input); ; turn++ {
		i, spoke := lastSpoke[lastNumber]
		if !spoke {
			lastSpoke[lastNumber] = turn - 1
			lastNumber = 0
		} else {
			difference := (turn - 1) - i
			lastSpoke[lastNumber] = turn - 1
			lastNumber = difference
		}

		if turn == 30000000-1 {
			fmt.Println(lastNumber)
			break
		}
	}
}
