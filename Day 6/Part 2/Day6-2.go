package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"strings"
	"time"
	"unicode"
)

func main() {
	start := time.Now()
	content, err := ioutil.ReadFile("../input.txt")
	if err != nil {
		log.Fatal(err)
	}

	text := string(content)
	groups := strings.Split(text, "\n\n")
	total := 0
	for _, group := range groups {
		persons := strings.Split(group, "\n")
		var ques []string
		for _, char := range group {
			if !unicode.IsSpace(char) && !stringInSlice(string(char), ques) {
				ques = append(ques, string(char))
			}
		}

		qCount := 0
		for _, que := range ques {
			addIt := true
			for _, ans := range persons {
				if !strings.Contains(ans, que) {
					addIt = false
				}
			}
			if addIt {
				qCount++
			}
		}

		total += qCount
	}

	fmt.Print("Answer: ")
	fmt.Println(total)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func stringInSlice(a string, list []string) bool {
	for _, b := range list {
		if b == a {
			return true
		}
	}
	return false
}
