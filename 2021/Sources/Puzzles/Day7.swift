//
//  Day7.swift
//
//  There's a bunch of crab submarines that can only move horizontally. We are trying to minimise the total fuel requirement
//  of all the subs to align at the same horizontal point.
//
//  In Part 1, each movement left or right uses 1 fuel, each sub has a starting point so just need to find the minimum fuel for
//  all subs to align.
//
//  Part 2 each the 1st step cost 1 fuel, 2nd step 2 fuel, 3rd step 3 fuel. Same problem as above.
//
//  Created by Michael Rollins on 07/12/2021.
//

import Foundation
import Utils

public class Day7 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toIntArray()
        let sortedData = data.sorted()
        
        // Find median position
        let middle = sortedData[Int(sortedData.count / 2)]
        
        // Find total distance of all subs from median
        var count = 0
        for value in sortedData {
            count += abs(value - middle)
        }
        
        return String(count)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toIntArray()
        
        // Find mean horizontal possition
        var total = 0
        for num in data {
            total += num
        }
        let targetDown = Int(Double(total)/Double(data.count))
        let targetUp = targetDown + 1
        
        // Sum all distances from mean rounded up and down then use the minimum
        var countUp = 0
        var countDown = 0
        for value in data {
            let distUp = abs(value - targetUp)
            let distDown = abs(value - targetDown)
            countUp += (distUp * (distUp + 1))/2
            countDown += (distDown * (distDown + 1))/2
        }
        
        return String(min(countUp, countDown))
    }
}
