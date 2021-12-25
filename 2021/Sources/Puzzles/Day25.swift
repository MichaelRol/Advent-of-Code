//
//  Day25.swift
//  
//
//  Created by Michael Rollins on 25/12/2021.
//

import Foundation
import Utils

public class Day25 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        // process input
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        var old: [[Character]] = []
        for line in data {
            old.append(Array(line))
        }
        var new: [[Character]] = []
        var count = 0
        while !arrayEquals(old, new) {
            count += 1
            if new != [] {
                old = new
            }
            var temp = old
            
            // move right facing cucumbers
            for r in 0...old.count - 1 {
                for c in 0...old[0].count - 1{
                    if old[r][c] == ">" {
                        if old[r][(c+1) % old[0].count] == "." {
                            temp[r][c] = "."
                            temp[r][(c+1) % old[0].count] = ">"
                        }
                    }
                }
            }
            new = temp
            
            // move downwards facing cucumbers
            for r in 0...old.count - 1 {
                for c in 0...old[0].count - 1{
                    if temp[r][c] == "v" {
                        if temp[(r+1) % old.count][c] == "." {
                            new[r][c] = "."
                            new[(r+1) % old.count][c] = "v"
                        }
                    }
                }
            }
        }
        return String(count)
    }
    
    public func part2() -> String {
        return ""
    }
    
    // for displaying the grid, for testing purpose only
    private func display(_ arr: [[Character]]) {
        for r in 0...arr.count - 1 {
            for c in 0...arr[0].count - 1{
                print(arr[r][c], terminator: "")
            }
            print()
        }
    }
    
    // compares if two arrays are equal
    private func arrayEquals(_ old: [[Character]], _ new: [[Character]]) -> Bool {
        if old.count != new.count || old[0].count != new[0].count {
            return false
        }
        for r in 0...old.count - 1 {
            for c in 0...old[0].count - 1{
                if old[r][c] != new[r][c] {
                    return false
                }
            }
        }
        return true
    }
}
