package main

import (
	"fmt"
	"io/ioutil"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	lines := readInput("../input.txt")

	goldIn := make(map[string]bool)
	goldIn["shiny gold bag"] = true

	rules := processRules(lines)

	// For each type of bag recursively look inside to see if it contains a gold bag
	for bag := range rules {
		if lookInside(bag, rules, goldIn) {
			goldIn[bag] = true
		}
	}
	fmt.Print("Answer: ")
	fmt.Println(len(goldIn) - 1)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func lookInside(bag string, rules map[string][]string, goldIn map[string]bool) bool {
	if goldIn[bag] {
		return true
	}
	for _, eachBag := range rules[bag] {
		if lookInside(eachBag, rules, goldIn) {
			goldIn[eachBag] = true
			return true
		}
	}
	return false
}

func processRules(lines []string) map[string][]string {
	rules := make(map[string][]string)

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
			rules[both[0][:len(both[0])-1]] = append(rules[both[0][:len(both[0])-1]], colour[2:])
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
