import { readLines } from "../utils/input";

export function part1(rawInput: string) {
    const numbers: bigint[] = readLines(rawInput).map(num => BigInt(parseInt(num)));

    let total = BigInt(0);
    for (let number of numbers) {
        for (let i = 0; i < 2000; i++) {
            const mult64 = number * BigInt(64);
            number ^= mult64;
            number %= BigInt(16777216);
            const div32 = number / BigInt(32);
            number ^= div32;
            number %= BigInt(16777216);
            const mult2048 = number * BigInt(2048);
            number ^= mult2048;
            number %= BigInt(16777216);
        }
        total += number;
    }
    return total;
}