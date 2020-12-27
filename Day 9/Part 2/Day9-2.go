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
	numbers := readNumbers("../input.txt")

	var invalid int
	var position int
	preamble := 25

	for i := preamble; i < len(numbers); i++ {
		if !isValid(numbers[i-preamble:i], numbers[i]) {
			invalid = numbers[i]
			position = i
			break
		}
	}

	contiguousList := findContiguousSum(invalid, position, numbers)
	if contiguousList != nil {
		fmt.Print("Answer: ")
		fmt.Println(max(contiguousList) + min(contiguousList))
		t := time.Now()
		elapsed := t.Sub(start)
		fmt.Print("Execution time: ")
		fmt.Println(elapsed)
	} else {
		fmt.Println("No list found")
		os.Exit(2)
	}
}

func findContiguousSum(target, position int, numbers []int) []int {
	for i := 0; i < position; i++ {
		tally := numbers[i]
		j := 1
		contig := []int{numbers[i]}
		for tally < target {
			tally += numbers[i+j]
			contig = append(contig, numbers[i+j])
			if tally == target {
				return contig
			}
			j++
		}
	}
	return nil
}

func min(v []int) int {
	m := 0
	for i, e := range v {
		if i == 0 || e < m {
			m = e
		}
	}
	return m
}

func max(v []int) int {
	m := 0
	for i, e := range v {
		if i == 0 || e > m {
			m = e
		}
	}
	return m
}

func isValid(numbers []int, value int) bool {
	for _, i := range numbers {
		for _, j := range numbers {
			if i == j {
				continue
			} else if i+j == value {
				return true
			}
		}
	}
	return false
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
