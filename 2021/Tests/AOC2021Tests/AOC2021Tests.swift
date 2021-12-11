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
    
    func test7() {
        let puzzle = Day7(inputFile: "input7")
        XCTAssertEqual(puzzle.part1(), "335330")
        XCTAssertEqual(puzzle.part2(), "92439766")
    }
    
    func test8() {
        let puzzle = Day8(inputFile: "input8")
        XCTAssertEqual(puzzle.part1(), "412")
        XCTAssertEqual(puzzle.part2(), "978171")
    }
    
    func test9() {
        let puzzle = Day9(inputFile: "input9")
        XCTAssertEqual(puzzle.part1(), "528")
        XCTAssertEqual(puzzle.part2(), "920448")
    }
    
    func test10() {
        let puzzle = Day10(inputFile: "input10")
        XCTAssertEqual(puzzle.part1(), "311949")
        XCTAssertEqual(puzzle.part2(), "3042730309")
    }
    
    func test11() {
        let puzzle1 = Day11(inputFile: "input11")
        XCTAssertEqual(puzzle1.part1(), "1669")
        let puzzle2 = Day11(inputFile: "input11")
        XCTAssertEqual(puzzle2.part2(), "351")
    }
}
