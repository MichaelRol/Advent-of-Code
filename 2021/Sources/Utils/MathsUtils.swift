//
//  File.swift
//  
//
//  Created by Michael Rollins on 03/12/2021.
//

import Foundation

precedencegroup PowerPrecedence { higherThan: MultiplicationPrecedence }
infix operator ^^ : PowerPrecedence
public func ^^ (radix: Int, power: Int) -> Int {
    return Int(pow(Double(radix), Double(power)))
}

public func binaryToInt(binaryString: String) -> Int {
  return Int(strtoul(binaryString, nil, 2))
}

public func mod(_ a: Int, _ n: Int) -> Int {
    precondition(n > 0, "modulus must be positive")
    let r = a % n
    return r >= 0 ? r : r + n
}
