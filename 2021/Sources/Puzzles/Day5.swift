//
//  Day5.swift
//  
//
//  Created by Michael Rollins on 05/12/2021.
//

import Foundation
import Utils

public class Day5 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        var vents: [String: Int] = [:]
        for line in data {
            let coordPair = String(line).components(separatedBy: " -> ")
            let startCoords = coordPair[0].split(separator: ",")
            let endCoords = coordPair[1].split(separator: ",")
            if (startCoords[0] == endCoords[0]) {
                let yStart = Int(startCoords[1])!
                let yEnd = Int(endCoords[1])!
                let step = (yStart-yEnd)/abs(yStart-yEnd) * -1
                for y in stride(from: yStart, through: yEnd, by: step){
                    vents[String(startCoords[0]) + ", " + String(y)] = (vents[String(startCoords[0]) + ", " + String(y)] ?? 0) + 1
                }
            } else if (startCoords[1] == endCoords[1]){
                let xStart = Int(startCoords[0])!
                let xEnd = Int(endCoords[0])!
                let step = (xStart-xEnd)/abs(xStart-xEnd) * -1
                for x in stride(from: xStart, through: xEnd, by: step){
                    vents[String(x) + ", " + String(startCoords[1])] = (vents[String(x) + ", " + String(startCoords[1])] ?? 0) + 1
                }
            }
        }
        var count = 0
        print(vents)
        for (_, num) in vents {
            if num > 1 {
                count += 1
            }
        }
        return String(count)
    }
    
    public func part2() -> String {
        return ""
    }
}
