// You have a large set of train tickets each with set of values, but you can't read the fields the values relate to.
// You have a set of rules which dictate the values each field can take. The task is to find which tickets have
// values which don't fit any of the rules, and are therefore invalid.
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
	_, nearbyTickets, rules := processInput("../input.txt")
	min := 1000
	max := 0
	// Find range of accepted values (low ranges and high ranges overlap so only overall max/min are needed)
	for _, rule := range rules {
		if rule.lowMin < min {
			min = rule.lowMin
		}
		if rule.highMax > max {
			max = rule.highMax
		}
	}
	// Total values outside of accepted range as error rate
	errorRate := 0
	for _, ticket := range nearbyTickets {
		for _, value := range ticket {
			if value < min || value > max {
				errorRate += value
			}
		}
	}
	fmt.Print("Answer: ")
	fmt.Println(errorRate)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
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
