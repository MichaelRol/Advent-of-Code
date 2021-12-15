//
//  Day15.swift
//  
//  Input is grid of numbers representing risk levels, aim of Part 1 is to move from top right to bottom left
//  whilst generating the minimum risk.
//
//  Created by Michael Rollins on 15/12/2021.
//

import Foundation
import Utils

public class Day15 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }    
    
    public func part1() -> String {
        // process input
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        var lengths: [Int: Int] = [:]
        var dist: [Int: Int] = [:]
        var prev: [Int: Int] = [:]
        var unvisited: [Int] = []
        
        for x in 0...data.count - 1 {
            for y in 0...data[0].count - 1 {
                lengths[x * (data.count) + y] = Int(data[x][y])!
                dist[x * (data.count) + y] = Int.max
                unvisited.append(x * (data.count) + y)
                prev[x * (data.count) + y] = nil
            }
        }
        dist[0] = 0
        lengths[0] = 0
        while unvisited.count != 0 {
            var u: (Int, Int) = (0, Int.max)
            for x in unvisited {
                if dist[x]! < u.1 {
                    u = (x, dist[x]!)
                }
            }
            
            print("count" + String(unvisited.count))
            print(u.0)
            unvisited = unvisited.filter{$0 != u.0}
            let neighs = [-1, 1, data.count, -(data.count)]
            for neigh in neighs {
                if (neigh == 1 && u.0 % data.count == data.count - 1) || (neigh == -1 && u.0 % data.count == 0) {
                    continue
                }
                if unvisited.filter({$0 == u.0+neigh}).count == 1 {
                    let neighbour = unvisited.filter({$0 == u.0+neigh})[0]
                    let newLength = u.1 + lengths[neighbour]!
                    if newLength < dist[neighbour]! {
                        dist[neighbour] = newLength
                        prev[neighbour] = u.0
                    }
                }
            }
        }
        return String(dist[(data.count) ^^ 2 - 1]!)
    }
    
    public func part2() -> String {
        return ""
    }
}
