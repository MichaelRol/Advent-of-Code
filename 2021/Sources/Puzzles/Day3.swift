//
//  Day3.swift
//
//  Input is list of 5-bit binary numbers (Diagnostic report)
//
//  Part 1 is to calculate submarines power consumption, defined as gamma rate * epsilon rate.
//
//  Each bit in the gamma rate can be determined by finding the most common bit in the corresponding
//  position of all numbers in the diagnostic report.
//  The epsilon rate is calculated in a similar way; rather than use the most common bit, the least
//  common bit from each position is used.
//
//  Part 2 is to find O2 rating and Co2 rating.
//
//  These are found by finding the most common bit at each bit-position (starting from the right)
//  and for the O2 rating keeping rows with that bit in the given position, and for the Co2 rating
//  disgarding those rows. You then move along the bits and repeat the process until there is a
//  single row left, this is you O2/Co2 rating.
//
//  Created by Michael Rollins on 03/12/2021.
//

import Foundation
import Utils

public class Day3 : Puzzle{
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        let (count0s, count1s) = getCounts(data: data)
        var gamma = 0
        var epsilon = 0
        for (i, count1) in count1s.enumerated() {
            // if count1 is greatest, add to gamma binary else to epsilon
            if count1 > count0s[i] {
                gamma += 2^^(count1s.count - 1 - i)
            } else {
                epsilon += 2^^(count1s.count - 1 - i)
            }
        }
        return String(gamma * epsilon)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        var (count0s, count1s) = getCounts(data: data)
        
        var o2 = data
        var x = 0
        while o2.count > 1 {
            // If there are more 1s at position x, remove all rows with 0 at this position, or vica versa
            if (count1s[x] >= count0s[x]) {
                o2 = o2.filter{ $0[x] == "1"}
            } else {
                o2 = o2.filter{ $0[x] == "0"}
            }
            (count0s, count1s) = getCounts(data: o2)
            x += 1
        }
        
        var co2 = data
        x = 0
        while co2.count > 1 {
            // If there are more 1s at position x, remove all rows with 1 at this position, or vica versa
            if (count0s[x] > count1s[x]) {
                co2 = co2.filter{ $0[x] == "1"}
            } else {
                co2 = co2.filter{ $0[x] == "0"}
            }
            (count0s, count1s) = getCounts(data: co2)
            x += 1
        }
        return String(binaryToInt(binaryString: o2[0]) * binaryToInt(binaryString: co2[0]))
    }
    
    // Count the number of 0s and 1s at each bit position
    func getCounts(data: [String]) -> ([Int], [Int]) {
        var count0s = [Int](repeating: 0, count: data[0].count)
        var count1s = [Int](repeating: 0, count: data[0].count)
        for row in data {
            for (i, bit) in row.enumerated(){
                if bit == "0" {
                    count0s[i] += 1
                } else if bit == "1" {
                    count1s[i] += 1
                } else {
                    print(bit)
                }
            }
        }
        return (count0s, count1s)
    }

}
