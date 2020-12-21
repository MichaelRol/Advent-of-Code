package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"
)

func main() {
	rules, _ := readInput("../input.txt")

	expandedRule := expandRule(rules["0"][0], rules)
	fmt.Println(expandedRule)
}

func expandRule(rule []string, rules map[string][][]string) [][]string {
	var newRule [][]string

	return newRule
}

func readInput(filename string) (rules map[string][][]string, messages []string) {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	sections := strings.Split(text, "\n\n")

	ruleStrings := strings.Split(sections[0], "\n")
	rules = make(map[string][][]string)

	for _, rule := range ruleStrings {
		splitRule := strings.Split(rule, ": ")
		subRules := strings.Split(splitRule[1], " | ")
		for _, subRule := range subRules {
			rules[splitRule[0]] = append(rules[splitRule[0]], strings.Split(subRule, " "))
		}
	}

	messages = strings.Split(sections[1], "\n")

	return rules, messages
}
