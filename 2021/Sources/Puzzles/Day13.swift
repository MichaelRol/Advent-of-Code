//
//  Day13.swift
//
//  Input is a list of coordinates of points on a piece of paper, followed by a number of axis along which
//  the paper is to be folded. Coords that match when folded merge into one.
//
//  Part 1 is to find the number of coords remaining when the paper is folded along first axis.
//  Part 2 was to complete all folds and display points in such a way that you can read off 8 letters from it.
//
//  Created by Michael Rollins on 13/12/2021.
//

import Foundation
import Utils

public class Day13 : Puzzle {
    
    let inputFile: String
    
    public init(inputFile: String) {
        self.inputFile = inputFile
    }
    
    public func part1() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toString()
        // Process input
        let points = data.components(separatedBy:  "\n\n")[0].components(separatedBy: "\n")
        let folds = data.components(separatedBy:  "\n\n")[1].components(separatedBy: "\n")
        var newPoints: [[Int]] = []
        let foldString = folds[0]
        
        // Work out which axis the fold is along
        var direction: Character
        if foldString.hasPrefix("fold along x") {
            direction = "x"
        } else {
            direction = "y"
        }
        let foldPoint = Int(foldString.split(separator: "=")[1])!
        for point in points {
            let coords: [Int] = point.split(separator: ",").map {Int($0)!}
            if direction == "x" {
                // if a point is before the fold point, leave it as is
                if coords[0] < foldPoint {
                    if !newPoints.contains(coords) {
                        newPoints.append(coords)
                    }
                } else { // otherwise calculate its new position and add it if that point hasn't already been stored
                    let dist = coords[0] - foldPoint
                    let newCoord = [foldPoint - dist, coords[1]]
                    if !newPoints.contains(newCoord) {
                        newPoints.append(newCoord)
                    }
                }
            } else {
                if coords[1] < foldPoint {
                    if !newPoints.contains(coords) {
                        newPoints.append(coords)
                    }
                } else {
                    let dist = coords[1] - foldPoint
                    let newCoord = [coords[0], foldPoint - dist]
                    if !newPoints.contains(newCoord) {
                        newPoints.append(newCoord)
                    }
                }
            }
        }
        return String(newPoints.count)
    }
    
    public func part2() -> String {
        let reader = InputReader(input: inputFile)
        let data = reader.toString()
        let points = data.components(separatedBy:  "\n\n")[0].components(separatedBy: "\n")
        let folds = data.components(separatedBy:  "\n\n")[1].components(separatedBy: "\n")
        var oldPoints: [[Int]] = []
        for point in points {
            oldPoints.append(point.split(separator: ",").map{Int($0)!})
        }
        for fold in folds {
            if fold == "" {
                continue
            }
            var newPoints: [[Int]] = []
            var direction: Character
            if fold.hasPrefix("fold along x") {
                direction = "x"
            } else {
                direction = "y"
            }
            let foldPoint = Int(fold.split(separator: "=")[1])!
            for coords in oldPoints {
                if direction == "x" {
                    if coords[0] < foldPoint {
                        if !newPoints.contains(coords) {
                            newPoints.append(coords)
                        }
                    } else {
                        let dist = coords[0] - foldPoint
                        let newCoord = [foldPoint - dist, coords[1]]
                        if !newPoints.contains(newCoord) {
                            newPoints.append(newCoord)
                        }
                    }
                } else {
                    if coords[1] < foldPoint {
                        if !newPoints.contains(coords) {
                            newPoints.append(coords)
                        }
                    } else {
                        let dist = coords[1] - foldPoint
                        let newCoord = [coords[0], foldPoint - dist]
                        if !newPoints.contains(newCoord) {
                            newPoints.append(newCoord)
                        }
                    }
                }
            }
            oldPoints = newPoints
        }
        display(oldPoints)
        return String(oldPoints.count)
    }
    
    func display(_ points: [[Int]]) {
        var maxX = 0
        var maxY = 0
        // find limits of grid
        for point in points {
            if point[0] > maxX {
                maxX = point[0]
            }
            if point[1] > maxY {
                maxY = point[1]
            }
        }
        // Create grid of dots
        var toDisplay: [[Character]] = []
        for x in 0...maxX {
            toDisplay.append([])
            for _ in 0...maxY {
                toDisplay[x].append(".")
            }
        }
        // add in the points that are marked
        for point in points {
            toDisplay[point[0]][point[1]] = "#"
        }
        for row in toDisplay {
            print(row)
        }
    }
    
}
