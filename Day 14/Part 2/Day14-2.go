package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"math"
	"os"
	"strconv"
	"strings"
)

func main() {
	commands := readLines("../input.txt")

	mask := ""
	data := make(map[int]string)
	for _, command := range commands {
		if command[0] == "mask" {
			mask = command[1]
		} else {
			index, binary := processValue(command)
			indexes := applyMask(index, mask)
			for _, i := range indexes {
				data[i] = binary
			}
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

	fmt.Println(total)
}

func applyMask(index int, mask string) (indexes []int) {
	var newIndex []rune

	indexBin := strconv.FormatInt(int64(index), 2)
	xCount := 0

	for i, char := range mask {
		if string(char) == "1" {
			newIndex = append(newIndex, char)
		} else if string(char) == "0" {
			if i < len(mask)-len(indexBin) {
				newIndex = append(newIndex, '0')
			} else {
				newIndex = append(newIndex, rune(indexBin[i-(len(mask)-len(indexBin))]))
			}
		} else if string(char) == "X" {
			newIndex = append(newIndex, 'X')
			xCount++
		}
	}
	for i := 0; i < int(math.Pow(2, float64(xCount))); i++ {
		indexString := []rune{}
		x := 0

		// Add zeros in front of binary
		var str strings.Builder
		iBin := strconv.FormatInt(int64(i), 2)
		for y := 0; y < xCount-len(iBin); y++ {
			str.WriteString("0")
		}
		str.WriteString(iBin)
		iBin = str.String()

		// If X append value from above binary otherwise just append 0 or 1
		for _, char := range newIndex {
			if char == 'X' {
				indexString = append(indexString, rune(iBin[x]))
				x++
			} else {
				indexString = append(indexString, char)
			}
		}
		indexInt, err := strconv.ParseInt(string(indexString), 2, 64)
		if err != nil {
			fmt.Println(indexInt)
			os.Exit(2)
		}
		indexes = append(indexes, int(indexInt))
	}
	return indexes
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