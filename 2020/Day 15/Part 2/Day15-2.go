// Identical premise to Part 1 except result is the 30000000th number spoken.
package main

import (
	"fmt"
	"time"
)

func main() {
	start := time.Now()
	input := []int{8, 11, 0, 19, 1, 2}
	lastSpoke := make(map[int]int)
	for turn, number := range input {
		if turn == len(input)-1 {
			break
		}
		lastSpoke[number] = turn
	}
	lastNumber := input[len(input)-1]

	// Instead of looking backwards, keep track of the turn number when each number is spoken
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
			fmt.Print("Answer: ")
			fmt.Println(lastNumber)
			t := time.Now()
			elapsed := t.Sub(start)
			fmt.Print("Execution time: ")
			fmt.Println(elapsed)
			break
		}
	}
}
