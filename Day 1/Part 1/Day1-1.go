package main

import (
	"fmt"
	"io"
	"log"
	"os"
	"time"
)

func main() {
	start := time.Now()
	file, err := os.Open("../input.txt")

	if err != nil {
		log.Fatal(err)
	}

	defer file.Close()

	var perline int
	var nums []int
	for {
		_, err := fmt.Fscanf(file, "%d\n", &perline) // give a patter to scan
		if err != nil {
			if err == io.EOF {
				break // stop reading the file
			}
			fmt.Println(err)
			return
		}

		nums = append(nums, perline)
	}

	for i := 0; i < len(nums); i++ {
		for j := 0; j < len(nums); j++ {
			if i == j {
				continue
			}
			if nums[i]+nums[j] == 2020 {
				fmt.Print("Answer: ")
				fmt.Println(nums[i] * nums[j])
				t := time.Now()
				elapsed := t.Sub(start)
				fmt.Print("Execution time: ")
				fmt.Println(elapsed)
				return
			}
		}
	}
}
