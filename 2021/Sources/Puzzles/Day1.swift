//
//  Day1.swift
//  
//
//  Created by Michael Rollins on 01/12/2021.
//

import Foundation
import Utils

public class Day1 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        
        var prev = Int(data[0]) ?? 0
        var count = 0
        for value in data.dropFirst() {
            if Int(value) ?? 0 > prev {
                count += 1
            }
            prev = Int(value) ?? 0
        }
        return String(count)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        
        var prevSum = Int(data[0])! + Int(data[1])! + Int(data[2])!
        var count = 0
        for (i, value) in data.enumerated(){
            if (i == data.count - 2) {
                break
            }
            let newSum = Int(value)! + Int(data[i + 1])! + Int(data[i + 2])!
            if newSum > prevSum {
                count += 1
            }
            prevSum = newSum
        }
        return String(count)
    }
}
