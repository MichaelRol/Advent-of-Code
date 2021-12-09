//
//  Day9.swift
//
//  Input is grid of numbers representing sea level depths.
//  Part 1 is to find all the minimum points, all the points where its depth is lower than all of its NSEW neighbours.
//  Part 2 was to find the size of all the "basin" areas surrounding minimum points, themselves surrounded by "9"s, which
//  where not considered part of a basin.
//
//  Created by Michael Rollins on 08/12/2021.
//

import Foundation
import Utils

public class Day9 : Puzzle {
    
    var basinArr: [String]
    
    public init(inputFile: String) {
        let reader = InputReader(input: inputFile)
        basinArr = reader.toStringArray()
    }
    
    // Find minimums add up the total of all their depths + 1
    public func part1() -> String {
        
        let mins: [(Int, Int)] = findMins(grid: basinArr)
        
        var risk = 0
        for min in mins {
            risk += Int(basinArr[min.0][min.1])! + 1
        }
        return String(risk)
    }
    
    // Use recursive algo to fill basins, counting points as it goes
    // Store basin sizes in array and find 3 greaters, returning thier product.
    public func part2() -> String {
        let mins: [(Int, Int)] = findMins(grid: basinArr)
        var basins: [Int] = []
        for min in mins {
            basins.append(basin(pos: min))
        }
        var max1 = 0
        var max2 = 0
        var max3 = 0
        for basin in basins {
            if basin >= max1 {
                max3 = max2
                max2 = max1
                max1 = basin
            } else if basin >= max2 {
                max3 = max2
                max2 = basin
            } else if basin >= max1 {
                max3 = basin
            }
        }
        return String(max1*max2*max3)
    }
    
    // Recursively fills in basic, counts non "9" neighbours
    func basin(pos: (Int, Int)) -> Int {
        var count = 1
        basinArr[pos.0] = basinArr[pos.0].replace(pos.1, "9")
        if pos.0 < basinArr.count - 1 {
            let below = basinArr[pos.0 + 1][pos.1]
            if below != "9" {
                count += basin(pos: (pos.0 + 1, pos.1))
            }
        }
        if pos.0 > 0 {
            let below = basinArr[pos.0 - 1][pos.1]
            if below != "9" {
                count += basin(pos: (pos.0 - 1, pos.1))
            }
        }
        if pos.1 < basinArr[0].count - 1 {
            let below = basinArr[pos.0 ][pos.1 + 1]
            if below != "9" {
                count += basin(pos: (pos.0, pos.1 + 1))
            }
        }
        if pos.1 > 0 {
            let below = basinArr[pos.0][pos.1 - 1]
            if below != "9" {
                count += basin(pos: (pos.0, pos.1 - 1))
            }
        }
        return count
    }
    
    // Finds minimum points by comparing to neighbours
    func findMins(grid: [String]) -> [(Int, Int)] {
        var mins: [(Int, Int)] = []
        for x in 0...grid.count - 1 {
            for y in 0...grid[0].count - 1 {
                if (grid[x][y] == "9") {
                    continue
                }
                if (x < grid.count - 1) {
                    if Int(grid[x][y])! > Int(grid[x+1][y])! {
                        continue
                    }
                }
                if (y < grid[0].count - 1) {
                    if Int(grid[x][y])! > Int(grid[x][y+1])! {
                        continue
                    }
                }
                if (x > 0) {
                    if Int(grid[x][y])! > Int(grid[x-1][y])! {
                        continue
                    }
                }
                if (y > 0) {
                    if Int(grid[x][y])! > Int(grid[x][y-1])! {
                        continue
                    }
                }
                mins.append((x, y))
            }
        }
        return mins
    }
}
