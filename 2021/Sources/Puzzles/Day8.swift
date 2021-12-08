//
//  Day8.swift
//
//  Puzzle about mixed up wires in 7-bit displays. Characters a-g represent the 7 segments, but we don't know which.
//  Each line of input has the 10 charaters 0-9 as codes, e.g "adeb" and then 4 output characters (again 4 of 0-9).
//
//  Part 1 is to count the number of occurences of 1, 4, 7, and 8 in the output values as these have a unique
//  number of segments so are easy to identify.
//
//  Part 2 is work out the values of all four output numbers. Then concatinate them and sum across all input rows.
//
//  Created by Michael Rollins on 07/12/2021.
//

import Foundation
import Utils

public class Day8 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        
        var count = 0
        for row in data {
            let outputs = row.split(separator: "|")[1].split(separator: " ")
            for output in outputs {
                if [2, 4, 3, 7].contains(output.count) { // these numbers represent unique lenghts of 1, 4, 7 and 9
                    count += 1
                }
            }
        }
        return String(count)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        
        var total = 0
        for fullRow in data {
            var splitRow = fullRow.split(separator: "|")
            if splitRow[0].starts(with: " ") {
                splitRow[0].removeFirst()
            }
            if splitRow[1].starts(with: " ") {
                splitRow[1].removeFirst()
            }
            let inputs = splitRow[0].components(separatedBy: CharacterSet.whitespaces) // the ten values 0-9
            let outputs = splitRow[1].components(separatedBy: CharacterSet.whitespaces) // the four outputted characters
            
            var nums: [String] = ["", "", "", "", "", "", "", "", "", ""]
            
            // the four easy ones
            nums[1] = inputs.filter {$0.count == 2}[0]
            nums[4] = inputs.filter {$0.count == 4}[0]
            nums[7] = inputs.filter {$0.count == 3}[0]
            nums[8] = inputs.filter {$0.count == 7}[0]

            let lengthFive = findInputsWithLength(length: 5, inputs: inputs)
            let lengthSix = findInputsWithLength(length: 6, inputs: inputs)
            
            // Middle character can be found as the only common segment of 4 and the three 5 segment numbers (2, 3, 5)
            var toFindMiddle = lengthFive
            toFindMiddle.append(nums[4])
            let middle = findCommonLetters(inputs: toFindMiddle)[0]

            nums[0] = lengthSix.filter {!$0.contains(middle)}[0] // 0 is the only six segment missing the middle charater
            nums[9] = lengthSix.filter{ findCommonLetters(inputs: [$0, nums[4]]).count == nums[4].count}[0] // 9 is the only six segment number which shares all characters with 4
            nums[6] = lengthSix.filter {$0 != nums[0]}.filter {$0 != nums[9]}[0] // the only left over 6 segment possibility

            let topRight = findMissingLetters(input: nums[6])[0] // find the only missing segment in six (Top Right)
            nums[5] = lengthFive.filter { !$0.contains(topRight)}[0] // 5 is the only five segment number missing the top right segment
            nums[3] = lengthFive.filter {findCommonLetters(inputs: [$0, nums[1]]).count == nums[1].count}[0] // three is the only five segment letter than has all of 1s segments
            nums[2] = lengthFive.filter{ $0 != nums[5]}.filter { $0 != nums[3] }[0] // the last five segment number
            
            // sum the value of the output numbers
            total += valueOfOutput(output: outputs, nums: nums)
        }
        return String(total)
    }
    
    // Find value of each digit and convert to int
    func valueOfOutput(output: [String], nums: [String]) -> Int {
        var total = 0
        for x in 0...output.count - 1 {
            let num = matchOutput(toMatch: output[x], nums: nums)
            total += num * (10 ^^ (output.count - 1 - x))
        }
        return total
    }
    
    // Find digit that an output display digit matches
    func matchOutput(toMatch: String, nums: [String]) -> Int {
        for index in 0...nums.count - 1 {
            if toMatch.count == nums[index].count && findCommonLetters(inputs: [toMatch, nums[index]]).count == nums[index].count {
                return index
            }
        }
        return -1
    }
    
    // Find characters missing from a digit, i.e. "abcde" is missing "f", and "g"
    func findMissingLetters(input: String) -> [Character] {
        var pos: [Character] = ["a", "b", "c", "d", "e", "f", "g"]
        for char in input {
            if let index = pos.firstIndex(of: char) {
                pos.remove(at: index)
            }
        }
        return pos
    }
    
    func findInputsWithLength(length: Int, inputs: [String]) -> [String] {
        return inputs.filter { $0.count == length}
    }
    
    // find common letters between a list of strings
    func findCommonLetters(inputs: [String]) -> [Character] {
        let first = inputs[0]
        var common: [Character] = []
        for x in first {
            var inAll = true
            for word in inputs {
                var inWord = false
                for y in word {
                    if x == y {
                        inWord = true
                    }
                }
                if !inWord {
                    inAll = false
                }
            }
            if inAll {
                common.append(x)
            }
        }
        return common
    }
}
