export function part1(rawInput: string) {
    const input = rawInput.split(" ").map(num => parseInt(num));
    return blink(input, 25);
}

export function part2(rawInput: string) {
    const input = rawInput.split(" ").map(num => parseInt(num));
    return blink(input, 75);
}

function blink(stones: number[], repetitions: number): number {
    for (let i = 0; i < repetitions; i++) {
        const newStones: number[] = [];
        for (const stone of stones) {
            if (stone === 0) newStones.push(1);
            else if (stone.toString().length % 2 === 0) {
                const stringStone = stone.toString();
                const firstStone = parseInt(stringStone.slice(0, stringStone.length / 2));
                const secondStone = parseInt(stringStone.slice(stringStone.length / 2));
                newStones.push(firstStone);
                newStones.push(secondStone)
            } else {
                newStones.push(stone * 2024);
            }
        }
        stones = newStones;
    }
    return stones.length;
}