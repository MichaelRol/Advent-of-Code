//
//  InputReader.swift
//  
//
//  Created by Michael Rollins on 01/12/2021.
//

import Foundation

public class InputReader {
    let inputFile: String
    
    public init(input: String) {
        var aocDir: String?
        if let dir = ProcessInfo.processInfo.environment["AOC_DIR"] {
            aocDir = dir + "/"
        } else {
            aocDir = "."
        }
        inputFile = "\(aocDir!)/Sources/Inputs/\(input).txt"
        print(inputFile)
    }
    
    public func toString2dArray(delim : String = " ", trimBlank: Bool = true) -> [[String]] {
        do {
            let text = try String(contentsOfFile: inputFile, encoding: String.Encoding.utf8)
            var lines = text.components(separatedBy: CharacterSet.newlines)
            if trimBlank {
                lines = lines.filter { $0 != "" }
            }
            var array = [[String]]()
            for line in lines {
                array.append(line.components(separatedBy: delim))
            }
            return array
        } catch {
            fatalError("Cannot load file \(inputFile)")
        }
    }
    
    public func toStringArray(trimBlank: Bool = true) -> [String] {
        do {
            let text = try String(contentsOfFile: inputFile, encoding: String.Encoding.utf8)
            var lines = text.components(separatedBy: CharacterSet.newlines)
            if trimBlank {
                lines = lines.filter { $0 != "" }
            }
            return lines
        } catch {
            fatalError("Cannot load file \(inputFile)")
        }
    }
    
    public func toString() -> String {
        return toStringArray().first!.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines)
    }
}
