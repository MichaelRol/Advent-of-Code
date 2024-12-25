import { readLines, splitSections } from "../utils/input";

export function part1(rawInput: string) {
    const locksAndKeys: string[][][] = splitSections(rawInput, "\n\n").map(x =>
        readLines(x).map(line => line.split(""))
    );

    const locks: number[][] = [];
    const keys: number[][] = [];
    for (const lockOrKey of locksAndKeys) {
        const rotated = lockOrKey[0].map((val, index) => lockOrKey.map(row => row[index]).reverse());
        const columns: number[] = [];
        rotated.forEach(col => columns.push(col.filter(cell => cell === "#").length - 1));
        if (lockOrKey[0][0] === "#") {
            // Key
            keys.push(columns);
        } else {
            // Lock
            locks.push(columns);
        }
    }

    let count = 0;
    for (const lock of locks) {
        for (const key of keys) {
            let allMatch = true;
            for (let i = 0; i < 5; i++) {
                if (lock[i] + key[i] > 5) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) count++;
        }
    }
    return count;
}
