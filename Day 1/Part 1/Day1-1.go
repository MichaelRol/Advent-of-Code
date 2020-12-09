package main

import (
    "io"
    "fmt"
    "log"
    "os"
)

func main() {
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
				os.Exit(1)
		}

		nums = append(nums, perline)
}

for i:=0; i<len(nums); i++ {
	for j:=0; j<len(nums); j++ {
		if i == j {
			continue
		}
		if nums[i] + nums[j] == 2020 {
			fmt.Println(nums[i] * nums[j])
			return
		}
	}
}

}