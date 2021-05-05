// Cracking some weakass crypto. Was nice of the to finish on an easy one.

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
	cardPK, doorPK := readInput("../input.txt")
	doorLoopSize := calcLoopSize(7, doorPK)

	encryptionKey := transformSubject(cardPK, doorLoopSize)
	t := time.Now()

	fmt.Print("Answer: ")
	fmt.Println(encryptionKey)

	fmt.Print("Execution time: ")
	elapsed := t.Sub(start)
	fmt.Println(elapsed)
}

// Use loop size to produce encryption key
func transformSubject(subject, loopSize int) int {
	value := 1
	divisor := 20201227
	for i := 0; i < loopSize; i++ {
		value *= subject
		value %= divisor
	}
	return value
}

// Find the number of times the subject value is acted on before it turns into the PK
func calcLoopSize(subject, publicKey int) int {
	value := 1
	divisor := 20201227

	for i := 1; ; i++ {
		value *= subject
		value %= divisor
		if value == publicKey {
			return i
		}
	}
}

func readInput(filename string) (cardPK, doorPK int) {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}
	text := string(content)
	lines := strings.Split(text, "\n")

	cardPK, err = strconv.Atoi(lines[0])
	if err != nil {
		panic(err)
	}
	doorPK, err = strconv.Atoi(lines[1])
	if err != nil {
		panic(err)
	}

	return cardPK, doorPK
}
