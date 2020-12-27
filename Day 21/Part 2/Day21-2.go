package main

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"sort"
	"strings"
	"time"
)

func main() {
	start := time.Now()
	ingredientList, allergensList := readInput("../input.txt")

	_, allergens := findIngrediantAndAllergens(ingredientList, allergensList)
	part2Answer := produceIngrediantList(allergens)
	fmt.Print("Answer: ")
	fmt.Println(part2Answer)
	t := time.Now()
	elapsed := t.Sub(start)
	fmt.Print("Execution time: ")
	fmt.Println(elapsed)
}

func produceIngrediantList(allergens map[string]string) string {
	mapAllergensToIngrediants := make(map[string]string)
	var listOfAllergens []string

	for i, a := range allergens {
		mapAllergensToIngrediants[a] = i
		listOfAllergens = append(listOfAllergens, a)
	}

	sort.Strings(listOfAllergens)
	var buffer bytes.Buffer

	for _, a := range listOfAllergens {
		buffer.WriteString(mapAllergensToIngrediants[a])
		buffer.WriteString(",")
	}

	answer := buffer.String()
	answer = answer[:len(answer)-1]

	return answer
}

func findIngrediantAndAllergens(ingredientList, allergensList [][]string) (ingredients []string, allergens map[string]string) {
	possibleAllergens := make(map[string][]string)
	allergens = make(map[string]string)
	for x := 0; x < len(ingredientList); x++ {
		for _, allergen := range allergensList[x] {
			_, ok := possibleAllergens[allergen]
			if ok {
				possibleAllergens[allergen] = findSharedMembers(possibleAllergens[allergen], ingredientList[x])
			} else {
				possibleAllergens[allergen] = ingredientList[x]
			}
		}
		ingredients = append(ingredients, ingredientList[x]...)
	}

	for len(possibleAllergens) > 0 {
		for allergen, ingredients := range possibleAllergens {
			if len(ingredients) == 1 {
				allergens[ingredients[0]] = allergen
				possibleAllergens = deleteIngredient(possibleAllergens, ingredients[0])
				delete(possibleAllergens, allergen)
				break
			}
		}
	}

	return ingredients, allergens
}

func deleteIngredient(possibleAllergens map[string][]string, ingredient string) map[string][]string {
	for allergen, otherIngredients := range possibleAllergens {
		var newList []string

		for _, i := range otherIngredients {
			if i != ingredient {
				newList = append(newList, i)
			}
		}
		possibleAllergens[allergen] = newList
	}
	return possibleAllergens
}

func findSharedMembers(setA, setB []string) (sharedMembers []string) {
	for _, a := range setA {
		for _, b := range setB {
			if a == b {
				sharedMembers = append(sharedMembers, a)
			}
		}
	}

	return sharedMembers
}

func readInput(filename string) (ingredients, allergens [][]string) {
	content, err := ioutil.ReadFile(filename)
	if err != nil {
		log.Fatal(err)
		os.Exit(2)
	}

	text := string(content)
	lines := strings.Split(text, "\n")

	for _, line := range lines {
		splitIandA := strings.Split(line, " (contains ")
		i := strings.Split(splitIandA[0], " ")
		ingredients = append(ingredients, i)

		a := strings.Split(splitIandA[1][:len(splitIandA[1])-1], ", ")
		allergens = append(allergens, a)
	}

	return ingredients, allergens
}
