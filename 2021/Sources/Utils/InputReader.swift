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
    
    public func toInt2dArray(delim : String = " ", trimBlank: Bool = true) -> [[Int]] {
        do {
            let text = try String(contentsOfFile: inputFile, encoding: String.Encoding.utf8)
            var lines = text.components(separatedBy: CharacterSet.newlines)
            if trimBlank {
                lines = lines.filter { $0 != "" }
            }
            var array = [[Int]]()
            for line in lines {
                var intLine: [Int] = []
                for num in line {
                    intLine.append(Int(String(num))!)
                }
                array.append(intLine)
            }
            return array
        } catch {
            fatalError("Cannot load file \(inputFile)")
        }
    }
    
    public func toStringArray(trimBlank: Bool = true, delim: CharacterSet = CharacterSet.newlines) -> [String] {
        do {
            var text = try String(contentsOfFile: inputFile, encoding: String.Encoding.utf8)
            if trimBlank && text.hasSuffix("\n") {
                text = text.substring(toIndex: text.count - 1)
            }
            var lines = text.components(separatedBy: delim)
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
    
    public func toIntArray(trimBlank: Bool = true, delim: String = ",") -> [Int] {
        return toStringArray(delim: CharacterSet.init(charactersIn: delim)).map{ Int($0)! }
    }
}
