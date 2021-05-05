// A simple game where 6 starting numbers are spoken and then each turn the
// most recently spoken number is considered to determine the next number.
// If the number has never been spoken before, 0 is the next number, else
// the next number is the number of turns since the number was spoken before
// the last turn, and the last turn itself.
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
		// Look backwards to see if number has been spoken before
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
