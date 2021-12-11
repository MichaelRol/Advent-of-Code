//
//  Day11.swift
//
//  Input is a grid of numbers representing the energy levels of some flashing octopus which flash
//  once their energy level goes above 9, which inturn increases its neighbours energy levels by 1.
//  All octopus gain 1 energy level per round.
//
//  Part 1 counts the number of flashes in the first 100 rounds.
//  Part 2 is to find the frst round at which all octupus flash at the same time.
//
//  Created by Michael Rollins on 11/12/2021.
//

import Foundation
import Utils

public class Day11 : Puzzle {
    
    var data: [[Int]]
    var flashCount = 0
    var roundFlashCount = 0
    
    public init(inputFile: String) {
        let reader = InputReader(input: inputFile)
        self.data =  reader.toInt2dArray()
    }
    
    public func part1() -> String {
        for _ in 0...99 {
            for row in 0...data.count - 1 {
                for col in 0...data[0].count - 1 {
                    data[row][col] += 1
                    if (data[row][col] == 10) {
                        flash(row, col)
                    }
                }
            }
            for row in 0...data.count - 1 {
                for col in 0...data[0].count - 1 {
                    if (data[row][col] >= 10) {
                        data[row][col] = 0
                    }
                }
            }
        }
        return String(flashCount)
    }
    
    public func part2() -> String {
        var roundCount = 0
        while roundFlashCount != data.flatMap({ $0 }).count {
            roundCount += 1
            roundFlashCount = 0
            for row in 0...data.count - 1 {
                for col in 0...data[0].count - 1 {
                    data[row][col] += 1
                    if (data[row][col] == 10) {
                        flash(row, col)
                    }
                }
            }
            for row in 0...data.count - 1 {
                for col in 0...data[0].count - 1 {
                    if (data[row][col] >= 10) {
                        data[row][col] = 0
                    }
                }
            }
        }
        return String(roundCount)
    }
    
    private func flash(_ row: Int, _ col: Int) {
        flashCount += 1
        roundFlashCount += 1
        for x in -1...1 {
            for y in -1...1 {
                if (row + x >= 0 && row + x < data.count && col + y >= 0 && col + y < data[0].count && !(x == 0 && y == 0)) {
                    data[row+x][col+y] += 1
                    if (data[row+x][col+y] == 10) {
                        flash(row+x, col+y)
                    }
                }
            }
        }
    }
    
    // Just used for testing purposes
    private func display(_ something: [[Int]]) {
        for row in something {
            print(row.map {String($0)}.joined())
        }
        print()
    }
}
