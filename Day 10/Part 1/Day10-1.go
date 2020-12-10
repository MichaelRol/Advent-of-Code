package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"sort"
	"strconv"
	"strings"
)

func main() {
	ratings := readNumbers("../input.txt")

	sort.Ints(ratings)
	ratings = append(ratings, ratings[len(ratings)-1]+3)

	oneJumps := 0
	threeJumps := 0
	if ratings[0] == 1 {
		oneJumps++
	} else if ratings[0] == 3 {
		threeJumps++
	}

	for i := 1; i < len(ratings); i++ {
		if ratings[i]-ratings[i-1] == 1 {
			oneJumps++
		} else if ratings[i]-ratings[i-1] == 3 {
			threeJumps++
		}
	}
	fmt.Println(oneJumps * threeJumps)
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
