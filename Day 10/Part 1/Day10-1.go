package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"sort"
	"strconv"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	ratings := readNumbers("../input.txt")

	// Sort input numbers, which are "joltage" ratings
	sort.Ints(ratings)
	// Add your in-built adapter which is 3 jolts higher than your highest adapter
	ratings = append(ratings, ratings[len(ratings)-1]+3)

	oneJumps := 0
	threeJumps := 0
	if ratings[0] == 1 {
		oneJumps++
	} else if ratings[0] == 3 {
		threeJumps++
	}

	// Count the number of 1-jolt and 3-jolt jumps between adapters
	for i := 1; i < len(ratings); i++ {
		if ratings[i]-ratings[i-1] == 1 {
			oneJumps++
		} else if ratings[i]-ratings[i-1] == 3 {
			threeJumps++
		}
	}
	fmt.Print("Answer: ")
	fmt.Println(oneJumps * threeJumps)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func readNumbers(filename string) []int {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	lines := strings.Split(text, "\n")

	var numbers []int
	for _, line := range lines {
		if line == "" {
			continue
		}
		i, err := strconv.Atoi(line)
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}
		numbers = append(numbers, i)
	}

	return numbers
}
