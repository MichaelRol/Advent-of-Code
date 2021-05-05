package main

import (
	"fmt"
	"io/ioutil"
	"strconv"
	"strings"
	"time"
)

type instruction struct {
	op     string
	arg    int
	access bool
}

func main() {
	start := time.Now()
	lines := readInput("../input.txt")
	instructions := processInstructions(lines)

	// Tallies accumulator until an instruction is accessed for a second time
	acc := 0
	for i := 0; i < len(instructions); i++ {
		if instructions[i].access {
			fmt.Print("Answer: ")
			fmt.Println(acc)
			t := time.Now()
			elapsed := t.Sub(start)
			fmt.Print("Execution time: ")
			fmt.Println(elapsed)
			return
		}
		instructions[i].access = true

		if instructions[i].op == "acc" {
			acc += instructions[i].arg
		}

		if instructions[i].op == "jmp" {
			i += instructions[i].arg - 1
		}
	}
}

func processInstructions(lines []string) []instruction {
	var instructions []instruction

	for _, line := range lines {
		operation := line[:3]
		i, err := strconv.Atoi(line[4:])
		if err != nil {
			panic(err)
		}
		instructions = append(instructions, instruction{op: operation, arg: i, access: false})
	}
	return instructions
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
