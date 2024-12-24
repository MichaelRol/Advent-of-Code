import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    let vertices: Map<string, [number, number][]> = new Map();
    let distsForward: Map<string, number> = new Map();
    let prevs: Map<string, string> = new Map();
    let end: [number, number] | undefined;
    let start: [number, number] | undefined;
    ({ start, end } = populateVertices(readChars(rawInput), vertices, distsForward));
    bfs(vertices, start!, distsForward, prevs);

    let distsBackwards: Map<string, number> = new Map();
    vertices.forEach((v, k) => distsBackwards.set(k, Number.MAX_VALUE));
    bfs(vertices, end!, distsBackwards, new Map());

    const road: string[] = [];
    let backwards: string | undefined = JSON.stringify(end);
    while (backwards) {
        road.push(backwards);
        backwards = prevs.get(backwards);
    }
    const baseDist = distsForward.get(JSON.stringify(end))!;
    const possibleCheats: Map<string, string> = new Map();
    for (const point of road) {
        const pos = JSON.parse(point);
        if (!vertices.has(JSON.stringify([pos[0] - 1, pos[1]])) && vertices.has(JSON.stringify([pos[0] - 2, pos[1]])))
            possibleCheats.set(JSON.stringify([pos[0] - 1, pos[1]]), "N");
        if (!vertices.has(JSON.stringify([pos[0] + 1, pos[1]])) && vertices.has(JSON.stringify([pos[0] + 2, pos[1]])))
            possibleCheats.set(JSON.stringify([pos[0] + 1, pos[1]]), "S");
        if (!vertices.has(JSON.stringify([pos[0], pos[1] - 1])) && vertices.has(JSON.stringify([pos[0], pos[1] - 2])))
            possibleCheats.set(JSON.stringify([pos[0], pos[1] - 1]), "W");
        if (!vertices.has(JSON.stringify([pos[0], pos[1] + 1])) && vertices.has(JSON.stringify([pos[0], pos[1] + 2])))
            possibleCheats.set(JSON.stringify([pos[0], pos[1] + 1]), "E");
    }

    let count = 0;
    for (const cheat of possibleCheats) {
        const cheatPos: [number, number] = JSON.parse(cheat[0]);
        const startAndEnd: [[number, number], [number, number]] = getCheatStartAndEnd(cheatPos, cheat[1]);
        const distTo = distsForward.get(JSON.stringify(startAndEnd[0]))!;
        const distFrom = distsBackwards.get(JSON.stringify(startAndEnd[1]))!;
        if (baseDist - (distFrom + distTo + 2) >= (vertices.size < 100 ? 1 : 100)) count++;
    }
    return count;
}

export function part2(rawInput: string) {
    let vertices: Map<string, [number, number][]> = new Map();
    let distsForward: Map<string, number> = new Map();
    let prevs: Map<string, string> = new Map();
    let end: [number, number] | undefined;
    let start: [number, number] | undefined;
    const map: string[][] = readChars(rawInput);
    ({ start, end } = populateVertices(map, vertices, distsForward));
    map[end![0]][end![1]] = ".";
    bfs(vertices, start!, distsForward, prevs);
    let distsBackwards: Map<string, number> = new Map();
    vertices.forEach((v, k) => distsBackwards.set(k, Number.MAX_VALUE));
    bfs(vertices, end!, distsBackwards, new Map());

    const road: string[] = [];
    let backwards: string | undefined = JSON.stringify(end);
    while (backwards) {
        road.push(backwards);
        backwards = prevs.get(backwards);
    }
    const baseDist = distsForward.get(JSON.stringify(end))!;

    let count = 0;
    for (const pos of road) {
        const cheatEnds: { pos: [number, number]; dist: number }[] = findCheats(JSON.parse(pos), map);
        for (const cheat of cheatEnds) {
            const cheatPos = cheat.pos;
            const cheatDist = cheat.dist;
            const distTo = distsForward.get(pos)!;
            const distFrom = distsBackwards.get(JSON.stringify(cheatPos))!;
            const improvement = baseDist - (distFrom + distTo + cheatDist);
            if (improvement >= (vertices.size < 100 ? 50 : 100)) count++;
        }
    }
    return count;
}

function getCheatStartAndEnd(pos: [number, number], direction: string): [[number, number], [number, number]] {
    switch (direction) {
        case "N":
            return [
                [pos[0] + 1, pos[1]],
                [pos[0] - 1, pos[1]]
            ];
        case "S":
            return [
                [pos[0] - 1, pos[1]],
                [pos[0] + 1, pos[1]]
            ];
        case "W":
            return [
                [pos[0], pos[1] + 1],
                [pos[0], pos[1] - 1]
            ];
        case "E":
            return [
                [pos[0], pos[1] - 1],
                [pos[0], pos[1] + 1]
            ];
        default:
            throw "Unknown direction";
    }
}

function bfs(
    vertices: Map<string, [number, number][]>,
    start: [number, number],
    dists: Map<string, number>,
    prevs: Map<string, string>
) {
    const toVisit = [start];
    dists.set(JSON.stringify(start), 0);
    while (toVisit.length !== 0) {
        const u = toVisit.shift();
        const uKey = JSON.stringify(u);
        const neighbors = vertices.get(uKey)!;
        for (const v of neighbors) {
            if (dists.get(JSON.stringify(v)) === Number.MAX_VALUE) {
                const alt = dists.get(uKey)! + 1;
                dists.set(JSON.stringify(v), alt);
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

function findCheats(pos: [number, number], map: string[][]): { pos: [number, number]; dist: number }[] {
    const cheats: { pos: [number, number]; dist: number }[] = [];
    for (let x = -20; x <= 20; x++) {
        for (let y = -20 + Math.abs(x); y <= 20 + Math.abs(x); y++) {
            const dist = Math.abs(x) + Math.abs(y);
            if (map[pos[0] + x]?.[pos[1] + y] === "." && dist <= 20) {
                cheats.push({
                    pos: [pos[0] + x, pos[1] + y],
                    dist: dist
                });
            }
        }
    }
    return cheats;
}
