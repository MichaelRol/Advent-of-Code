//
//  Day4.swift
//  
//
//  Created by Michael Rollins on 04/12/2021.
//

import Foundation
import Utils

public class Day4 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        var data = reader.toStringArray(trimBlank: false).joined(separator: "\n").components(separatedBy: "\n\n")
        let nums = data[0].split(separator: ",")
        data = Array(data.dropFirst())
        var tables: [[[String]]] = []
        for tableString in data {
            var table: [[String]] = []
            let lines = tableString.components(separatedBy: CharacterSet.newlines)
            for line in lines {
                table.append(line.components(separatedBy: " ").filter{ $0 != ""})
            }
            tables.append(table)
        }
        for num in nums {
            for t in 0...tables.count - 1 {
                for r in 0...tables[t].count - 1 {
                    for c in 0...tables[t][r].count - 1 {
                        if tables[t][r][c] == num {
                            tables[t][r][c] = "X"
                        }
                    }
                }
                if (bingoCol(grid: tables[t]) || bingoRow(grid: tables[t])) {
                    return calcScore(grid: tables[t], num: String(num))
                }
            }
        }
        return "Failure"
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        var data = reader.toStringArray(trimBlank: false).joined(separator: "\n").components(separatedBy: "\n\n")
        let nums = data[0].split(separator: ",")
        data = Array(data.dropFirst())
        var tables: [[[String]]] = []
        for tableString in data {
            var table: [[String]] = []
            let lines = tableString.components(separatedBy: CharacterSet.newlines)
            for line in lines {
                table.append(line.components(separatedBy: " ").filter{ $0 != ""})
            }
            tables.append(table)
        }
        var won: [Int] = []
        for num in nums {
            for t in 0...tables.count - 1 {
                if won.contains(t) {
                    continue
                }
                for r in 0...tables[t].count - 1 {
                    for c in 0...tables[t][r].count - 1 {
                        if tables[t][r][c] == num {
                            tables[t][r][c] = "X"
                        }
                    }
                }

                if (bingoCol(grid: tables[t]) || bingoRow(grid: tables[t])) {
                    won.append(t)
                    if won.count == tables.count{
                        return calcScore(grid: tables[t], num: String(num))
                    }
                }
            }
        }
        return "Failure"
    }

    func bingoRow(grid: [[String]]) -> Bool {
        for row in grid {
            var allX = true
            for element in row {
                if element != "X"{
                    allX = false
                }
            }
            if allX {
                return true
            }
        }
        return false
    }

    func bingoCol(grid: [[String]]) -> Bool {
        for i in 0...grid[0].count - 1{
            var allX = true
            for row in grid {
                if row[i] != "X" {
                    allX = false
                }
            }
            if allX {
                return true
            }
        }
        return false
    }

    func calcScore(grid: [[String]], num: String) -> String {
        var total = 0
        for row in grid {
            for element in row {
                if element != "X" {
                    total += Int(element)!
                }
            }
        }
        return String(total * Int(num)!)
    }
}
