import * as fs from "fs";
import { existsSync } from "fs";
import * as path from "path";

export function getPuzzlePath(day: number) {
    const dayWithLeadingZeros = String(day).padStart(2, "0");
    const puzzlePath = path.join(getAppRoot(), "days", "day_" + dayWithLeadingZeros);
    if (fs.existsSync(puzzlePath + ".ts")) {
        return puzzlePath;
    }
    throw `No puzzle exists for day: ${day}`;
}

export function getInput(day: number) {
    const dayWithLeadingZeros = String(day).padStart(2, "0");
    return fs.readFileSync(path.join(getAppRoot(), "input", "input_" + dayWithLeadingZeros + ".txt")).toString("utf-8");
}

export function getTestInput(day: number, part: number) {
    const dayWithLeadingZeros = String(day).padStart(2, "0");
    const standardFileName = dayWithLeadingZeros + ".txt";
    const pathRoot = path.join(getAppRoot(), "test_input", "test_");
    if (fs.existsSync(pathRoot + standardFileName)) {
        return fs.readFileSync(pathRoot + standardFileName).toString("utf-8");
    }
    const partFileName = dayWithLeadingZeros + "_" + part + ".txt";
    return fs.readFileSync(pathRoot + partFileName).toString("utf-8");
}

function getAppRoot() {
    let currentDir = __dirname;
    while (!existsSync(path.join(currentDir, "package.json"))) {
        currentDir = path.dirname(currentDir);
    }
    return currentDir;
}
