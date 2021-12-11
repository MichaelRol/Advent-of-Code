//
//  Day4.swift
//  
//  Input is a series of 5x5 bingo grids and a list of numbers to be called out.
//
//  Part 1 is to find the first board that will win, and then return its score equal to the sum of all unmarked numbers
//  multiplied by the number that was called that won the game.
//
//  Part 2 is to find the board that will win last.
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
        // process input
        for tableString in data {
            var table: [[String]] = []
            let lines = tableString.components(separatedBy: CharacterSet.newlines)
            for line in lines {
                table.append(line.components(separatedBy: " ").filter{ $0 != ""})
            }
            tables.append(table)
        }
        for num in nums {
            // check if num is present in each square of each board, mark with X if so
            for t in 0...tables.count - 1 {
                for r in 0...tables[t].count - 1 {
                    for c in 0...tables[t][r].count - 1 {
                        if tables[t][r][c] == num {
                            tables[t][r][c] = "X"
                        }
                    }
                }
                // Check if game has been won, if so return score
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
                // keep track of boards that have already won, until the number won = the total number
                // then return the score of the last board to win
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
