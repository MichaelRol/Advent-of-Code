import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const map: string[][] = readChars(rawInput);

    let end: [number, number];
    const vertices: Map<string, Vertex> = new Map();
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
                vertices.set(JSON.stringify([i, j]), {
                    dist: map[i][j] === "S" ? 0 : Number.MAX_VALUE,
                    pos: [i, j],
                    prev:
                        map[i][j] === "S"
                            ? {
                                  dist: 0,
                                  pos: [i, j - 1],
                                  prev: undefined,
                                  neighbors: []
                              }
                            : undefined,
                    neighbors: neighbors
                });
            }
        }
    }
    const target = dijkstras(vertices, end!);
    return target.dist;
}

function dijkstras(vertices: Map<string, Vertex>, end: [number, number]): Vertex {
    while (vertices.size !== 0) {
        const uKey = findShortestDist(vertices);
        const u = vertices.get(uKey)!;
        vertices.delete(uKey);
        if (uKey === JSON.stringify(end)) {
            return u;
        }
        const neighbors = u.neighbors
            .filter(neighbor => Array.from(vertices.keys()).includes(JSON.stringify(neighbor)))
            .map(pos => vertices.get(JSON.stringify(pos))!);
        for (const v of neighbors) {
            const alt = u.dist + getWeight(v.pos, u.pos, u.prev?.pos);
            if (alt < v.dist) {
                v.dist = alt;
                v.prev = u;
                vertices.set(JSON.stringify(v.pos), v);
            }
        }
    }
    throw "Could not find shortest path";
}

function getWeight(next: [number, number], curr: [number, number], last: [number, number] | undefined): number {
    if (!last) return 1;
    if ((next[0] === curr[0] && curr[0] === last[0]) || (next[1] === curr[1] && curr[1] === last[1])) {
        return 1;
    }
    return 1001;
}

function findShortestDist(vertices: Map<string, Vertex>): string {
    let minDist = Number.MAX_VALUE;
    let min = undefined;
    for (const vertex of vertices.entries()) {
        if (vertex[1].dist <= minDist) {
            minDist = vertex[1].dist;
            min = vertex[0];
        }
    }
    if (!min) {
        throw "Could not find min distance";
    }
    return min;
}

type Vertex = {
    dist: number;
    pos: [number, number];
    prev: Vertex | undefined;
    neighbors: [number, number][];
};
