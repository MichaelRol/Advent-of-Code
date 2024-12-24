import { readLines } from "../utils/input";

export function part1(rawInput: string) {
    const lines = readLines(rawInput);

    const towels: string[] = lines[0].split(", ");
    const patterns: string[] = lines.slice(2).filter(line => line);

    let possible = 0;
    for (const pattern of patterns) {
        if (findArrangements(pattern, towels, new Map())) possible++;
    }
    return possible;
}

export function part2(rawInput: string) {
    const lines = readLines(rawInput);

    const towels: string[] = lines[0].split(", ");
    const patterns: string[] = lines.slice(2).filter(line => line);
    let possible = BigInt(0);
    for (const pattern of patterns) {
        possible += findArrangements(pattern, towels, new Map());
    }
    return possible;
}

function findArrangements(section: string, towels: string[], subPatterns: Map<string, bigint>): bigint {
    if (section.length === 0) {
        return BigInt(1);
    }

    if (subPatterns.has(section)) {
        return subPatterns.get(section)!;
    }

    let sum = BigInt(0);
    for (const towel of towels) {
        if (section.startsWith(towel)) {
            const remaining = section.slice(towel.length);
            const arrangements = findArrangements(remaining, towels, subPatterns);
            sum += arrangements;
        }
    }

    subPatterns.set(section, sum);
    return sum;
}
