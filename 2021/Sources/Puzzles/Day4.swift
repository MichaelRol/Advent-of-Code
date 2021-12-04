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
        var data = reader.toString().components(separatedBy: "\n\n")
        let nums = data[0].split(separator: ",")
        data = Array(data.dropFirst())
        var tables: [[[String]]] = []
        for tableString in data {
            var table: [[String]] = []
            let lines = tableString.components(separatedBy: CharacterSet.newlines)
            for line in lines {
                table.append(line.components(separatedBy: " "))
            }
            tables.append(table)
        }
        print(tables)

        return ""
    }
    
    public func part2() -> String {
        return ""
    }
}
