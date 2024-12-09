import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const map: string[][] = readChars(rawInput);
    const nodes: Map<string, [number, number][]> = new Map();
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (map[i][j] !== ".") {
                nodes.set(map[i][j], (nodes.get(map[i][j]) || []).concat([[i, j]]));
            }
        }
    }
    const antinodes: [number, number][] = [];
    for (const key of nodes.keys()) {
        const keyNodes: [number, number][] = nodes.get(key)!;
        let combinations = keyNodes.flatMap(v => keyNodes.filter(w => v != w).map(w => [v, w]));

        for (const combination of combinations) {
            const xDiff = combination[0][0] - combination[1][0];
            const yDiff = combination[0][1] - combination[1][1];
            antinodes.push([combination[0][0] + xDiff, combination[0][1] + yDiff]);
            antinodes.push([combination[1][0] - xDiff, combination[1][1] - yDiff]);
        }
    }
    const antinodesInRange = antinodes.filter(antinode => inRange(antinode, map.length, map[0].length));
    const set = new Set(antinodesInRange.map(pair => JSON.stringify(pair)));
    return set.size;
}

export function part2(rawInput: string) {
    const map: string[][] = readChars(rawInput);
    const nodes: Map<string, [number, number][]> = new Map();
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (map[i][j] !== ".") {
                nodes.set(map[i][j], (nodes.get(map[i][j]) || []).concat([[i, j]]));
            }
        }
    }
    const antinodes: [number, number][] = [];
    for (const key of nodes.keys()) {
        const keyNodes: [number, number][] = nodes.get(key)!;
        let combinations = keyNodes.flatMap(v => keyNodes.filter(w => v != w).map(w => [v, w]));

        for (const combination of combinations) {
            const xDiff = combination[0][0] - combination[1][0];
            const yDiff = combination[0][1] - combination[1][1];
            let upNode: [number, number] = [combination[0][0] + xDiff, combination[0][1] + yDiff];
            let downNode: [number, number] = [combination[1][0] - xDiff, combination[1][1] - yDiff];
            while (inRange(upNode, map.length, map[0].length)) {
                antinodes.push(upNode);
                upNode = [upNode[0] + xDiff, upNode[1] + yDiff];
            }
            while (inRange(downNode, map.length, map[0].length)) {
                antinodes.push(downNode);
                downNode = [downNode[0] - xDiff, downNode[1] - yDiff];
            }
        }
    }
    const antinodesInRange = antinodes.concat(Array.from(nodes.values()).flat());
    const set = new Set(antinodesInRange.map(pair => JSON.stringify(pair)));
    return set.size;
}

function inRange(antinode: [number, number], length: number, width: number) {
    return antinode[0] >= 0 && antinode[0] < length && antinode[1] >= 0 && antinode[1] < width;
}
