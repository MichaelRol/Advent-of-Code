import XCTest
@testable import Puzzles

final class AOC2021Tests: XCTestCase {
    func test1() {
        let puzzle = Day1(inputFile: "input1")
        XCTAssertEqual(puzzle.part1(), "1676")
        XCTAssertEqual(puzzle.part2(), "1706")
    }
    
    func test2() {
        let puzzle = Day2(inputFile: "input2")
        XCTAssertEqual(puzzle.part1(), "1561344")
        XCTAssertEqual(puzzle.part2(), "1848454425")
    }
    
    func test3() {
        let puzzle = Day3(inputFile: "input3")
        XCTAssertEqual(puzzle.part1(), "738234")
        XCTAssertEqual(puzzle.part2(), "3969126")
    }
    
    func test4() {
        let puzzle = Day4(inputFile: "input4")
        XCTAssertEqual(puzzle.part1(), "69579")
        XCTAssertEqual(puzzle.part2(), "14877")
    }
    
    func test5() {
        let puzzle = Day5(inputFile: "input5")
        XCTAssertEqual(puzzle.part1(), "6267")
        XCTAssertEqual(puzzle.part2(), "20196")
    }
    
    func test6() {
        let puzzle = Day6(inputFile: "input6")
        XCTAssertEqual(puzzle.part1(), "375482")
        XCTAssertEqual(puzzle.part2(), "1689540415957")
    }
}
