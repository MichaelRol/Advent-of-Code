import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const map: number[][] = readChars(rawInput, "", x => parseInt(x));
    let count = 0;
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (map[i][j] === 0) {
                count += new Set(findPeaks([i, j], map).map(peak => JSON.stringify(peak))).size;
            }
        }
    }
    return count;
}

export function part2(rawInput: string) {
    const map: number[][] = readChars(rawInput, "", x => parseInt(x));
    let count = 0;
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (map[i][j] === 0) {
                count += findPeaks([i, j], map).length;
            }
        }
    }
    return count;
}

function findPeaks(pos: [number, number], map: number[][]): [number, number][] {
    const curr = map[pos[0]][pos[1]];
    if (curr === 9) {
        return [pos];
    }
    let count: [number, number][] = [];
    if (pos[0] < map.length - 1 && map[pos[0] + 1][pos[1]] === curr + 1) {
        findPeaks([pos[0] + 1, pos[1]], map).forEach(peak => count.push(peak));
    }
    if (pos[0] > 0 && map[pos[0] - 1][pos[1]] === curr + 1) {
        findPeaks([pos[0] - 1, pos[1]], map).forEach(peak => count.push(peak));
    }
    if (pos[1] < map[0].length - 1 && map[pos[0]][pos[1] + 1] === curr + 1) {
        findPeaks([pos[0], pos[1] + 1], map).forEach(peak => count.push(peak));
    }
    if (pos[1] > 0 && map[pos[0]][pos[1] - 1] === curr + 1) {
        findPeaks([pos[0], pos[1] - 1], map).forEach(peak => count.push(peak));
    }
    return count;
}
