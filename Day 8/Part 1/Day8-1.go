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
