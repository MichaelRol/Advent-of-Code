import _ from "lodash";
import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const map: string[][] = readChars(rawInput);
    const visited: string[] = [];
    let total = 0;
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (visited.includes(JSON.stringify([i, j]))) continue;
            const region = map[i][j];
            let toVisit: [number, number][] = [[i, j]];
            let area = 0;
            let perim = 0;
            while (toVisit.length > 0) {
                const pos = toVisit.pop()!;
                visited.push(JSON.stringify(pos));
                area++;
                if (map[pos[0] + 1] && map[pos[0] + 1][pos[1]] === region) {
                    toVisit.push([pos[0] + 1, pos[1]]);
                } else {
                    perim++;
                }
                if (map[pos[0] - 1] && map[pos[0] - 1][pos[1]] === region) {
                    toVisit.push([pos[0] - 1, pos[1]]);
                } else {
                    perim++;
                }
                if (map[pos[0]][pos[1] + 1] === region) {
                    toVisit.push([pos[0], pos[1] + 1]);
                } else {
                    perim++;
                }
                if (map[pos[0]][pos[1] - 1] === region) {
                    toVisit.push([pos[0], pos[1] - 1]);
                } else {
                    perim++;
                }
                toVisit = toVisit.filter(neighbor => !visited.includes(JSON.stringify(neighbor)));
            }
            total += area * perim;
        }
    }
    return total;
}
export function part2(rawInput: string) {
    const map: string[][] = readChars(rawInput);
    const visited: string[] = [];
    let total = 0;
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (visited.includes(JSON.stringify([i, j]))) continue;
            const region = map[i][j];
            let toVisit: [number, number][] = [[i, j]];
            let area = 0;
            let nodes: [number, number][] = [];
            while (toVisit.length > 0) {
                const pos = toVisit.pop()!;
                visited.push(JSON.stringify(pos));
                nodes.push(pos);
                area++;
                if (map[pos[0] + 1] && map[pos[0] + 1][pos[1]] === region) {
                    toVisit.push([pos[0] + 1, pos[1]]);
                    nodes.push([pos[0] + 1, pos[1]]);
                }
                if (map[pos[0] - 1] && map[pos[0] - 1][pos[1]] === region) {
                    toVisit.push([pos[0] - 1, pos[1]]);
                    nodes.push([pos[0] - 1, pos[1]]);
                }
                if (map[pos[0]][pos[1] + 1] === region) {
                    toVisit.push([pos[0], pos[1] + 1]);
                    nodes.push([pos[0], pos[1] + 1]);
                }
                if (map[pos[0]][pos[1] - 1] === region) {
                    toVisit.push([pos[0], pos[1] - 1]);
                    nodes.push([pos[0], pos[1] - 1]);
                }
                toVisit = toVisit.filter(neighbor => !visited.includes(JSON.stringify(neighbor)));
            }
            const uniqueNodes: [number, number][] = [];
            const uniqueNodeStrings = new Set(nodes.map(node => JSON.stringify(node)));
            uniqueNodeStrings.forEach(node => uniqueNodes.push(JSON.parse(node)));
            const edges: number = calcNumOfEdges(uniqueNodes);
            total += area * edges;
        }
    }
    return total;
}

function calcNumOfEdges(nodes: [number, number][]): number {
    let edges = 0;
    const minX = nodes.map(node => node[0]).sort((a, b) => a - b)[0];
    const maxX = nodes.map(node => node[0]).sort((a, b) => a - b)[nodes.length - 1];
    const minY = nodes.map(node => node[1]).sort((a, b) => a - b)[0];
    const maxY = nodes.map(node => node[1]).sort((a, b) => a - b)[nodes.length - 1];
    // scan left to right
    let scan = true;
    let ys: number[] = [];
    for (let x = minX; x <= maxX; x++) {
        const newYs: number[] = [];
        for (let y = minY; y <= maxY; y++) {
            if (nodes.find(node => _.isEqual(node, [x, y]))) {
                if (scan) {
                    if (!ys.includes(y)) {
                        edges++;
                    }
                    newYs.push(y);
                    scan = false;
                }
            } else {
                if (!scan) scan = true;
            }
        }
        ys = newYs;
        scan = true;
    }
    // scan right to left
    ys = [];
    for (let x = minX; x <= maxX; x++) {
        const newYs: number[] = [];
        for (let y = maxY; y >= minY; y--) {
            if (nodes.find(node => _.isEqual(node, [x, y]))) {
                if (scan) {
                    if (!ys.includes(y)) {
                        edges++;
                    }
                    newYs.push(y);
                    scan = false;
                }
            } else {
                if (!scan) scan = true;
            }
        }
        ys = newYs;
        scan = true;
    }
    // scan top to bottom
    let xs: number[] = [];
    for (let y = minY; y <= maxY; y++) {
        const newXs: number[] = [];
        for (let x = minX; x <= maxX; x++) {
            if (nodes.find(node => _.isEqual(node, [x, y]))) {
                if (scan) {
                    if (!xs.includes(x)) {
                        edges++;
                    }
                    newXs.push(x);
                    scan = false;
                }
            } else {
                if (!scan) scan = true;
            }
        }
        xs = newXs;
        scan = true;
    }
    // scan bottom to top
    xs = [];
    for (let y = minY; y <= maxY; y++) {
        const newXs: number[] = [];
        for (let x = maxX; x >= minX; x--) {
            if (nodes.find(node => _.isEqual(node, [x, y]))) {
                if (scan) {
                    if (!xs.includes(x)) {
                        edges++;
                    }
                    newXs.push(x);
                    scan = false;
                }
            } else {
                if (!scan) scan = true;
            }
        }
        xs = newXs;
        scan = true;
    }
    return edges;
}
