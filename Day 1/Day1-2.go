package main

import (
    "io"
    "fmt"
    "log"
    "os"
)

func main() {
	file, err := os.Open("input.txt")

    if err != nil {
        log.Fatal(err)
    }

	defer file.Close()
	
	var perline int
	var nums []int

	for {

		_, err := fmt.Fscanf(file, "%d\n", &perline)

		if err != nil {

				if err == io.EOF {
					break
				}
				fmt.Println(err)
				os.Exit(1)
		}

		nums = append(nums, perline)
}

for i:=0; i<len(nums); i++ {
	for j:=0; j<len(nums); j++ {
		for k:=0; k<len(nums); k++ {
			if i == j || i == k || j == k{
				continue
			}
			if nums[i] + nums[j] + nums[k] == 2020 {
				fmt.Println(nums[i] * nums[j] * nums[k])
				return
			}
		}
	}
}

}