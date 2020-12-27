package main

import (
	"fmt"
	"time"
)

func main() {
	start := time.Now()
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
			fmt.Print("Answer: ")
			fmt.Println(nextNumber)
			t := time.Now()
			elapsed := t.Sub(start)
			fmt.Print("Execution time: ")
			fmt.Println(elapsed)
			break
		}
	}
}
