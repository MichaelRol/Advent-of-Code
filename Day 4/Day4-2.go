package main

import (
    "io/ioutil"
    "fmt"
    "log"
	"strings"
	"strconv"
	"os"
	"regexp"
)

func main() {

	validCount := 0

	content, err := ioutil.ReadFile("input.txt")
    if err != nil {
        log.Fatal(err)
    }

	text := string(content)
	lines := strings.Split(text, "\n\n")
	for i := 0; i < len(lines); i++ {
		valid := true
		data := strings.Fields(lines[i])
		fullfields := strings.Fields(lines[i])
		for j := 0; j < len(data); j++ {
			data[j] = string(data[j][0:3])
		}
		if len(data) == 8 {
			// valid++
			for j := 0; j < len(data); j++ {
				if string(fullfields[j][0:3]) == "byr" {
					i, err := strconv.Atoi(string(fullfields[j][4:]))
					if err != nil {
						fmt.Println(err)
						os.Exit(2)
					}
					if i < 1920 || i > 2002 {
						valid = false
					}
					
				}

				if string(fullfields[j][0:3]) == "iyr" {
					i, err := strconv.Atoi(string(fullfields[j][4:]))
					if err != nil {
						fmt.Println(err)
						os.Exit(2)
					}
					if i < 2010 || i > 2020 {
						valid = false
					}
					
				}

				if string(fullfields[j][0:3]) == "eyr" {
					i, err := strconv.Atoi(string(fullfields[j][4:]))
					if err != nil {
						fmt.Println(err)
						os.Exit(2)
					}
					if i < 2020 || i > 2030 {
						valid = false
					}
					
				}

				if string(fullfields[j][0:3]) == "hgt" {
					last2  := string(fullfields[j][len(fullfields[j])-2:])
					middle := string(fullfields[j][4:len(fullfields[j])-2])
					if last2 == "cm" {
						i, err := strconv.Atoi(middle)
						if err != nil {
							fmt.Println(err)
							os.Exit(2)
						}
						
						if i < 150 || i > 193 {
							valid = false
						}
					} else if last2 == "in" {
						i, err := strconv.Atoi(middle)
						if err != nil {
							fmt.Println(err)
							os.Exit(2)
						}
						if i < 59 || i > 76 {
							valid = false
						}
					} else {
						valid = false
					}			
				}

				if string(fullfields[j][0:3]) == "hcl" {
					r, _ := regexp.Compile("#[0-9a-f]{6}")
					if !r.MatchString(string(fullfields[j][4:])) {
						valid = false
					}
				}

				if string(fullfields[j][0:3]) == "ecl" {
					if string(fullfields[j][4:]) != "amb" && string(fullfields[j][4:]) != "blu" && string(fullfields[j][4:]) != "brn" && string(fullfields[j][4:]) != "gry" && string(fullfields[j][4:]) != "grn" && string(fullfields[j][4:]) != "hzl" && string(fullfields[j][4:]) != "oth"  {
						valid = false
					}
				}

				if string(fullfields[j][0:3]) == "pid" {
					r, _ := regexp.Compile("[0-9]{9}")
					if !r.MatchString(string(fullfields[j][4:])) {
						valid = false
					}

				}

			}
			if valid {
				validCount++
				fmt.Println("VALID")
			} else {
				fmt.Println("INVALID")
			}
		} else if len(data) == 7 {
			if !Contains(data, "cid") {
				for j := 0; j < len(data); j++ {
					if string(fullfields[j][0:3]) == "byr" {
						i, err := strconv.Atoi(string(fullfields[j][4:]))
						if err != nil {
							fmt.Println(err)
							os.Exit(2)
						}
						if i < 1920 || i > 2002 {
							valid = false
						}
						
					}
	
					if string(fullfields[j][0:3]) == "iyr" {
						i, err := strconv.Atoi(string(fullfields[j][4:]))
						if err != nil {
							fmt.Println(err)
							os.Exit(2)
						}
						if i < 2010 || i > 2020 {
							valid = false
						}
						
					}
	
					if string(fullfields[j][0:3]) == "eyr" {

						i, err := strconv.Atoi(string(fullfields[j][4:len(fullfields[j])]))
						if err != nil {
							valid = false
						}
						if i < 2020 || i > 2030 {
							valid = false
						}
						
					}
	
					if string(fullfields[j][0:3]) == "hgt" {
						last2  := string(fullfields[j][len(fullfields[j])-2:])
						middle := string(fullfields[j][4:len(fullfields[j])-2])
						if last2 == "cm" {
							i, err := strconv.Atoi(middle)
							if err != nil {
								fmt.Println(err)
								os.Exit(2)
							}
							if i < 150 || i > 193 {
								valid = false
							}
						} else if last2 == "in" {
							i, err := strconv.Atoi(middle)
							if err != nil {
								fmt.Println(err)
								os.Exit(2)
							}
							if i < 59 || i > 76 {
								valid = false
							}
						} else {
							valid = false
						}			
					}
	
					if string(fullfields[j][0:3]) == "hcl" {
						r, _ := regexp.Compile("#[0-9a-f]{6}")
						if !r.MatchString(string(fullfields[j][4:])) {
							valid = false
						}
					}
	
					if string(fullfields[j][0:3]) == "ecl" {
						if string(fullfields[j][4:]) != "amb" && string(fullfields[j][4:]) != "blu" && string(fullfields[j][4:]) != "brn" && string(fullfields[j][4:]) != "gry" && string(fullfields[j][4:]) != "grn" && string(fullfields[j][4:]) != "hzl" && string(fullfields[j][4:]) != "oth"  {
							valid = false
						}
					}
	
					if string(fullfields[j][0:3]) == "pid" {
						r, _ := regexp.Compile("[0-9]{9}")
						if !r.MatchString(string(fullfields[j][4:])) {
							valid = false
						}
					}
	
				}
				if valid {
					validCount++
					fmt.Println("VALID")
				} else {
					fmt.Println("INVALID")
				}
		} else {
			fmt.Println("INVALID")
		}

	} else {
		fmt.Println("INVALID")
	}
	

	}
	fmt.Println(validCount)
}

func Contains(a []string, x string) bool {
    for _, n := range a {
        if x == n {
            return true
        }
    }
    return false
}