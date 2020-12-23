/* It should be noted that this solution was inspired by https://github.com/Snosixtyboo/aoc2020/blob/master/20-2.go
   cause frankly this problem was ****ing mental. */

package main

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"log"
	"math"
	"math/bits"
	"os"
	"strconv"
	"strings"
)

var grid [][]int
var tiles = make(map[int]Tile)
var findTileFromBorder = make(map[uint]map[int]bool)

type Tile struct {
	pixels       [][]byte
	border       [4]uint
	length       int
	numUnmatched int
}

func main() {
	gridLength, tileLength := processInput("../input.txt")
	fillGrid(prepareRefTile(), 0, 0)
	fullImage := stitchImage(tileLength, gridLength)
	fmt.Println(countWaves(tileLength, gridLength, fullImage))
}

func produceHash(hash uint, length int) uint {
	reverseHash := bits.Reverse(hash) >> (64 - length)
	return max(hash, reverseHash)
}

func rotateTile(t int) {
	tile := tiles[t]
	newPixels := make([][]byte, len(tile.pixels))
	for y := 0; y < tile.length; y++ {
		newPixels[y] = make([]byte, len(tile.pixels[y]))
		for x := 0; x < tile.length; x++ {
			newPixels[y][x] = tile.pixels[x][tile.length-1-y]
		}
	}
	tile.pixels = newPixels
	tile.border[0], tile.border[1], tile.border[2], tile.border[3] = tile.border[1], tile.border[2], tile.border[3], tile.border[0]
	tiles[t] = tile
}

func horiTileFlip(p int) {
	tile := tiles[p]
	newPixels := make([][]byte, len(tile.pixels))
	for y := 0; y < tile.length; y++ {
		newPixels[y] = make([]byte, len(tile.pixels[y]))
		for x := 0; x < tile.length; x++ {
			newPixels[y][x] = tile.pixels[y][tile.length-1-x]
		}
	}
	tile.pixels = newPixels
	tile.border[1], tile.border[3] = tile.border[3], tile.border[1]
	for i := 0; i < 4; i++ {
		tile.border[i] = bits.Reverse(tile.border[i]) >> (64 - tile.length)
	}
	tiles[p] = tile
}

func vertTileV(p int) {
	tile := tiles[p]
	newPixels := make([][]byte, len(tile.pixels))
	for y := 0; y < tile.length; y++ {
		newPixels[y] = make([]byte, len(tile.pixels[y]))
		for x := 0; x < tile.length; x++ {
			newPixels[y][x] = tile.pixels[tile.length-1-y][x]
		}
	}
	tile.pixels = newPixels
	tile.border[0], tile.border[2] = tile.border[2], tile.border[0]
	for i := 0; i < 4; i++ {
		tile.border[i] = bits.Reverse(tile.border[i]) >> (64 - tile.length)
	}
	tiles[p] = tile
}

func prepareRefTile() int {
	refTile := -1
	for _, ids := range findTileFromBorder {
		if len(ids) == 1 {
			for id := range ids {
				p := tiles[id]
				p.numUnmatched++
				if p.numUnmatched == 2 {
					refTile = id
					break
				}
				tiles[id] = p
			}
		}
	}
	for len(findTileFromBorder[produceHash(tiles[refTile].border[1], tiles[refTile].length)]) < 2 ||
		len(findTileFromBorder[produceHash(tiles[refTile].border[2], tiles[refTile].length)]) < 2 {
		rotateTile(refTile)
	}
	return refTile
}

func fillGrid(id, x, y int) {
	if grid[y][x] != 0 {
		return
	}
	grid[y][x] = id
	tile := tiles[id]
	for c := 1; c <= 2; c++ {
		opposite := (c + 2) % 4
		code := tile.border[c]
		hash := produceHash(code, tile.length)
		if len(findTileFromBorder[hash]) > 1 {
			var other int
			for entry := range findTileFromBorder[hash] {
				if entry != id {
					other = entry
				}
			}
			for produceHash(tiles[other].border[opposite], tiles[other].length) != hash {
				rotateTile(other)
			}
			if tiles[other].border[opposite] == code {
				if c == 1 {
					vertTileV(other)
				} else {
					horiTileFlip(other)
				}
			}
			if c == 1 {
				fillGrid(other, x+1, y)
			} else {
				fillGrid(other, x, y+1)
			}
		}
	}
}

func stitchImage(tileLength, gridLength int) [][]byte {
	imgLength := gridLength * (tileLength - 2)
	fullImage := make([][]byte, imgLength)
	for y := 0; y < gridLength; y++ {
		for ty := 1; ty < tileLength-1; ty++ {
			fullImage[y*(tileLength-2)+ty-1] = make([]byte, imgLength)
			for x := 0; x < gridLength; x++ {
				for tx := 1; tx < tileLength-1; tx++ {
					fullImage[y*(tileLength-2)+ty-1][x*(tileLength-2)+tx-1] = tiles[grid[y][x]].pixels[ty][tx]
				}
			}
		}
	}
	return fullImage
}

