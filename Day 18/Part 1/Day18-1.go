package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {
	equations := readInput("../input.txt")
	sum := 0
	for _, equation := range equations {
		num := processEquation(equation)
		sum += num
	}
	fmt.Println(sum)
}

func processEquation(equation string) int {
	operation := ""
	value := 0
	for i := 0; i < len(equation); i++ {
		if string(equation[i]) == " " {
			continue
		} else if string(equation[i]) == "(" {
			startIndex := i + 1
			parenCount := 1
			for j := i + 1; j < len(equation); j++ {
				if string(equation[j]) == "(" {
					parenCount++
				} else if string(equation[j]) == ")" {
					parenCount--
					if parenCount == 0 {
						subEquation := equation[startIndex:j]
						num := processEquation(subEquation)
						if value == 0 {
							value = num
						} else if operation == "+" {
							value += num
						} else if operation == "*" {
							value *= num
						}
						i = j
						break
					}
				}
			}
		} else if string(equation[i]) == "+" {
			operation = "+"
		} else if string(equation[i]) == "*" {
			operation = "*"
		} else {
			num, err := strconv.Atoi(string(equation[i]))
			if err != nil {
				fmt.Println(string(equation[i]))
				fmt.Println("Invalid character")
				os.Exit(2)
			}
			if value == 0 {
				value = num
			} else if operation == "+" {
				value += num
			} else if operation == "*" {
				value *= num
			}
		}
	}
	return value
}

func readInput(filename string) []string {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	lines := strings.Split(text, "\n")

	return lines
}
