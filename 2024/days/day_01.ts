import { readLines } from "../utils/input";

export function part1(rawInput: string) {
    const input = readLines(rawInput);
    const numbers = new Map();
    numbers.set("1", 1);
    numbers.set("2", 2);
    numbers.set("3", 3);
    numbers.set("4", 4);
    numbers.set("5", 5);
    numbers.set("6", 6);
    numbers.set("7", 7);
    numbers.set("8", 8);
    numbers.set("9", 9);
    return sumFirstAndLastNumbers(numbers, input);
}

export function part2(rawInput: string) {
    const input = readLines(rawInput);
    const numbers = new Map();
    numbers.set("1", 1);
    numbers.set("2", 2);
    numbers.set("3", 3);
    numbers.set("4", 4);
    numbers.set("5", 5);
    numbers.set("6", 6);
    numbers.set("7", 7);
    numbers.set("8", 8);
    numbers.set("9", 9);
    numbers.set("one", 1);
    numbers.set("two", 2);
    numbers.set("three", 3);
    numbers.set("four", 4);
    numbers.set("five", 5);
    numbers.set("six", 6);
    numbers.set("seven", 7);
    numbers.set("eight", 8);
    numbers.set("nine", 9);
    return sumFirstAndLastNumbers(numbers, input);
}

function sumFirstAndLastNumbers(numbers: Map<string, number>, input: string[]) {
    var sum = 0;
    for (var line of input) {
        line = line.toLowerCase();
        sum += 10 * findFirstNumber(line, numbers) + findLastNumber(line, numbers);
    }
    return sum;
}

function findFirstNumber(line: string, numbers: Map<string, number>): number {
    return Array.from(numbers.keys())
        .filter(num => line.includes(num))
        .map(num => [line.indexOf(num), numbers.get(num)!])
        .reduce((prev, curr) => (prev[0] < curr[0] ? prev : curr))[1];
}

function findLastNumber(line: string, numbers: Map<string, number>): number {
    return Array.from(numbers.keys())
        .filter(num => line.includes(num))
        .map(num => [line.lastIndexOf(num), numbers.get(num)!])
        .reduce((prev, curr) => (prev[0] > curr[0] ? prev : curr))[1];
}
