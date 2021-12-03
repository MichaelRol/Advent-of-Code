//
//  Day3.swift
//  
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
        for (i, bit) in count1s.enumerated() {
            if bit > count0s[i] {
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
