//
//  File.swift
//  
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
        
        let middle = sortedData[Int(sortedData.count / 2)]
        
        var count = 0
        for value in sortedData {
            count += abs(value - middle)
        }
        
        return String(count)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toIntArray()
        
        var total = 0
        for num in data {
            total += num
        }
        let targetDown = Int(Double(total)/Double(data.count))
        let targetUp = targetDown + 1
        
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
