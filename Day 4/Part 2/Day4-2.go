package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"regexp"
	"strconv"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	validCount := 0
	passports := readInput("../input.txt")

	// Checks each passport has the correct number of fields
	// and that each field is valid
	for _, passport := range passports {
		fields := strings.Fields(passport)
		if len(fields) == 8 || len(fields) == 7 {
			validCount += checkFields(fields)
		}
	}
	fmt.Print("Answer: ")
	fmt.Println(validCount)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func checkFields(fields []string) int {
	for j := 0; j < len(fields); j++ {
		switch fields[j][0:3] {
		case "byr":
			if !validateByr(fields[j][4:]) {
				return 0
			}
		case "iyr":
			if !validateIyr(fields[j][4:]) {
				return 0
			}
		case "eyr":
			if !validateEyr(fields[j][4:]) {
				return 0
			}
		case "hgt":
			if !validateHgt(fields[j][4:]) {
				return 0
			}
		case "hcl":
			if !validateHcl(fields[j][4:]) {
				return 0
			}
		case "ecl":
			if !validateEcl(fields[j][4:]) {
				return 0
			}
		case "pid":
			if !validatePid(fields[j][4:]) {
				return 0
			}
		case "cid":
			if len(fields) == 7 {
				return 0
			}
		}
	}
	return 1
}

func validateByr(byr string) bool {
	i, err := strconv.Atoi(byr)
	if err != nil {
		fmt.Println(err)
		os.Exit(2)
	}
	if i < 1920 || i > 2002 {
		return false
	}
	return true
}

func validateIyr(iyr string) bool {
	i, err := strconv.Atoi(iyr)
	if err != nil {
		fmt.Println(err)
		os.Exit(2)
	}
	if i < 2010 || i > 2020 {
		return false
	}
	return true
}

func validateEyr(eyr string) bool {
	i, err := strconv.Atoi(eyr)
	if err != nil {
		fmt.Println(err)
		os.Exit(2)
	}
	if i < 2020 || i > 2030 {
		return false
	}
	return true
}

func validateHgt(hgt string) bool {
	measurement := hgt[len(hgt)-2:]
	valueS := hgt[:len(hgt)-2]
	value, err := strconv.Atoi(valueS)
	if err != nil {
		return false
	}
	if measurement == "cm" {
		if value < 150 || value > 193 {
			return false
		}
	} else if measurement == "in" {
		if value < 59 || value > 76 {
			return false
		}
	} else {
		return false
	}
	return true
}

func validateHcl(hcl string) bool {
	r := regexp.MustCompile("^#[0-9a-f]{6}$")
	return r.MatchString(hcl)
}

func validateEcl(ecl string) bool {
	if ecl != "amb" && ecl != "blu" && ecl != "brn" && ecl != "gry" && ecl != "grn" && ecl != "hzl" && ecl != "oth" {
		return false
	}
	return true
}

func validatePid(pid string) bool {
	r := regexp.MustCompile(`^\d{9}$`)
	return r.MatchString(pid)
}

func readInput(filename string) []string {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}
	text := string(content)
	lines := strings.Split(text, "\n\n")

	return lines
}
