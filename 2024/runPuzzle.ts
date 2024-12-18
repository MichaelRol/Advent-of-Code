import { getInput, getPuzzlePath, getTestInput } from "./utils/files";

const TOTAL_PUZZLES = 18;

export async function runPuzzle(test = false) {
    const args = process.argv.slice(2);
    if (args.length == 0) {
        var totalTime = 0;
        for (var day = 1; day <= TOTAL_PUZZLES; day++) {
            const puzzle = require(getPuzzlePath(day));
            const startTime = performance.now();
            const result1 = await puzzle.part1(test ? getTestInput(day, 1) : getInput(day));
            const result2 = await puzzle.part2(test ? getTestInput(day, 2) : getInput(day));
            const endTime = performance.now();
            totalTime += endTime - startTime;
            console.log(`Day: ${day}, Part 1: ${result1}`);
            console.log(`Day: ${day}, Part 2: ${result2}`);
        }
        console.log(`Total Time: ${formatMs(totalTime)}ms`);
    } else if (args.length == 1) {
        const day = parseInt(args[0]);
        const puzzle = require(getPuzzlePath(day));
        const input1 = test ? getTestInput(day, 1) : getInput(day);
        const input2 = test ? getTestInput(day, 2) : getInput(day);
        const startTime = performance.now();
        const result1 = await puzzle.part1(input1);
        const part1Time = performance.now();
        const result2 = await puzzle.part2(input2);
        const endTime = performance.now();
        console.log(`Day: ${day}, Part 1: ${result1} - Duration: ${formatMs(part1Time - startTime)}ms`);
        console.log(`Day: ${day}, Part 2: ${result2} - Duration: ${formatMs(endTime - part1Time)}ms`);
    } else if (args.length == 2) {
        const day = parseInt(args[0]);
        const part = args[1];
        const puzzle = require(getPuzzlePath(day));
        var input;
        if (part === "1") {
            input = test ? getTestInput(day, 1) : getInput(day);
        } else if (part === "2") {
            input = test ? getTestInput(day, 2) : getInput(day);
        } else {
            return Promise.reject("Invalid part number, should be either 1 or 2, but was: " + args[1]);
        }
        const startTime = performance.now();
        const result = await (part === "1" ? puzzle.part1(input) : puzzle.part2(input));
        const endTime = performance.now();
        console.log(`Day: ${day}, Part ${part}: ${result} - Duration: ${formatMs(endTime - startTime)}ms`);
    } else {
        throw "Invalid number of args, should be 0 (run all days), 1 (run both parts of a single day) or 2 (run a single part of a single day)";
    }
}

function formatMs(num: number) {
    return Math.round((num + Number.EPSILON) * 10000) / 10000;
}
