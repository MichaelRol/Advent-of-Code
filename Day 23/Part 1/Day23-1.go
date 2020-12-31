// Crab Cups! Some pretty self explanitory code. A game of moving cups around.
package main

import (
	"fmt"
	"strconv"
	"time"
)

type game struct {
	current *cup
	head    *cup
	tail    *cup
	highVal int
	lowVal  int
}

type cup struct {
	value int
	next  *cup
	prev  *cup
}

func main() {
	start := time.Now()
	// Each number is a cup
	input := "643719258"
	var inputArray []int
	for _, num := range input {
		inputArray = append(inputArray, stringToInt(string(num)))
	}
	game := createGame()
	for _, num := range inputArray {
		game.addCup(num)
	}
	game.current = game.head
	for i := 0; i < 100; i++ {
		game.playRound()
	}

	fmt.Print("Answer: ")
	game.listCupsAfter1()
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func (g *game) playRound() {
	numOfCups := 3
	pickUp := g.pickUpCups(numOfCups)
	dest := g.findDest()
	for i := numOfCups - 1; i >= 0; i-- {
		g.insertCupAfter(pickUp[i], dest)
	}
	g.nextCup()
}

func (g *game) listCupsAfter1() {
	cup := g.findCup(1).next
	for cup.value != 1 {
		fmt.Print(cup.value)
		cup = cup.next
	}
	fmt.Println()
}

func (g *game) pickUpCups(count int) []*cup {
	var pickedUpCups []*cup
	for i := 0; i < count; i++ {
		cup := g.removeCup(g.current.next)
		pickedUpCups = append(pickedUpCups, cup)
	}
	return pickedUpCups
}

func (g *game) insertCupAfter(c, dest *cup) {
	c.next = dest.next
	c.prev = dest
	dest.next.prev = c
	dest.next = c
	if c.value > g.highVal {
		g.highVal = c.value
	}
	if c.value < g.lowVal {
		g.lowVal = c.value
	}
	if dest == g.tail {
		g.tail = c
	}
}

func (g *game) findDest() *cup {
	destValue := g.current.value - 1
	for g.findCup(destValue) == nil {
		if destValue <= g.lowVal {
			destValue = g.highVal
		} else {
			destValue--
		}
	}
	destCup := g.findCup(destValue)
	return destCup
}

func (g *game) nextCup() *cup {
	g.current = g.current.next
	return g.current
}

// func (g *game) prevCup() *cup {
// 	g.current = g.current.prev
// 	return g.current
// }

func (g *game) findCup(value int) *cup {
	currentCup := g.head
	for {
		if currentCup.value == value {
			return currentCup
		}
		if currentCup == g.tail {
			break
		}
		currentCup = currentCup.next
	}
	return nil
}

func createGame() *game {
	return &game{}
}

func (g *game) addCup(value int) {
	c := &cup{value: value}
	if g.head == nil {
		g.head = c
		g.lowVal = c.value
		g.highVal = c.value
	} else {
		currentCup := g.tail
		currentCup.next = c
		c.prev = g.tail
		if c.value > g.highVal {
			g.highVal = c.value
		}
		if c.value < g.lowVal {
			g.lowVal = c.value
		}
	}
	g.tail = c
	c.next = g.head
	g.head.prev = c
}

func (g *game) removeCup(c *cup) *cup {
	c.prev.next = c.next
	c.next.prev = c.prev
	if c == g.head {
		g.head = c.next
	}
	if c == g.tail {
		g.tail = c.prev
	}
	if c.value == g.highVal {
		g.highVal = g.findHighVal()
	}
	if c.value == g.lowVal {
		g.lowVal = g.findLowVal()
	}

	c.next = nil
	c.prev = nil
	return c
}

func (g *game) findHighVal() int {
	max := 0
	for _, value := range g.listAllValues() {
		if value > max {
			max = value
		}
	}
	return max
}

func (g *game) findLowVal() int {
	min := 9999999
	for _, value := range g.listAllValues() {
		if value < min {
			min = value
		}
	}
	return min
}

func (g *game) listAllValues() []int {
	currentCup := g.head
	var values []int
	if currentCup == nil {
		fmt.Println("Playlist is empty.")
		return []int{}
	}
	values = append(values, currentCup.value)
	for currentCup != g.tail {
		currentCup = currentCup.next
		values = append(values, currentCup.value)
	}
	return values
}

// func (g *game) showAllCups() {
// 	currentCup := g.head
// 	if currentCup == nil {
// 		fmt.Println("Playlist is empty.")
// 		return
// 	}
// 	fmt.Print(currentCup.value)
// 	for currentCup != g.tail {
// 		currentCup = currentCup.next
// 		fmt.Print(currentCup.value)
// 	}
// 	fmt.Println()
// }

func stringToInt(input string) int {
	num, err := strconv.Atoi(input)
	if err != nil {
		panic(err)
	}
	return num
}
