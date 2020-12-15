package main

import (
	"fmt"
)

func main() {
	turns := []int{8, 11, 0, 19, 1, 2}

	for turn := len(turns); ; turn++ {
		lastNumber := turns[turn-1]
		nextNumber := 0
		for i := turn - 2; i >= 0; i-- {
			if turns[i] == lastNumber {
				nextNumber = (turn - 1) - i
				break
			}
		}
		turns = append(turns, nextNumber)
		if turn == 2019 {
			fmt.Println(nextNumber)
			break
		}
	}
}
