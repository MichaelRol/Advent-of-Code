import { readChars } from "../utils/input";

export function part1(input: string) {
    const levels: number[][] = readChars(input, " ", parseInt);
    return levels.filter((row: number[]) => checkDiffs(calcDiffs(row)) !== 0).length;
}

export function part2(input: string) {
    const levels: number[][] = readChars(input, " ", parseInt);
    return levels.filter((row: number[]) => verifyRowWithTolerance(row)).length;
}

function calcDiffs(row: number[]) {
    const diffs = [];
    for (var i = 0; i < row.length - 1; i++) {
        diffs.push(row[i + 1] - row[i]);
    }
    return diffs;
}

function checkDiffs(diffs: number[]) {
    if (diffs.every(diff => diff <= 3 && diff >= 1)) return 1;
    if (diffs.every(diff => diff >= -3 && diff <= -1)) return -1;
    return 0;
}

function verifyRowWithTolerance(row: number[]): boolean {
    const diffs: number[] = calcDiffs(row);
    const sign = checkDiffs(diffs)
    if (sign !== 0) return true;
    const checks = diffs.map(diff => verifyDiff(diff, sign));
    var badDiffs = [];
    for (var i = 0; i < diffs.length; i++) {
        if (!checks[i]) badDiffs.push(i);
    }
    if (badDiffs.length > 2) return false;
    if (badDiffs.length === 2) {
        if (badDiffs[1] - badDiffs[0] !== 1) return false;
        return verifyDiff(row[badDiffs[1] + 1] - row[badDiffs[0]], sign);
    }
    if (badDiffs[0] === diffs.length - 1 || badDiffs[0] === 0) return true;
    return (
        verifyDiff(row[badDiffs[0] + 1] - row[badDiffs[0] - 1], sign) ||
        verifyDiff(row[badDiffs[0] + 2] - row[badDiffs[0]], sign)
    );
}

function verifyDiff(diff: number, sign: number) {
    return Math.abs(diff) >= 1 && Math.abs(diff) <= 3 && Math.sign(diff) === sign;
}
