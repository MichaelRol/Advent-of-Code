package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"regexp"
	"strconv"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	rules, messages := readRules("../input.txt")
	unlooped := unloopWithDepth(rules, 6)
	rgx := regexp.MustCompile(parseRules(unlooped))

	var counter = 0
	for _, msg := range strings.Split(messages, "\n") {
		if rgx.MatchString(msg) {
			counter++
		}
	}
	fmt.Print("Answer: ")
	fmt.Println(counter)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func parseRules(rawRules string) string {
	mem := make(map[int]string)
	rules := strings.Split(rawRules, "\n")
	for {
		if _, ok := mem[0]; ok {
			break
		}
	rulesLoop:
		for _, rule := range rules {
			tokens := strings.Fields(rule)
			var i int
			fmt.Sscanf(tokens[0], "%d:", &i)
			// visited check:
			if _, ok := mem[i]; ok {
				continue
			}
			tokens = tokens[1:] // remove the index from the tokens
			// base case:
			if len(tokens) == 1 && tokens[0][0] == '"' {
				mem[i] = string(tokens[0][1])
				continue
			}
			// list of rules:
			ruleRegex := "(?:"
			for _, token := range tokens {
				switch c := token[0]; {
				case c >= '0' && c <= '9':
					tokenNum := mustAtoi(token)
					subRuleRegex, ok := mem[tokenNum]
					if !ok {
						continue rulesLoop
					}
					ruleRegex += subRuleRegex
				case c == '|':
					ruleRegex += "|"
				case c == '+':
					ruleRegex += "+"
				default:
					log.Fatal("somethings gone wrong: ", token)
				}
			}
			ruleRegex += ")"
			mem[i] = ruleRegex
		}
	}
	// we are interested in an exact match of rule 0
	return "^" + mem[0] + "$"
}

// generates x possible loop variations (loop depth) for rules with loops (8 and 11)
func unloopWithDepth(rawRules string, depth int) string {
	var unlooped string
	for _, line := range strings.Split(rawRules, "\n") {
		newLine := line
		// unloop rule 8
		if line[0:2] == "8:" {
			newLine = "8: 42 +"
		}
		// unloop rule 11
		if line[0:3] == "11:" {
			rule := "42 31"
			for i := 2; i < depth; i++ {
				rule += " | "
				for j := 0; j < i; j++ {
					rule += " 42 "
				}
				for j := 0; j < i; j++ {
					rule += " 31 "
				}
			}
			newLine = "11: " + rule
		}
		unlooped += newLine + "\n"
	}
	return unlooped[:len(unlooped)-1]
}

func readRules(filename string) (rules, messages string) {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	split := strings.Split(text, "\n\n")
	return split[0], split[1]
}

func mustAtoi(s string) int {
	i, err := strconv.Atoi(s)
	if err != nil {
		panic(err)
	}
	return i
}
