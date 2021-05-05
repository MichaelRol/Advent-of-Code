package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strconv"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	nums := readInput("../input.txt")

	// Reads through list of numbers, find two that sum to 2020
	for i := 0; i < len(nums); i++ {
		for j := 0; j < len(nums); j++ {
			if i == j {
				continue
			}
			if nums[i]+nums[j] == 2020 {
				fmt.Print("Answer: ")
				fmt.Println(nums[i] * nums[j])
				t := time.Now()
				elapsed := t.Sub(start)
				fmt.Print("Execution time: ")
				fmt.Println(elapsed)
				return
			}
		}
	}
}

func readInput(filename string) []int {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}
	text := string(content)
	lines := strings.Split(text, "\n")

	var numbers []int

	for _, line := range lines {
		num, err := strconv.Atoi(line)
		if err != nil {
			continue
		}
		numbers = append(numbers, num)
	}

	return numbers
}
