import { readChars } from "../utils/input";

export function part1(input: string) {
    const levels: number[][] = readChars(input, " ", parseInt);
    return levels.filter((row: number[]) => checkDiffs(calcDiffs(row))).length;
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
    return diffs.every(diff => diff <= 3 && diff >= 1) || diffs.every(diff => diff >= -3 && diff <= -1);
}

function verifyRowWithTolerance(row: number[]): boolean {
    const diffs: number[] = calcDiffs(row);
    if (checkDiffs(diffs)) return true;
    const sign = Math.sign(diffs.map(num => Math.sign(num!)).reduce((a, b) => a + b));
    var badIndex = -1;
    for (var i = 1; i < row.length - 1; i++) {
        if (badIndex === i) {
            continue;
        }
        const beforeIndex = badIndex === i - 1 ? i - 2 : i - 1;
        const beforeCheck = verifyDiff(row[i] - row[beforeIndex], sign);
        const afterCheck = verifyDiff(row[i + 1] - row[i], sign);
        if ((!beforeCheck || !afterCheck) && badIndex !== -1) {
            return false;
        }
        if (!beforeCheck && !afterCheck) {
            const skipMiddle = verifyDiff(row[i + 1] - row[i - 1], sign);
            if (skipMiddle) {
                badIndex = i;
            } else {
                return false;
            }
        } else if (!beforeCheck) {
            if (i === 1) {
                const checkSelected = verifyDiff(row[i + 1] - row[i - 1], sign);
                if (checkSelected) {
                    badIndex = 1;
                } else {
                    badIndex = 0;
                }
                continue;
            }
            const checkSelected = verifyDiff(row[i + 1] - row[i - 1], sign);
            const checkPrevious = verifyDiff(row[i] - row[i - 2], sign);
            if (!checkPrevious && !checkSelected) {
                return false;
            }
            if (!checkPrevious && checkSelected) {
                badIndex = i;
            } else {
                badIndex = i - 1;
            }
        } else if (!afterCheck) {
            if (i + 1 === row.length - 1) {
                continue;
            }
            const checkSelected = verifyDiff(row[i + 1] - row[i - 1], sign);
            const checkNext = verifyDiff(row[i + 2] - row[i], sign);
            if (!checkNext && !checkSelected) {
                return false;
            }
            if (checkNext && !checkSelected) {
                badIndex = i + 1;
            } else {
                badIndex = i;
            }
        }
    }
    return true;
}

function verifyDiff(diff: number, sign: number) {
    return Math.abs(diff) >= 1 && Math.abs(diff) <= 3 && Math.sign(diff) === sign;
}
