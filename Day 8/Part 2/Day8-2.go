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

type instruction struct {
	op     string
	arg    int
	access bool
}

func main() {
	start := time.Now()
	content, err := ioutil.ReadFile("../input.txt")
	if err != nil {
		log.Fatal(err)
	}

	const nop = "nop"
	const jmp = "jmp"
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
		if instructions[i].op == nop {
			instructions[i].op = jmp
		} else if instructions[i].op == jmp {
			instructions[i].op = nop
		}

		newList := make([]instruction, len(instructions))
		copy(newList, instructions)

		acc, fin := runProg(newList)

		if fin == 0 {
			fmt.Print("Answer: ")
			fmt.Println(acc)
			t := time.Now()
			elapsed := t.Sub(start)
			fmt.Print("Execution time: ")
			fmt.Println(elapsed)
			return
		}
		if instructions[i].op == nop {
			instructions[i].op = jmp
		} else if instructions[i].op == jmp {
			instructions[i].op = nop
		}
	}
}

func runProg(instructions []instruction) (acc, x int) {
	acc = 0

	for i := 0; i < len(instructions); i++ {
		if instructions[i].access {
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
