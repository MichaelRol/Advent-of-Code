//
//  Day10.swift
//
//  Intput is a series of lines of different brackets.
//  Part 1 is to find first non-matching bracket in line, take a score for that bracket from a list and sum across as lines.
//
//
//  Created by Michael Rollins on 10/12/2021.
//

import Foundation
import Utils

public class Day10 : Puzzle {
    
    let inputFile: String
    
    let closeScoreMap = [")": 3, "]": 57, "}": 1197, ">": 25137]
    let openScoreMap = ["(": 1, "[": 2, "{": 3, "<": 4]
    let bracketMap = [")": "(", "}": "{", "]": "[", ">": "<"]
    let open = ["(", "{", "[", "<"]
    let close = [")", "}", "]", ">"]
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        
        var score = 0
        for line in data {
            let lineArr = line.map(String.init)
            var stack = Stack()
            for bracket in lineArr {
                // if an opening bracket push onto stack, if closing pop stack
                if open.contains(bracket) {
                    stack.push(bracket)
                } else if close.contains(bracket) {
                    let poppedBracket = stack.pop()
                    // If non-matching bracket - look up score
                    if poppedBracket != bracketMap[bracket] ?? "" {
                        score += closeScoreMap[bracket]!
                        break
                    }
                }
            }
        }
        return String(score)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        var scores: [Int] = []
        for line in data {
            var valid = true
            let lineArr = line.map(String.init)
            var stack = Stack()
            for bracket in lineArr {
                if open.contains(bracket) {
                    stack.push(bracket)
                } else if close.contains(bracket) {
                    let poppedBracket = stack.pop()
                    if poppedBracket != bracketMap[bracket] ?? "" {
                        valid = false
                        break
                    }
                }
            }
            if valid {
                var score = 0
                // pop remaining brackets from stack, tallying score as it goes
                while !stack.empty() {
                    let bracket = stack.pop()
                    score *= 5
                    score += openScoreMap[bracket]!
                }
                scores.append(score)
            }
        }
        scores.sort()
        return String(scores[scores.count/2])
    }
}
