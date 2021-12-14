//
//  Day14.swift
//
//  Input is a string and a number of rules for mutating the string. Each rules maps a pair of letters
//  to a single letter. Each cycle every two letter pair, has its mapped single letter inserted between the pair.
//
//  E.g If you have NNCB and NN -> C, NC -> B and, CB -> H it will become NCNBCHB
//
//  Part 1 is to do 10 iterations then return the number of the most common letter in the string minus the number
//  of the least common letter. Part 2 is to do the same over 40 cycles.
//
//  Created by Michael Rollins on 14/12/2021.
//

import Foundation
import Utils

public class Day14 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        // process input
        let reader = InputReader(input: inputFile)
        var data = reader.toStringArray()
        let inputCode = data[0]
        data = Array(data.dropFirst())
        var rules: [String: [String]] = [:]
        var ruleCounts: [String: Int] = [:]
        var letterCounts: [String: Int] = [:]
        
        // move rules into map (map single pair, to the two pairs it creates)
        for row in data {
            let parts = row.components(separatedBy: " -> ")
            ruleCounts[parts[0]] = 0
            rules[parts[0]] = [parts[0][0] + parts[1], parts[1] + parts[0][1]]
        }
        
        // count number of each pair in initial string
        for i in 0...inputCode.count - 2 {
            ruleCounts[inputCode[i..<i+2]]! += 1
        }
        
        // run cycles
        for _ in 0...9 {
            var newCounts: [String: Int] = ruleCounts.mapValues{value in return 0}
            // each iteration take each of the two pairs the original pair maps to and increase their count
            // by the number of the original pair. d
            for (x, y) in ruleCounts {
                newCounts[rules[x]![0]]! += y
                newCounts[rules[x]![1]]! += y
            }
            ruleCounts = newCounts
        }
        
        // count number of each letters present across all ruleCounts
        for (rule, count) in ruleCounts {
            letterCounts[rule[0]] = count + (letterCounts[rule[0]] ?? 0)
            letterCounts[rule[1]] = count + (letterCounts[rule[1]] ?? 0)
        }
        
        // find max and min most common letters
        var max = 0
        var min = Int.max
        var maxL = ""
        var minL = ""
        for (lett, value) in letterCounts {
            if value > max {
                max = value
                maxL = lett
            }
            if value < min {
                min = value
                minL = lett
            }
        }
        
        // minor adjustments for OBOB errors cause by edge case where the string starts/ends with most/least common letter.
        if (maxL == inputCode[0] || maxL == inputCode[inputCode.count-1]) {
            max += 1
        }
        if (minL == inputCode[0] || minL == inputCode[inputCode.count-1]) {
            min += 1
        }
        return String((max-min)/2)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        var data = reader.toStringArray()
        let inputCode = data[0]
        data = Array(data.dropFirst())
        var rules: [String: [String]] = [:]
        var ruleCounts: [String: Int] = [:]
        var letterCounts: [String: Int] = [:]
        for row in data {
            let parts = row.components(separatedBy: " -> ")
            ruleCounts[parts[0]] = 0
            rules[parts[0]] = [parts[0][0] + parts[1], parts[1] + parts[0][1]]
        }
        for i in 0...inputCode.count - 2 {
            ruleCounts[inputCode[i..<i+2]]! += 1
        }
        for _ in 0...39 {
            var newCounts: [String: Int] = ruleCounts.mapValues{value in return 0}
            for (x, y) in ruleCounts {
                newCounts[rules[x]![0]]! += y
                newCounts[rules[x]![1]]! += y
            }
            ruleCounts = newCounts
        }
        for (rule, count) in ruleCounts {
            letterCounts[rule[0]] = count + (letterCounts[rule[0]] ?? 0)
            letterCounts[rule[1]] = count + (letterCounts[rule[1]] ?? 0)
        }
        var max = 0
        var min = Int.max
        var maxL = ""
        var minL = ""
        for (lett, value) in letterCounts {
            if value > max {
                max = value
                maxL = lett
            }
            if value < min {
                min = value
                minL = lett
            }
        }
        if (maxL == inputCode[0] || maxL == inputCode[inputCode.count-1]) {
            max += 1
        }
        if (minL == inputCode[0] || minL == inputCode[inputCode.count-1]) {
            min += 1
        }
        
        return String((max-min)/2)
    }
}
