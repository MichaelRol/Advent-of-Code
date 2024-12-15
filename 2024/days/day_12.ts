import _ from "lodash";
import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const map: string[][] = readChars(rawInput);
    const visited: Map<string, string[]> = new Map();
    let total = 0;
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            const region = map[i][j];
            if (visited.get(region)?.includes(JSON.stringify([i, j]))) continue;
            let toVisit: [number, number][] = [[i, j]];
            let regionPositions: string[] = [JSON.stringify([i, j])];
            let area = 0;
            let perim = 0;
            while (toVisit.length > 0) {
                const pos = toVisit.pop()!;
                regionPositions.push(JSON.stringify(pos));
                area++;
                perim += checkSquare(pos[0] + 1, pos[1], region, toVisit, map);
                perim += checkSquare(pos[0] - 1, pos[1], region, toVisit, map);
                perim += checkSquare(pos[0], pos[1] + 1, region, toVisit, map);
                perim += checkSquare(pos[0], pos[1] - 1, region, toVisit, map);
                toVisit = toVisit.filter(neighbor => !regionPositions.includes(JSON.stringify(neighbor)));
            }
            visited.set(region, (visited.get(region) || []).concat(...regionPositions));
            total += area * perim;
        }
    }
    return total;
}

export function part2(rawInput: string) {
    const map: string[][] = readChars(rawInput);
    const visited: Map<string, string[]> = new Map();
    let total = 0;
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            const region = map[i][j];
            if (visited.get(region)?.includes(JSON.stringify([i, j]))) continue;
            let toVisit: [number, number][] = [[i, j]];
            let area = 0;
            let nodes: string[] = [];
            while (toVisit.length > 0) {
                const pos = toVisit.pop()!;
                nodes.push(JSON.stringify(pos));
                area++;
                checkSquare(pos[0] + 1, pos[1], region, toVisit, map);
                checkSquare(pos[0] - 1, pos[1], region, toVisit, map);
                checkSquare(pos[0], pos[1] + 1, region, toVisit, map);
                checkSquare(pos[0], pos[1] - 1, region, toVisit, map);
                toVisit = toVisit.filter(neighbor => !nodes.includes(JSON.stringify(neighbor)));
            }
            visited.set(region, (visited.get(region) || []).concat(...nodes));
            const edges: number = calcNumOfEdges(nodes.map(node => JSON.parse(node)));
            total += area * edges;
        }
    }
    return total;
}

function checkSquare(i: number, j: number, region: string, toVisit: [number, number][], map: string[][]): number {
    if (map[i] && map[i][j] === region) {
        toVisit.push([i, j]);
        return 0;
    } else {
        return 1;
    }
}

function calcNumOfEdges(nodes: [number, number][]): number {
    let edges = 0;
    // TODO: Can we limit the range by creating a map of x => [yMin, yMax]
    const minX = nodes.map(node => node[0]).sort((a, b) => a - b)[0];
    const maxX = nodes.map(node => node[0]).sort((a, b) => a - b)[nodes.length - 1];
    const minY = nodes.map(node => node[1]).sort((a, b) => a - b)[0];
    const maxY = nodes.map(node => node[1]).sort((a, b) => a - b)[nodes.length - 1];
    // scan left to right
    let ys: number[] = [];
    for (let x = minX; x <= maxX; x++) {
        const newYs: number[] = [];
        let scan = false;
        for (let y = minY; y <= maxY + 1; y++) {
            ({ scan, edges } = checkForEdge(nodes, x, y, scan, ys, edges, newYs, true));
        }
        ys = newYs;
    }
    // // scan right to left
    // ys = [];
    // for (let x = minX; x <= maxX; x++) {
    //     const newYs: number[] = [];
    //     let scan = true;
    //     for (let y = maxY; y >= minY; y--) {
    //         ({ scan, edges } = checkForEdge(nodes, x, y, scan, ys, edges, newYs, true));
    //     }
    //     ys = newYs;
    // }
    // scan top to bottom
    let xs: number[] = [];
    for (let y = minY; y <= maxY; y++) {
        const newXs: number[] = [];
        let scan = false;
        for (let x = minX; x <= maxX + 1; x++) {
            ({ scan, edges } = checkForEdge(nodes, x, y, scan, xs, edges, newXs, false));
        }
        xs = newXs;
    }
    // // scan bottom to top
    // xs = [];
    // for (let y = minY; y <= maxY; y++) {
    //     const newXs: number[] = [];
    //     let scan = true;
    //     for (let x = maxX; x >= minX; x--) {
    //         ({ scan, edges } = checkForEdge(nodes, x, y, scan, xs, edges, newXs, false));
    //     }
    //     xs = newXs;
    // }
    return edges;
}

function checkForEdge(
    nodes: [number, number][],
    x: number,
    y: number,
    inRegion: boolean,
    xs: number[],
    edges: number,
    newXs: number[],
    direction: boolean
) {
    if (nodes.find(node => _.isEqual(node, [x, y]))) {
        if (!inRegion) {
            if (!xs.includes(direction ? y : x)) {
                edges++;
            }
            newXs.push(direction ? y : x);
            // edges++;
            inRegion = true;
        }
    } else {
        if (inRegion) {
            if (!xs.includes(direction ? y : x)) {
                edges++;
            }
            newXs.push(direction ? y : x);
            inRegion = false;
        }
    }
    return { scan: inRegion, edges };
}
