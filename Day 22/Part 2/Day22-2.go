// Recursive Combat Card Game
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
	player1, player2 := readHands("../input.txt")
	player1, player2 = recursiveCombat(player1, player2)

	fmt.Print("Answer: ")
	if len(player1) != 0 {
		fmt.Println(countScore(player1))
	} else {
		fmt.Println(countScore(player2))
	}
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func countScore(cards []int) int {
	score := 0

	for i, value := range cards {
		score += value * (len(cards) - i)
	}

	return score
}

func recursiveCombat(player1, player2 []int) (newPlayer1, newPlayer2 []int) {
	var handList [][][]int
	handPair := [][]int{player1, player2}
	handList = append(handList, handPair)
	// If no one has won, play a round
	for len(player1) > 0 && len(player2) > 0 {
		player1, player2 = playRound(player1, player2)
		// If exact hand has been played before make Player 1 win by emptying Player 2's hand.
		if isHandInHandList(handList, [][]int{player1, player2}) {
			player2 = []int{}
		}
		handPair := [][]int{player1, player2}
		handList = append(handList, handPair)
	}
	newPlayer1 = player1
	newPlayer2 = player2
	return newPlayer1, newPlayer2
}

func playRound(player1, player2 []int) (newPlayer1, newPlayer2 []int) {
	// If both players have more cards than the value of the one they are playing
	// start recursive game with each player having the number of cards equal to the
	// value of the car they played.
	if player1[0] <= len(player1)-1 && player2[0] <= len(player2)-1 {
		player1Input := make([]int, len(player1[1:player1[0]+1]))
		player2Input := make([]int, len(player2[1:player2[0]+1]))
		copy(player1Input, player1[1:player1[0]+1])
		copy(player2Input, player2[1:player2[0]+1])
		subPlayer1, subPlayer2 := recursiveCombat(player1Input, player2Input)
		if len(subPlayer1) != 0 {
			winningCard := player1[0]
			losingCard := player2[0]
			newPlayer1 = player1[1:]
			newPlayer2 = player2[1:]
			newPlayer1 = append(newPlayer1, winningCard, losingCard)
		} else if len(subPlayer2) != 0 {
			winningCard := player2[0]
			losingCard := player1[0]
			newPlayer1 = player1[1:]
			newPlayer2 = player2[1:]
			newPlayer2 = append(newPlayer2, winningCard, losingCard)
		} else {
			fmt.Println("Error")
			os.Exit(2)
		}
		// Otherwise play normal Combat
	} else {
		if player1[0] > player2[0] {
			winningCard := player1[0]
			losingCard := player2[0]
			newPlayer1 = player1[1:]
			newPlayer2 = player2[1:]
			newPlayer1 = append(newPlayer1, winningCard, losingCard)
		} else {
			winningCard := player2[0]
			losingCard := player1[0]
			newPlayer1 = player1[1:]
			newPlayer2 = player2[1:]
			newPlayer2 = append(newPlayer2, winningCard, losingCard)
		}
	}

	return newPlayer1, newPlayer2
}

func isHandInHandList(handList [][][]int, newHand [][]int) bool {
	for _, oldHand := range handList {
		isItIn := true
		if len(oldHand[0]) == len(newHand[0]) && len(oldHand[1]) == len(newHand[1]) {
			for i := 0; i < len(newHand[0]); i++ {
				if oldHand[0][i] != newHand[0][i] {
					isItIn = false
					break
				}
			}
			for i := 0; i < len(newHand[1]); i++ {
				if oldHand[1][i] != newHand[1][i] {
					isItIn = false
					break
				}
			}
		} else {
			isItIn = false
		}
		if isItIn {
			return true
		}
	}
	return false
}

func readHands(filename string) (player1, player2 []int) {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	hands := strings.Split(text, "\n\n")

	player1Hand := strings.Split(hands[0], "\n")[1:]
	player2Hand := strings.Split(hands[1], "\n")[1:]

	for _, card := range player1Hand {
		cardValue, err := strconv.Atoi(card)
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}
		player1 = append(player1, cardValue)
	}

	for _, card := range player2Hand {
		cardValue, err := strconv.Atoi(card)
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}
		player2 = append(player2, cardValue)
	}

	return player1, player2
}
