//
//  Day2.swift
//  
//
//  Created by Michael Rollins on 02/12/2021.
//

import Foundation
import Utils

public class Day2 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toString2dArray()
        
        var hori = 0
        var vert = 0
        for row in data {
            switch (row[0]) {
            case "forward":
                hori += Int(row[1]) ?? 0
            case "up":
                vert -= Int(row[1]) ?? 0
            case "down":
                vert += Int(row[1]) ?? 0
            default:
                print("Invalid direction input")
            }
        }
        return String(hori * vert)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toString2dArray()
        
        var hori = 0
        var vert = 0
        var aim = 0
        for row in data {
            switch (row[0]) {
            case "forward":
                hori += Int(row[1]) ?? 0
                vert += aim * (Int(row[1]) ?? 0)
            case "up":
                aim -= Int(row[1]) ?? 0
            case "down":
                aim += Int(row[1]) ?? 0
            default:
                print("Invalid direction input")
            }
        }
        return String(hori * vert)
    }
}
