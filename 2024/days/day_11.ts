export function part1(rawInput: string) {
    const input = rawInput.split(" ").map(num => parseInt(num));
    return blink(input, 25);
}

export function part2(rawInput: string) {
    const input = rawInput.split(" ").map(num => parseInt(num));
    return blink(input, 75);
}

function blink(stones: number[], repetitions: number): number {
    let stonesMap: Map<number, number> = new Map();
    for (const stone of stones) {
        stonesMap.set(stone, (stonesMap.get(stone) || 0) + 1);
    }
    for (let i = 0; i < repetitions; i++) {
        const newStonesMap: Map<number, number> = new Map();
        for (const stone of stonesMap.keys()) {
            if (stone === 0) newStonesMap.set(1, (newStonesMap.get(1) || 0) + stonesMap.get(stone)!);
            else if (stone.toString().length % 2 === 0) {
                const stringStone = stone.toString();
                const firstStone = parseInt(stringStone.slice(0, stringStone.length / 2));
                const secondStone = parseInt(stringStone.slice(stringStone.length / 2));
                newStonesMap.set(firstStone, (newStonesMap.get(firstStone) || 0) + stonesMap.get(stone)!);
                newStonesMap.set(secondStone, (newStonesMap.get(secondStone) || 0) + stonesMap.get(stone)!);
            } else {
                newStonesMap.set(stone * 2024, (newStonesMap.get(stone * 2024) || 0) + stonesMap.get(stone)!);
            }
        }
        stonesMap = newStonesMap;
    }
    return Array.from(stonesMap.values()).reduce((a, b) => a + b);
}