//
//  Day12.swift
//  
//  Input is a a series of connections between "caves"
//  Part 1 is to find all paths the "start" cave to the "end" cave, only visiting small caves (lowercase names) once.
//  Part 2 allows one small cave to be visited twice.
//
//  Created by Michael Rollins on 12/12/2021.
//

import Foundation
import Utils

public class Day12 : Puzzle {
    
    let inputFile: String
    var allCaves: [String: Cave]
    var total: Int
    
    public init(inputFile: String) {
        self.inputFile = inputFile
        self.allCaves = [:]
        self.total = 0
    }
    
    public func part1() -> String {
        total = 0
        self.allCaves = [:]
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        for row in data {
            let caves = row.split(separator: "-")
            for caveSubstring in caves {
                let caveString = String(caveSubstring)
                if allCaves[String(caveString)] == nil {
                    allCaves[caveString] = Cave(caveString[0].uppercased() == caveString[0], caveString)
                }
            }
            allCaves[String(caves[0])]!.addCave(allCaves[String(caves[1])]!)
            allCaves[String(caves[1])]!.addCave(allCaves[String(caves[0])]!)
        }
        transverse(allCaves["start"]!, [])
        return String(total)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toStringArray()
        total = 0
        self.allCaves = [:]
        for row in data {
            let caves = row.split(separator: "-")
            for caveSubstring in caves {
                let caveString = String(caveSubstring)
                if allCaves[String(caveString)] == nil {
                    allCaves[caveString] = Cave(caveString[0].uppercased() == caveString[0], caveString)
                }
            }
            allCaves[String(caves[0])]!.addCave(allCaves[String(caves[1])]!)
            allCaves[String(caves[1])]!.addCave(allCaves[String(caves[0])]!)
        }
        transverse2(allCaves["start"]!, [], nil)
        return String(total)
    }
    
    private func transverse(_ cave: Cave, _ history: [String]) {
        for nextCave in cave.getCaves() {
            var newHistory = history
            newHistory.append(cave.getName())
            if (nextCave.getName() == "end") {
                total += 1
                newHistory.append("end")
            } else if !newHistory.contains(nextCave.getName()) || nextCave.isBig() {
                transverse(allCaves[nextCave.getName()]!, newHistory)
            }
        }
    }
    
    private func transverse2(_ cave: Cave, _ history: [String], _ smallCaveVisited: String?) {
        for nextCave in cave.getCaves() {
            var newHistory = history
            newHistory.append(cave.getName())
            if (nextCave.getName() == "end") {
                total += 1
                newHistory.append("end")
            } else if newHistory.contains(nextCave.getName()) && !nextCave.isBig() {
                if smallCaveVisited == nil && !["start", "end"].contains(nextCave.getName()) {
                    transverse2(allCaves[nextCave.getName()]!, newHistory, nextCave.getName())
                }
            } else {
                transverse2(allCaves[nextCave.getName()]!, newHistory, smallCaveVisited)
            }
        }
    }
}

struct Cave {
    var nodes: [Cave]
    let big: Bool
    let name: String
    
    init(_ big: Bool, _ name: String) {
        self.big = big
        self.name = name
        self.nodes = []
    }
    
    func isBig() -> Bool {
        return big
    }
    
    func getCaves() -> [Cave] {
        return nodes
    }
    
    func getName() -> String {
        return name
    }
    
    mutating func addCave(_ cave: Cave) {
        nodes.append(cave)
    }
}
