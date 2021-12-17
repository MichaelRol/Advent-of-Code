//
//  Day15.swift
//  
//  Input is grid of numbers representing risk levels, aim of Part 1 is to move from top right to bottom left
//  whilst generating the minimum risk. Solution is essentially Dijkstra's.
//
//  Part 2 is where the grid now repeats in 25 times in a 5x5 drid - with the risk numbers being increased by one every step
//  away from the original grid. Risk numbers are capped at 9 and loop back round to 1.
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
                prev[x * (data.count) + y] = nil
            }
        }
        dist[0] = 0
        unvisited.append(0)
        lengths[0] = 0
        var u = 0
        while unvisited.count != 0 {
            unvisited = unvisited.filter{$0 != u}
            let neighs = [-1, 1, data.count, -(data.count)]
            for neigh in neighs {
                if (neigh == 1 && u % data.count == data.count - 1) || (neigh == -1 && u % data.count == 0)
                    || (neigh == data.count && u >= data.count * (data.count - 1)) || (neigh == -data.count && u < data.count) {
                    continue
                }
                let neighbour = u+neigh
                let newLength = dist[u]! + lengths[neighbour]!
                if newLength < dist[neighbour]! {
                    unvisited.append(neighbour)
                    dist[neighbour] = newLength
                    prev[neighbour] = u
                }
            }
            if unvisited.count != 0 {
                u = unvisited.min(by: { (a, b) -> Bool in
                    return dist[a]! < dist[b]!
                })!
            }
        }
        return String(dist[(data.count) ^^ 2 - 1]!)
    }
    
    public func part2() -> String {
        // process input
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        var lengths: [Int: Int] = [:]
        var dist: [Int: Int] = [:]
        var unvisited: [Int] = []
        let maxSize = data.count*5
        let dest = (maxSize ^^ 2) - 1
        for r in 0...4 {
            for c in 0...4 {
                for x in 0...data.count - 1 {
                    for y in 0...data[0].count - 1 {
                        let pos = x * maxSize + y + (data.count*c) + (maxSize * data.count * r)
                        lengths[pos] = ((Int(data[x][y])! + r + c - 1) % 9) + 1
                        dist[pos] = Int.max
                    }
                }
            }
        }
        dist[0] = 0
        unvisited.append(0)
        lengths[0] = 0
        var u = 0
        while unvisited.count != 0 {
            unvisited = unvisited.filter{$0 != u}
            let neighs = [-1, 1, maxSize, -maxSize]
            for neigh in neighs {
                if (neigh == 1 && u % maxSize == maxSize - 1) || (neigh == -1 && u % maxSize == 0)
                    || (neigh == maxSize && u >= maxSize * (maxSize - 1)) || (neigh == -maxSize && u < maxSize) {
                    continue
                }
                let neighbour = u+neigh
                let newLength = dist[u]! + lengths[neighbour]!
                if newLength < dist[neighbour]! {
                    unvisited.append(neighbour)
                    dist[neighbour] = newLength
                }
            }
            if unvisited.count != 0 {
                u = unvisited.min{dist[$0]! < dist[$1]!}!
            }
            if (u == dist[dest]!) {
                break
            }
        }
        return String(dist[dest]!)
    }
}
