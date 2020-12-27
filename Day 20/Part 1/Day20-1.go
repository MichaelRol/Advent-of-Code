package main

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strconv"
	"strings"
	"time"
)

type tile struct {
	top   string
	bot   string
	left  string
	right string
}

func main() {
	start := time.Now()
	tiles := readInput("../input.txt")
	total := 1
	for name, tile := range tiles {
		count := 0

		for checkTileName, checkTile := range tiles {
			if name == checkTileName {
				continue
			}
			if matchTiles(tile, checkTile) {
				count++
			}
			if count == 3 {
				break
			}
		}

		if count == 2 {
			value, err := strconv.Atoi(name)
			if err != nil {
				fmt.Println(err)
				os.Exit(2)
			}

			total *= value
		}
	}
	fmt.Print("Answer: ")
	fmt.Println(total)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func matchTiles(mainTile, checkTile tile) bool {
	if isIn(mainTile.top, checkTile) {
		return true
	} else if isIn(mainTile.bot, checkTile) {
		return true
	} else if isIn(mainTile.left, checkTile) {
		return true
	} else if isIn(mainTile.right, checkTile) {
		return true
	}
	return false
}

func isIn(side string, checkTile tile) bool {
	if side == checkTile.top || side == checkTile.bot || side == checkTile.left || side == checkTile.right {
		return true
	}
	reverseTop := Reverse(checkTile.top)
	reverseBot := Reverse(checkTile.bot)
	reverseLeft := Reverse(checkTile.left)
	reverseRight := Reverse(checkTile.right)

	if side == reverseTop || side == reverseBot || side == reverseLeft || side == reverseRight {
		return true
	}
	return false
}

func Reverse(s string) string {
	runes := []rune(s)
	for i, j := 0, len(runes)-1; i < j; i, j = i+1, j-1 {
		runes[i], runes[j] = runes[j], runes[i]
	}
	return string(runes)
}

func readInput(filename string) map[string]tile {
	tiles := make(map[string]tile)

	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	sections := strings.Split(text, "\n\n")

	for _, section := range sections {
		lines := strings.Split(section, "\n")
		name := lines[0][5 : len(lines[0])-1]
		top := lines[1]
		bot := lines[len(lines)-1]

		var leftBuffer bytes.Buffer
		var rightBuffer bytes.Buffer

		for _, line := range lines[1:] {
			leftBuffer.WriteString(string(line[0]))
			rightBuffer.WriteString(string(line[len(line)-1]))
		}
		left := leftBuffer.String()
		right := rightBuffer.String()

		tiles[name] = tile{top: top, bot: bot, left: left, right: right}
	}

	return tiles
}
