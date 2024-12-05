import { readChars, splitSections } from "../utils/input";

export function part1(rawInput: string) {
    const sections: string[] = splitSections(rawInput);
    const rawRules: [number, number][] = readChars(sections[0], "|", parseInt).map(rule => [rule[1], rule[0]]);
    const updates: number[][] = readChars(sections[1], ",", parseInt);

    const rules: Map<number, number[]> = produceMappings(rawRules);

    let goodUpdates = [];
    for (const update of updates) {
        let valid = true;
        for (let i = 0; i < update.length - 1; i++) {
            if (rules.get(update[i])?.includes(update[i + 1])) {
                valid = false;
                break;
            }
        }
        if (valid) goodUpdates.push(update);
    }
    return goodUpdates.map(update => update[Math.floor(update.length / 2)]).reduce((a, b) => a + b);
}

export function part2(rawInput: string) {
    const sections: string[] = splitSections(rawInput);
    const rawRules: [number, number][] = readChars(sections[0], "|", parseInt).map(rule => [rule[1], rule[0]]);
    const updates: number[][] = readChars(sections[1], ",", parseInt);

    const rules: Map<number, number[]> = produceMappings(rawRules);

    let fixedUpdates = [];
    for (const update of updates) {
        let needsFixing = false;
        for (let i = 0; i < update.length - 1; i++) {
            if (rules.get(update[i])?.includes(update[i + 1])) {
                needsFixing = true;
                const temp = update[i];
                update[i] = update[i + 1];
                update[i + 1] = temp;
                i -= 2; // This is very dumb
            }
        }
        if (needsFixing) fixedUpdates.push(update);
    }
    return fixedUpdates.map(update => update[Math.floor(update.length / 2)]).reduce((a, b) => a + b);
}

function produceMappings(rawRules: [number, number][]): Map<number, number[]> {
    const map = new Map();
    for (const rule of rawRules) {
        if (map.has(rule[0])) {
            (map.get(rule[0]) ?? []).push(rule[1]);
        } else {
            map.set(rule[0], [rule[1]]);
        }
    }
    return map;
}
