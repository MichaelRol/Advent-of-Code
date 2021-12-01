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

if CommandLine.arguments.count != 3{
    print("Please specify the day number to run and the stage of the puzzle.")
    exit(1)
}

let day: Int = Int(CommandLine.arguments[1]) ?? 0
let stage: Int = Int(CommandLine.arguments[2]) ?? 0
let input = "input" + String(day)

print("Day \(day), Part \(stage)")

var puzzle: Puzzle!

switch day {
case 1:
    puzzle = Day1(inputFile: input)
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
