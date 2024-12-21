import _ from "lodash";
import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    let vertices: Map<string, [number, number][]> = new Map();
    let dists: Map<string, number> = new Map();
    let prevs: Map<string, string> = new Map();
    let end: [number, number] | undefined;
    let start: [number, number] | undefined;
    ({ start, end } = populateVertices(readChars(rawInput), vertices, dists));
    bfs(vertices, start!, dists, prevs);
    const road: string[] = [];
    let backwards: string | undefined = JSON.stringify(end);
    while (backwards) {
        road.push(backwards);
        backwards = prevs.get(backwards);
    }
    const baseDist = dists.get(JSON.stringify(end))!;
    const possibleCheats: Set<string> = new Set();
    for (const point of road) {
        const pos = JSON.parse(point);
        if (!vertices.has(JSON.stringify([pos[0] - 1, pos[1]])) && vertices.has(JSON.stringify([pos[0] - 2, pos[1]])))
            possibleCheats.add(JSON.stringify([pos[0] - 1, pos[1]]));
        if (!vertices.has(JSON.stringify([pos[0] + 1, pos[1]])) && vertices.has(JSON.stringify([pos[0] + 2, pos[1]])))
            possibleCheats.add(JSON.stringify([pos[0] + 1, pos[1]]));
        if (!vertices.has(JSON.stringify([pos[0], pos[1] - 1])) && vertices.has(JSON.stringify([pos[0], pos[1] - 2])))
            possibleCheats.add(JSON.stringify([pos[0], pos[1] - 1]));
        if (!vertices.has(JSON.stringify([pos[0], pos[1] + 1])) && vertices.has(JSON.stringify([pos[0], pos[1] + 2])))
            possibleCheats.add(JSON.stringify([pos[0], pos[1] + 1]));
    }

    let count = 0;
    for (const cheat of possibleCheats) {
        const cheatPos: [number, number] = JSON.parse(cheat);
        const neighbors: [number, number][] = [];
        if (road.includes(JSON.stringify([cheatPos[0] - 1, cheatPos[1]]))) neighbors.push([cheatPos[0] - 1, cheatPos[1]]);
        if (road.includes(JSON.stringify([cheatPos[0] + 1, cheatPos[1]]))) neighbors.push([cheatPos[0] + 1, cheatPos[1]]);
        if (road.includes(JSON.stringify([cheatPos[0], cheatPos[1] - 1]))) neighbors.push([cheatPos[0], cheatPos[1] - 1]);
        if (road.includes(JSON.stringify([cheatPos[0], cheatPos[1] + 1]))) neighbors.push([cheatPos[0], cheatPos[1] + 1]);

        const cheatMap: Map<string, [number, number][]> = _.cloneDeep(vertices);
        cheatMap.set(cheat, []);
        neighbors.forEach(neighbor => {
            cheatMap.get(JSON.stringify(neighbor))?.push(cheatPos);
            cheatMap.get(cheat)!.push(neighbor);
        })

        let min = Number.MAX_VALUE;
        for (const neighbor of neighbors) {
            const distTo = dists.get(JSON.stringify(neighbor))!;
            const cheatDists: Map<string, number> = new Map(Array.from(cheatMap.entries()).map(entry => [entry[0], Number.MAX_VALUE]));
            bfs(cheatMap, neighbor, cheatDists, new Map());
            const distFrom = cheatDists.get(JSON.stringify(end))!;
            if (distTo + distFrom < min) {
                min = distTo + distFrom
            }
        }
        if (baseDist - min >= (vertices.size < 100 ? 1 : 100)) count++;
    }
    return count;
}

function bfs(vertices: Map<string, [number, number][]>, start: [number, number], dists: Map<string, number>, prevs: Map<string, string>) {
    const toVisit = [start];
    dists.set(JSON.stringify(start), 0);
    while (toVisit.length !== 0) {
        const u = toVisit.shift();
        const uKey = JSON.stringify(u);
        const neighbors = vertices.get(uKey)!;
        for (const v of neighbors) {
            if (dists.get(JSON.stringify(v)) === Number.MAX_VALUE) {
                const alt = dists.get(uKey)! + 1;
                dists.set(JSON.stringify(v), alt)
                prevs.set(JSON.stringify(v), uKey);
                toVisit.push(v);
            }
        }
    }
}

function populateVertices(map: string[][], vertices: Map<string, [number, number][]>, dists: Map<string, number>) {
    let end: [number, number] | undefined;
    let start: [number, number] | undefined;
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (map[i][j] === "E") {
                end = [i, j];
            }
            if (map[i][j] !== "#") {
                const neighbors: [number, number][] = [];
                if (map[i - 1][j] !== "#") neighbors.push([i - 1, j]);
                if (map[i + 1][j] !== "#") neighbors.push([i + 1, j]);
                if (map[i][j - 1] !== "#") neighbors.push([i, j - 1]);
                if (map[i][j + 1] !== "#") neighbors.push([i, j + 1]);
                if (map[i][j] === "S") {
                    dists.set(JSON.stringify([i, j]), 0);
                    start = [i, j];
                } else {
                    dists.set(JSON.stringify([i, j]), Number.MAX_VALUE);
                }
                vertices.set(JSON.stringify([i, j]), neighbors);
            }
        }
    }
    return { start, end };
}
