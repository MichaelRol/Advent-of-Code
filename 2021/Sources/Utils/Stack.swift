//
//  Stack.swift
//
//  All credit and big thank you to https://github.com/kalub92 for this code originally found here:
//  https://gist.github.com/kalub92/4f0b09e163f70ead9bad219be37a29e2
//
//  Some small adaptations made by Michael Rollins 10/12/2021
//

import Foundation

public struct Stack {
    
    public init() {
        
    }
    private var items: [String] = []
    
    public func peek() -> String {
        guard let topElement = items.first else { fatalError("This stack is empty.") }
        return topElement
    }
    
    public mutating func pop() -> String {
        return items.removeFirst()
    }
    
    public mutating func push(_ element: String) {
        items.insert(element, at: 0)
    }
    
    public func empty() -> Bool {
        return items.count == 0
    }
}
