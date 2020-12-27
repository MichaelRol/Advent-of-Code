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

type rule struct {
	lowMin  int
	lowMax  int
	highMin int
	highMax int
}

func main() {
	start := time.Now()
	myTicket, nearbyTickets, rules := processInput("../input.txt")

	validTickets := findValidTickets(rules, nearbyTickets)
	possiblePositons := findPossiblePositions(rules, validTickets)
	definitePositions := findDefinitePositions(possiblePositons)

	total := 1
	for name, value := range definitePositions {
		if strings.Split(name, " ")[0] == "departure" {
			total *= myTicket[value]
		}
	}
	fmt.Print("Answer: ")
	fmt.Println(total)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func findDefinitePositions(possiblePositons map[string][]int) map[string]int {
	definitePositions := make(map[string]int)
	for len(possiblePositons) > 0 {
		for name, values := range possiblePositons {
			if len(values) == 1 {
				definitePositions[name] = values[0]
				delete(possiblePositons, name)
				for name1, values1 := range possiblePositons {
					index := findIndex(values1, values[0])
					possiblePositons[name1] = remove(possiblePositons[name1], index)
				}
			}
		}
	}
	return definitePositions
}

func findPossiblePositions(rules map[string]rule, validTickets [][]int) map[string][]int {
	possiblePositons := make(map[string][]int)
	for name, rule := range rules {
		for i := 0; i < len(validTickets[0]); i++ {
			possible := true
			for _, ticket := range validTickets {
				if !fitRules(ticket[i], rule) {
					possible = false
					break
				}
			}
			if possible {
				possiblePositons[name] = append(possiblePositons[name], i)
			}
		}
	}
	return possiblePositons
}

func findValidTickets(rules map[string]rule, nearbyTickets [][]int) [][]int {
	min := 1000
	max := 0
	for _, rule := range rules {
		if rule.lowMin < min {
			min = rule.lowMin
		}
		if rule.highMax > max {
			max = rule.highMax
		}
	}

	var validTickets [][]int
	for _, ticket := range nearbyTickets {
		valid := true
		for _, value := range ticket {
			if value < min || value > max {
				valid = false
				break
			}
		}
		if valid {
			validTickets = append(validTickets, ticket)
		}
	}
	return validTickets
}

func findIndex(list []int, value int) int {
	for i, v := range list {
		if v == value {
			return i
		}
	}
	return -1
}

func remove(s []int, i int) []int {
	s[len(s)-1], s[i] = s[i], s[len(s)-1]
	return s[:len(s)-1]
}

func fitRules(value int, rule rule) bool {
	if (value >= rule.lowMin && value <= rule.lowMax) || (value >= rule.highMin && value <= rule.highMax) {
		return true
	}
	return false
}

func processInput(filename string) (myTicket []int, nearbyTickets [][]int, rules map[string]rule) {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	sections := strings.Split(text, "\n\n")

	rules = processRules(sections[0])
	myTicket = processMyTicket(sections[1])
	nearbyTickets = processNearbyTickets(sections[2])

	return myTicket, nearbyTickets, rules
}

func processMyTicket(ticketSection string) (myTicket []int) {
	myTicketFullString := strings.Split(ticketSection, "\n")[1]
	myTicketSplitString := strings.Split(myTicketFullString, ",")
	for _, valueString := range myTicketSplitString {
		value, err := strconv.Atoi(valueString)
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}
		myTicket = append(myTicket, value)
	}

	return myTicket
}

func processNearbyTickets(nearbyTicketSection string) (nearbyTickets [][]int) {
	nearbyTicketsStrings := strings.Split(nearbyTicketSection, "\n")[1:]
	for _, ticketString := range nearbyTicketsStrings {
		tickets := strings.Split(ticketString, ",")
		var ticketValues []int
		for _, valueString := range tickets {
			valueInt, err := strconv.Atoi(valueString)
			if err != nil {
				fmt.Println(err)
				os.Exit(2)
			}
			ticketValues = append(ticketValues, valueInt)
		}
		nearbyTickets = append(nearbyTickets, ticketValues)
	}
	return nearbyTickets
}

func processRules(rulesSection string) (rules map[string]rule) {
	rules = make(map[string]rule)
	ruleLines := strings.Split(rulesSection, "\n")
	for _, line := range ruleLines {
		lineSplit := strings.Split(line, ": ")
		valueGroups := strings.Split(lineSplit[1], " or ")
		lowString := strings.Split(valueGroups[0], "-")
		highString := strings.Split(valueGroups[1], "-")

		lowMin, err := strconv.Atoi(lowString[0])
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}

		lowMax, err := strconv.Atoi(lowString[1])
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}

		highMin, err := strconv.Atoi(highString[0])
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}

		highMax, err := strconv.Atoi(highString[1])
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}

		rules[lineSplit[0]] = rule{lowMin: lowMin, lowMax: lowMax, highMin: highMin, highMax: highMax}
	}
	return rules
}
