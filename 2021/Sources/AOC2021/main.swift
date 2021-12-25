//
//  main.swift
//
//
//  Created by Michael Rollins on 01/12/2021.
//

import Foundation
import Utils
import Puzzles

let start = Date()

var test = false
if CommandLine.arguments.count == 4 && CommandLine.arguments[3] == "test" {
    test = true
}

if CommandLine.arguments.count != 3 && !test {
    print("Please specify the day number to run and the stage of the puzzle (optional: add \"test\" to use test file.)")
    exit(1)
}

let day: Int = Int(CommandLine.arguments[1]) ?? 0
let stage: Int = Int(CommandLine.arguments[2]) ?? 0
var input = "input" + String(day)
if test {
    input = "test" + String(day)
}

print("Day: \(day), Part: \(stage), Test: \(test)")

var puzzle: Puzzle!

switch day {
case 1:
    puzzle = Day1(inputFile: input)
case 2:
    puzzle = Day2(inputFile: input)
case 3:
    puzzle = Day3(inputFile: input)
case 4:
    puzzle = Day4(inputFile: input)
case 5:
    puzzle = Day5(inputFile: input)
case 6:
    puzzle = Day6(inputFile: input)
case 7:
    puzzle = Day7(inputFile: input)
case 8:
    puzzle = Day8(inputFile: input)
case 9:
    puzzle = Day9(inputFile: input)
case 10:
    puzzle = Day10(inputFile: input)
case 11:
    puzzle = Day11(inputFile: input)
case 12:
    puzzle = Day12(inputFile: input)
case 13:
    puzzle = Day13(inputFile: input)
case 14:
    puzzle = Day14(inputFile: input)
case 15:
    puzzle = Day15(inputFile: input)
case 25:
    puzzle = Day25(inputFile: input)
default:
    print("Cannot load puzzle for Day \(day)")
    exit(1)
}

var answer = "???"

switch stage {
case 1:
    answer = puzzle.part1()
case 2:
    answer = puzzle.part2()
default:
    print("Cannot find Stage \(stage) for Day \(day)")
    exit(1)
}

let end = Date()

let interval = end.timeIntervalSince(start)

print("Answer: \(answer)")
print("Took \(interval) seconds")
