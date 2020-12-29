package main

import (
	"fmt"
	"io/ioutil"
	"strconv"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	lines := readInput("../input.txt")
	rules := processRules(lines)

	var numAndName []string
	numAndName = append(numAndName, "1", "shiny gold bag")

	fmt.Print("Answer: ")
	fmt.Println(countBags(numAndName, rules) - 1)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

// Recursively count bags
func countBags(bag []string, rules map[string][][]string) int {
	total := 0
	if bag[0] == "0" {
		return 0
	}

	for _, nextBag := range rules[bag[1]] {
		i, err := strconv.Atoi(nextBag[0])
		if err != nil {
			panic(err)
		}
		total += i * countBags(nextBag, rules)
	}

	return 1 + total
}

func processRules(lines []string) map[string][][]string {
	rules := make(map[string][][]string)

	for _, line := range lines {
		both := strings.Split(line, " contain ")
		contains := strings.Split(both[1], ", ")
		for _, colour := range contains {
			if colour[len(colour)-1] == '.' {
				colour = colour[:len(colour)-1]
			}
			if colour[len(colour)-1] == 's' {
				colour = colour[:len(colour)-1]
			}
			var numAndName []string
			if colour[:1] == "n" {
				numAndName = append(numAndName, "0", "no other bags")
			} else {
				numAndName = append(numAndName, colour[:1], colour[2:])
			}

			rules[both[0][:len(both[0])-1]] = append(rules[both[0][:len(both[0])-1]], numAndName)
		}
	}
	return rules
}

func readInput(filename string) []string {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		panic(err)
	}
	text := string(content)
	lines := strings.Split(text, "\n")

	return lines
}
