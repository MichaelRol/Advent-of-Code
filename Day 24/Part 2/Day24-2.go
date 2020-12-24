/*  It should be noted that the Hex code (the struct, directions, and the newHex and hexNeighbour functions) were taken from
https://github.com/pmcxs/hexgrid/blob/master/hex.go as the hex type was unexported but I needed it for my tiles map so
being a bit thick I couldn't work out what else to do, so I copied the code I needed in. Cheers Pedro */

package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"
)

type direction int

var directions = []hex{
	newHex(1, 0),
	newHex(1, -1),
	newHex(0, -1),
	newHex(-1, 0),
	newHex(-1, +1),
	newHex(0, +1),
}

type hex struct {
	q int // x axis
	r int // y axis
	s int // z axis
}

func main() {
	directionsList := readInput("../input.txt")
	tiles := make(map[hex]bool)
	refTile := newHex(0, 0)

	tiles[refTile] = false

	for _, directions := range directionsList {
		currTile := refTile
		for _, direction := range directions {
			currTile = hexNeighbor(currTile, direction)
			if _, ok := tiles[currTile]; !ok {
				tiles[currTile] = false
			}
		}
		if _, ok := tiles[currTile]; ok {
			tiles[currTile] = !tiles[currTile]
		} else {
			tiles[currTile] = true
		}
	}
	for i := 0; i < 100; i++ {
		tiles = gameOfLife(tiles)
	}

	fmt.Println(countBlackTiles(tiles))
}

func gameOfLife(tiles map[hex]bool) map[hex]bool {
	newTiles := make(map[hex]bool)
	for tile := range tiles {
		for d := 0; d < 6; d++ {
			if _, ok := tiles[hexNeighbor(tile, direction(d))]; !ok {
				tiles[hexNeighbor(tile, direction(d))] = false
			}
		}
	}
	for tile, isBlack := range tiles {
		blackNeighbours := 0
		for d := 0; d < 6; d++ {
			if blackNeighbour, ok := tiles[hexNeighbor(tile, direction(d))]; ok {
				if blackNeighbour {
					blackNeighbours++
				}
			} else {
				newTiles[hexNeighbor(tile, direction(d))] = false
			}
		}
		if isBlack {
			if blackNeighbours == 0 || blackNeighbours > 2 {
				newTiles[tile] = false
			} else {
				newTiles[tile] = true
			}
		} else {
			if blackNeighbours == 2 {
				newTiles[tile] = true
			} else {
				newTiles[tile] = false
			}
		}
	}

	return newTiles
}

func countBlackTiles(tiles map[hex]bool) int {
	blackCount := 0
	for _, isBlack := range tiles {
		if isBlack {
			blackCount++
		}
	}
	return blackCount
}

func newHex(q, r int) hex {
	h := hex{q: q, r: r, s: -q - r}
	return h
}

func hexNeighbor(h hex, direction direction) hex {
	directionOffset := directions[direction]
	return newHex(h.q+directionOffset.q, h.r+directionOffset.r)
}

func readInput(filename string) [][]direction {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}
	text := string(content)
	lines := strings.Split(text, "\n")

	directions := make([][]direction, len(lines))
	for x, line := range lines {
		for i := 0; i < len(line); i++ {
			if string(line[i]) == "e" {
				directions[x] = append(directions[x], 0)
			} else if string(line[i]) == "w" {
				directions[x] = append(directions[x], 3)
			} else if string(line[i]) == "n" {
				if string(line[i+1]) == "e" {
					directions[x] = append(directions[x], 5)
				} else if string(line[i+1]) == "w" {
					directions[x] = append(directions[x], 4)
				}
				i++
			} else if string(line[i]) == "s" {
				if string(line[i+1]) == "e" {
					directions[x] = append(directions[x], 1)
				} else if string(line[i+1]) == "w" {
					directions[x] = append(directions[x], 2)
				}
				i++
			}
		}
	}
	return directions
}
