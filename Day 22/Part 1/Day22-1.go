package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {
	player1, player2 := readHands("../input.txt")
	for len(player1) > 0 && len(player2) > 0 {
		player1, player2 = playHand(player1, player2)
	}

	if len(player1) != 0 {
		fmt.Println(countScore(player1))
	} else {
		fmt.Println(countScore(player2))
	}
}

func countScore(cards []int) int {
	score := 0

	for i, value := range cards {
		score += value * (len(cards) - i)
	}

	return score
}

func playHand(player1, player2 []int) (newPlayer1, newPlayer2 []int) {
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

	return newPlayer1, newPlayer2
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