func countWaves(tileLength, gridLength int, fullImage [][]byte) int {
	imgLength := gridLength * (tileLength - 2)
	monsterString := []string{"                  # ", "#    ##    ##    ###", " #  #  #  #  #  #   "}
	// Iterate over image and count occurrences
	monsterCounts := 0
	for cfg := 0; cfg < 8 && monsterCounts == 0; cfg++ {
		checkImg := fullImage
		if cfg > 4 {
			newPixels := make([][]byte, len(checkImg))
			for y := 0; y < imgLength; y++ {
				newPixels[y] = make([]byte, len(checkImg[y]))
				for x := 0; x < imgLength; x++ {
					newPixels[y][x] = checkImg[y][imgLength-1-x]
				}
			}
			checkImg = newPixels
		}
		for y := 0; y < imgLength-len(monsterString); y++ {
		NewMonster:
			for x := 0; x < imgLength-len(monsterString[0]); x++ {
				for my := 0; my < len(monsterString); my++ {
					for mx := 0; mx < len(monsterString[0]); mx++ {
						if monsterString[my][mx] == '#' && checkImg[y+my][x+mx] != '#' {
							continue NewMonster
						}
					}
				}
				monsterCounts++
			}
		}
		newPixels := make([][]byte, len(fullImage))
		for y := 0; y < imgLength; y++ {
			newPixels[y] = make([]byte, len(fullImage[y]))
			for x := 0; x < imgLength; x++ {
				newPixels[y][x] = fullImage[x][imgLength-1-y]
			}
		}
		fullImage = newPixels
	}
	wavesInImage, wavesInMonster := 0, 0
	for y := 0; y < imgLength; y++ {
		wavesInImage += strings.Count(string(fullImage[y]), "#")
	}
	for y := 0; y < len(monsterString); y++ {
		wavesInMonster += strings.Count(monsterString[y], "#")
	}
	return wavesInImage - wavesInMonster*monsterCounts
}

func createGrid(sections [][]byte) int {
	length := int(math.Sqrt(float64(len(sections))))
	grid = make([][]int, length)
	for x := 0; x < length; x++ {
		grid[x] = make([]int, length)
	}
	return length
}

func processTiles(sections [][]byte) int {
	var tileLength int
	for _, section := range sections {
		lines := bytes.Split(section, []byte("\n"))
		name, err := strconv.Atoi(string(lines[0][5 : len(lines[0])-1]))
		if err != nil {
			panic(err)
		}
		tile := Tile{length: len(lines[1]), pixels: lines[1:]}

		var topBuffer, botBuffer, leftBuffer, rightBuffer bytes.Buffer

		for _, x := range lines[1] {
			if string(x) == "#" {
				topBuffer.WriteString("1")
			} else {
				topBuffer.WriteString("0")
			}
		}
		topInt, err := strconv.ParseInt(topBuffer.String(), 2, 64)
		if err != nil {
			panic(err)
		}

		for _, x := range lines[len(lines)-1] {
			if string(x) == "#" {
				botBuffer.WriteString("1")
			} else {
				botBuffer.WriteString("0")
			}
		}
		botInt, err := strconv.ParseInt(Reverse(botBuffer.String()), 2, 64)
		if err != nil {
			panic(err)
		}

		for _, line := range lines[1:] {
			if string(line[0]) == "#" {
				leftBuffer.WriteString("1")
			} else {
				leftBuffer.WriteString("0")
			}
			if string(line[len(line)-1]) == "#" {
				rightBuffer.WriteString("1")
			} else {
				rightBuffer.WriteString("0")
			}
		}

		rightInt, err := strconv.ParseInt(rightBuffer.String(), 2, 64)
		if err != nil {
			panic(err)
		}
		leftInt, err := strconv.ParseInt(Reverse(leftBuffer.String()), 2, 64)
		if err != nil {
			panic(err)
		}

		tile.border[0] = uint(topInt)
		tile.border[1] = uint(rightInt)
		tile.border[2] = uint(botInt)
		tile.border[3] = uint(leftInt)

		tiles[name] = tile
		tileLength = tile.length
	}
	for id, p := range tiles {
		for _, border := range p.border {
			hash := produceHash(border, p.length)
			if findTileFromBorder[hash] == nil {
				findTileFromBorder[hash] = make(map[int]bool)
			}
			findTileFromBorder[hash][id] = true
		}
	}
	return tileLength
}

func Reverse(s string) string {
	runes := []rune(s)
	for i, j := 0, len(runes)-1; i < j; i, j = i+1, j-1 {
		runes[i], runes[j] = runes[j], runes[i]
	}
	return string(runes)
}

func max(a, b uint) uint {
	if a > b {
		return a
	}
	return b
}

func processInput(filename string) (gridLength, tileLength int) {
	pixels, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}
	sections := bytes.Split(pixels, []byte("\n\n"))
	gridLength = createGrid(sections)
	tileLength = processTiles(sections)
	return
}
