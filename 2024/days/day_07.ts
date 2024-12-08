import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const eqs = readChars(rawInput, ": ", x => x).map((line: string[]) => {
        return {
            target: parseInt(line[0]),
            nums: line[1].split(" ").map(num => parseInt(num)).reverse()
        };
    });
    let count = 0;
    for (const eq of eqs) {
        if (calc(eq.target, eq.nums)) count += eq.target;
    }
    return count;
}

function calc(target: number, nums: number[]): boolean {
    if (nums.length === 1) {
        if (nums[0] === target) return true;
        else return false;
    }
    if (target % nums[0] === 0) {
        return calc(target / nums[0], nums.slice(1)) || calc(target - nums[0], nums.slice(1));
    } else {
        return calc(target - nums[0], nums.slice(1));
    }
}