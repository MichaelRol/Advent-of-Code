package main

import (
	"fmt"
	"strings"
	"log"
	"io/ioutil"
	"strconv"
	"os"
)

type instruction struct {
	op string
	arg int
	access bool
}

func main() {
	content, err := ioutil.ReadFile("../input.txt")
	if err != nil {
		log.Fatal(err)
	}

	text := string(content)
	lines := strings.Split(text, "\n")

	var instructions []instruction

	for _, line := range lines {
		operation := line[:3]
		i, err := strconv.Atoi(line[4:])
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}
		instructions = append(instructions, instruction{op: operation, arg: i, access: false})
	}

	for i := 0; i < len(instructions); i++ {
		
		if instructions[i].op == "nop" {
			instructions[i].op = "jmp"
		} else if instructions[i].op == "jmp"{
			instructions[i].op = "nop"
		}
		
		newList := make([]instruction, len(instructions))
		copy(newList, instructions)

		acc, fin := runProg(newList)

		if fin == 0 {
			fmt.Println(acc)
			return
		} else {
			if instructions[i].op == "nop" {
				instructions[i].op = "jmp"
			} else if instructions[i].op == "jmp"{
				instructions[i].op = "nop"
			}
		}
	}

	
}

func runProg (instructions []instruction) (int, int) {

	acc := 0
	
	for i := 0; i < len(instructions); i++ {

		if instructions[i].access == true{
			return acc, 1
		}
		instructions[i].access = true

		if instructions[i].op == "acc" {
			acc += instructions[i].arg
		}

		if instructions[i].op == "jmp" {
			i += instructions[i].arg - 1
		}

	}
	return acc, 0
}