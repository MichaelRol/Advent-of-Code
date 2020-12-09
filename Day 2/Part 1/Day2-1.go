package main


import (
    "bufio"
    "fmt"
    "log"
	"os"
	"strings"
	"strconv"
)

func main() {
	var count int = 0

	file, err := os.Open("../input.txt")
	
    if err != nil {
        log.Fatal(err)
    }
    defer file.Close()

	scanner := bufio.NewScanner(file)
	var passwords []string
	
    for scanner.Scan() {
        passwords = append(passwords, scanner.Text())
    }

    if err := scanner.Err(); err != nil {
        log.Fatal(err)
	}

	for i:=0; i<len(passwords); i++ {
		if isPassValid(passwords[i]) {
			count++
		}
	}

	fmt.Println(count)
}

func isPassValid(fullString string) bool {

	var splitString []string = strings.Split(fullString, ": ")
	var password = splitString[1]

	splitString = strings.Split(splitString[0], " ")
	var letter = splitString[1]

	splitString = strings.Split(splitString[0], "-")
	var min, err1 = strconv.Atoi(splitString[0])
	var max, err2 = strconv.Atoi(splitString[1])

	if err1 != nil {
        fmt.Println(err1)
        os.Exit(2)
	}
	
	if err2 != nil {
        fmt.Println(err2)
        os.Exit(2)
    }

	letterCount := strings.Count(password, letter)

	if letterCount >= min && letterCount <= max {
		return true
	} else {
		return false
	}

}