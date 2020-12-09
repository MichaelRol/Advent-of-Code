package main

import (
    "io/ioutil"
    "fmt"
    "log"
	"strings"
)

func main() {

	valid := 0

	content, err := ioutil.ReadFile("../input.txt")
    if err != nil {
        log.Fatal(err)
    }

	text := string(content)
	lines := strings.Split(text, "\n\n")
	
	for i := 0; i < len(lines); i++ {
		data := strings.Fields(lines[i])
		for j := 0; j < len(data); j++ {
			data[j] = string(data[j][0:3])
		}

		if len(data) == 8 {
			valid++
		} 
		if len(data) == 7 {
			if !Contains(data, "cid") {
				valid++
			}
		} 

	}
	fmt.Println(valid)


}

func Contains(a []string, x string) bool {
    for _, n := range a {
        if x == n {
            return true
        }
    }
    return false
}