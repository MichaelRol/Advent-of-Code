import { readLines } from "../utils/input";
import { mod } from "../utils/maths";

export function part1(rawInput: string) {
    const numbers: number[] = readLines(rawInput).map(num => parseInt(num));

    let total = 0;
    for (let number of numbers) {
        for (let i = 0; i < 2000; i++) {
            number = mutate(number);
        }
        total += number;
    }
    return total;
}

export function part2(rawInput: string) {
    const numbers: number[] = readLines(rawInput).map(num => parseInt(num));
    const allWindows: Map<number, Map<string, number>> = new Map();
    for (let number of numbers) {
        let window = [];
        const windows = new Map();
        let prev = number % 10;
        const start = number;
        for (let i = 0; i < 2000; i++) {
            number = mutate(number);
            const next = number % 10;
            const diff = next - prev;
            if (diff === 0) {
                window = [];
                continue;
            }
            if (window.length === 4) {
                window.shift();
                window.push(diff);
                if (next !== 0 && !windows.has(JSON.stringify(window))) {
                    windows.set(JSON.stringify(window), next);
                }
            } else {
                window.push(diff);
                if (window.length === 4) {
                    if (next !== 0 && !windows.has(JSON.stringify(window))) {
                        windows.set(JSON.stringify(window), next);
                    }
                }
            }
            prev = next;
        }
        allWindows.set(start, windows);
    }
    let max = 0;
    const windows: Set<string> = new Set(Array.from(allWindows.values()).flatMap(map => Array.from(map.keys())));
    for (const window of windows) {
        let total = 0;
        for (const [_, value] of allWindows) {
            total += value.get(window) || 0;
        }
        if (total > max) {
            max = total;
        }
    }
    return max;
}

function mutate(secret: number) {
    secret = mod((secret << 6) ^ secret, 16777216);
    secret = mod((secret >> 5) ^ secret, 16777216);
    secret = mod((secret << 11) ^ secret, 16777216);
    return secret;
}
