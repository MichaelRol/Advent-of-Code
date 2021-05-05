package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"sort"
	"strconv"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	ratings := readNumbers("../input.txt")

	// Sort adapters, after adding outlet and your built-in adapter
	ratings = append(ratings, 0)
	sort.Ints(ratings)
	ratings = append(ratings, ratings[len(ratings)-1]+3)

	graph := createGraph(ratings)
	cache := make(map[int]int64)

	fmt.Print("Answer: ")
	fmt.Println(transverse(ratings[0], graph, cache))
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

// Transverse graph to find all ways in which your charger can connect to the outlet (0 jolts)
func transverse(node int, graph map[int][]int, cache map[int]int64) int64 {
	var count int64 = 0
	if len(graph[node]) == 0 {
		return 1
	}
	for _, nextNode := range graph[node] {
		if cache[nextNode] == 0 {
			count += transverse(nextNode, graph, cache)
		} else {
			count += cache[nextNode]
		}
	}
	cache[node] = count

	return count
}

// Create a graph will each adapter connected to all adapters that it can connect to (within 3 jolts)
func createGraph(ratings []int) map[int][]int {
	graph := make(map[int][]int)

	for i, rating := range ratings {
		var nexts []int
		for _, j := range []int{1, 2, 3} {
			if i+j < len(ratings) {
				if ratings[i+j]-rating <= 3 {
					nexts = append(nexts, ratings[i+j])
				}
			}
		}
		graph[rating] = nexts
	}

	return graph
}

func readNumbers(filename string) []int {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	lines := strings.Split(text, "\n")

	var numbers []int
	for _, line := range lines {
		if line == "" {
			continue
		}
		i, err := strconv.Atoi(line)
		if err != nil {
			fmt.Println(err)
			os.Exit(2)
		}
		numbers = append(numbers, i)
	}

	return numbers
}
