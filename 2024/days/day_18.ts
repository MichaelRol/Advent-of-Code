import _ from "lodash";
import { readLines } from "../utils/input";

export function part1(rawInput: string) {
    const bytes: number[][] = readLines(rawInput).map(line =>
        line
            .split(",")
            .map(num => parseInt(num))
            .reverse()
    );

    const size = bytes.length < 1024 ? 7 : 71;
    const numOfBytes = bytes.length < 1024 ? 12 : 1024;
    const vertices = populateVertices(bytes.slice(0, numOfBytes), size);
    return dijkstras(vertices, [size - 1, size - 1]);
}

export function part2(rawInput: string) {
    const bytes: number[][] = readLines(rawInput).map(line =>
        line
            .split(",")
            .map(num => parseInt(num))
            .reverse()
    );

    const size = bytes.length < 1024 ? 7 : 71;
    let low = 1;
    let high = bytes.length;
    while (high - low > 1) {
        const mid = Math.trunc((high + low) / 2);
        if (floodFill(bytes.slice(0, mid), size)) {
            low = mid;
        } else {
            high = mid;
        }
    }
    return `${bytes[low][1]},${bytes[low][0]}`;
}

function floodFill(antiMap: number[][], size: number) {
    const toCheck: [number, number][] = [[0, 0]];
    const antiMapStrings = antiMap.map(pos => JSON.stringify(pos));
    while (toCheck.length !== 0) {
        const pos: [number, number] = toCheck.pop()!;
        antiMapStrings.push(JSON.stringify(pos));
        if (_.isEqual(pos, [size - 1, size - 1])) return true;
        if (!antiMapStrings.includes(JSON.stringify([pos[0] - 1, pos[1]])) && pos[0] - 1 >= 0) {
            toCheck.push([pos[0] - 1, pos[1]]);
        }
        if (!antiMapStrings.includes(JSON.stringify([pos[0] + 1, pos[1]])) && pos[0] + 1 < size) {
            toCheck.push([pos[0] + 1, pos[1]]);
        }
        if (!antiMapStrings.includes(JSON.stringify([pos[0], pos[1] - 1])) && pos[1] - 1 >= 0) {
            toCheck.push([pos[0], pos[1] - 1]);
        }
        if (!antiMapStrings.includes(JSON.stringify([pos[0], pos[1] + 1])) && pos[1] + 1 < size) {
            toCheck.push([pos[0], pos[1] + 1]);
        }
    }
    return false;
}

function populateVertices(antiMap: number[][], size: number) {
    const antiMapStrings = antiMap.map(pos => JSON.stringify(pos));
    const vertices: Map<string, Vertex> = new Map();
    for (let i = 0; i < size; i++) {
        for (let j = 0; j < size; j++) {
            if (!antiMapStrings.includes(JSON.stringify([i, j]))) {
                const neighbors: string[] = [];
                if (!antiMapStrings.includes(JSON.stringify([i - 1, j]))) neighbors.push(JSON.stringify([i - 1, j]));
                if (!antiMapStrings.includes(JSON.stringify([i + 1, j]))) neighbors.push(JSON.stringify([i + 1, j]));
                if (!antiMapStrings.includes(JSON.stringify([i, j - 1]))) neighbors.push(JSON.stringify([i, j - 1]));
                if (!antiMapStrings.includes(JSON.stringify([i, j + 1]))) neighbors.push(JSON.stringify([i, j + 1]));
                vertices.set(JSON.stringify([i, j]), {
                    dist: i === 0 && j === 0 ? 0 : Number.MAX_VALUE,
                    pos: [i, j],
                    prev: undefined,
                    neighbors: neighbors
                });
            }
        }
    }
    for (const key of vertices.keys()) {
        const vertex: Vertex = vertices.get(key)!;
        const neighbors = vertex.neighbors!;
        const neighborVertices = [];
        for (const neighbor of neighbors) {
            const neighborVertex = vertices.get(neighbor as string)!;
            if (!neighborVertex) continue;
            neighborVertices.push(neighborVertex);
        }
        vertex.neighbors = neighborVertices;
    }
    return vertices;
}

function dijkstras(vertices: Map<string, Vertex>, end: [number, number]): number {
    const visited: Set<string> = new Set();
    while (vertices.size !== 0) {
        const uKey = findShortestDist(vertices);
        const u = vertices.get(uKey)!;
        if (uKey === JSON.stringify(end)) {
            if (u.dist === Number.MAX_VALUE) {
                throw "Could not find shortest path";
            }
            return u.dist;
        }
        vertices.delete(uKey);
        visited.add(uKey);
        const neighbors = u.neighbors.filter(
            neighbor => !visited.has(JSON.stringify((neighbor as Vertex).pos))
        ) as Vertex[];
        for (const v of neighbors) {
            const alt = u.dist + 1;
            if (alt < v.dist) {
                v.dist = alt;
                v.prev = u;
            }
        }
    }
    throw "Could not find shortest path";
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
    neighbors: Vertex[] | string[];
};
