import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const lists: string[][] = readChars(rawInput, "   ");
    const list1: number[] = lists.map(char => parseInt(char[0])).sort();
    const list2: number[] = lists.map(char => parseInt(char[1])).sort();

    var total = 0;
    for (var x = 0; x < list1.length; x++) {
        total += Math.abs(list1[x] - list2[x]);
    }
    return total;
}

export function part2(rawInput: string) {
    const lists: string[][] = readChars(rawInput, "   ");
    const list1: number[] = lists.map(char => parseInt(char[0]));
    const list2: number[] = lists.map(char => parseInt(char[1]));
    const list2Freq: Map<number, number> = new Map();
    list2.forEach(num => list2Freq.set(num, (list2Freq.get(num) || 0) + 1));
    return list1.map(num => (list2Freq.get(num) || 0) * num).reduce((x, y) => x + y);
}
