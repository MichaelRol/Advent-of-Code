package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"strings"
)

func main() {
	content, err := ioutil.ReadFile("../input.txt")
	if err != nil {
		log.Fatal(err)
	}

	text := string(content)
	lines := strings.Split(text, "\n")

	rules := make(map[string][]string)
	goldIn := make(map[string]bool)
	goldIn["shiny gold bag"] = true

	for _, line := range lines {
		both := strings.Split(line, " contain ")
		contains := strings.Split(both[1], ", ")
		for _, color := range contains {
			if color[len(color)-1] == '.' {
				color = color[:len(color)-1]
			}
			if color[len(color)-1] == 's' {
				color = color[:len(color)-1]
			}
			rules[both[0][:len(both[0])-1]] = append(rules[both[0][:len(both[0])-1]], color[2:])
		}
	}
	for bag := range rules {
		if lookInside(bag, rules, goldIn) {
			goldIn[bag] = true
		}
	}

	fmt.Println(len(goldIn) - 1)
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
