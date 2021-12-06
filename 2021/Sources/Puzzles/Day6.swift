//
//  Day6.swift
//  
//
//  Created by Michael Rollins on 06/12/2021.
//

import Foundation
import Utils

public class Day6 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }

    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray(delim: CharacterSet.init(charactersIn: ",")).map{Int($0)!}
        var fish = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        var six = 6
        for daysLeft in data {
            fish[daysLeft] += 1
        }
        for _ in 1...80 {
            let eight = six + 2 % 9
            fish[(six+1) % 9] += fish[mod((six - 6), 9)]
            fish[(eight+1) % 9] = fish[mod((six - 6), 9)]
            six = (six+1) % 9
        }
        
        return String(fish.reduce(0, +))
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray(delim: CharacterSet.init(charactersIn: ",")).map{Int($0)!}
        var fish = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        var six = 6
        for daysLeft in data {
            fish[daysLeft] += 1
        }
        for _ in 1...256 {
            let eight = six + 2 % 9
            fish[(six+1) % 9] += fish[mod((six - 6), 9)]
            fish[(eight+1) % 9] = fish[mod((six - 6), 9)]
            six = (six+1) % 9
        }
        
        return String(fish.reduce(0, +))
    }
}
