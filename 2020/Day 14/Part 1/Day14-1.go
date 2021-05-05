// Input is a lisk of masks and values to be stored in memory after having
// current mask applied.
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

func main() {
	start := time.Now()
	commands := readLines("../input.txt")

	mask := ""
	data := make(map[int]string)
	for _, command := range commands {
		// Either update mask value, or apply mask to value stored at index
		if command[0] == "mask" {
			mask = command[1]
		} else {
			index, binary := processValue(command)
			maskedBinary := applyMask(binary, mask)
			data[index] = maskedBinary
		}
	}

	total := 0
	for _, v := range data {
		intValue, err := strconv.ParseInt(v, 2, 64)
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}
		total += int(intValue)
	}
	fmt.Print("Answer: ")
	fmt.Println(total)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func applyMask(binary, mask string) (maskedBinary string) {
	var newBinary []rune
	for i, char := range mask {
		if string(char) == "1" {
			newBinary = append(newBinary, char)
		} else if string(char) == "0" {
			newBinary = append(newBinary, char)
		} else if string(char) == "X" {
			if i < len(mask)-len(binary) {
				newBinary = append(newBinary, '0')
			} else {
				newBinary = append(newBinary, rune(binary[i-(len(mask)-len(binary))]))
			}
		}
	}
	return string(newBinary)
}

func processValue(command []string) (index int, binary string) {
	index, err1 := strconv.Atoi(command[0][4 : len(command[0])-1])
	if err1 != nil {
		fmt.Println("Bad index.")
		os.Exit(2)
	}

	value, err2 := strconv.Atoi(command[1])
	if err2 != nil {
		fmt.Println("Bad index.")
		os.Exit(2)
	}

	binary = strconv.FormatInt(int64(value), 2)

	return index, binary
}

func readLines(filename string) [][]string {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	var commands [][]string
	text := string(content)
	lines := strings.Split(text, "\n")
	for _, line := range lines {
		commands = append(commands, strings.Split(line, " = "))
	}
	return commands
}
