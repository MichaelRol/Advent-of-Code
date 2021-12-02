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
    }
