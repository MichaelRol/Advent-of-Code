//
//  Day6.swift
//  
//  Input is a list of numbers representing the number of days until a fish respawns. When it gets to 0 on the next iteration
//  it goes back to 7 days left and spawn a child with 9 days left.
//
//  Part 1 is to count the number of fish after 80 iterations, part 2 the same with 256.
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
        let data = reader.toIntArray()
        var fish = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        var six = 6 // keeps track of which index the 6-days left count is stored in
        // count number of fish at each day count
        for daysLeft in data {
            fish[daysLeft] += 1
        }
        for _ in 1...80 {
            let eight = six + 2 % 9
            fish[(six+1) % 9] += fish[mod((six - 6), 9)] // fish that were on 0 move to 7
            fish[(eight+1) % 9] = fish[mod((six - 6), 9)] // fish that were on 0 also spawn new fish on 9
            six = (six+1) % 9 // update our 6 tracker
        }
        
        return String(fish.reduce(0, +)) // sum number of fish in existance
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
