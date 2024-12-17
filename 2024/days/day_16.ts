import _ from "lodash";
import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const map: string[][] = readChars(rawInput);

    let vertices: Map<string, Vertex> | undefined;
    let end: [number, number] | undefined;
    ({ vertices, end } = populateVertices(map));
    const target = dijkstras(vertices, end!);
    return target.dist;
}

export function part2(rawInput: string) {
    const map: string[][] = readChars(rawInput);

    let vertices: Map<string, Vertex> | undefined;
    let end: [number, number] | undefined;
    ({ vertices, end } = populateVertices(map));
    const target = dijkstras(vertices, end!);
    const seats: Set<string> = new Set();
    findSeats(undefined, target, seats);
    return seats.size;
}

function findSeats(prev: Vertex | undefined, curr: Vertex, seats: Set<string>) {
    seats.add(JSON.stringify(curr.pos));
    if (curr.prev?.dist === 0) {
        seats.add(JSON.stringify(curr.prev.pos));
        return;
    }
    if (!prev) {
        findSeats(curr, curr.prev!, seats);
        return;
    }
    const neighbors = (curr.neighbors as Vertex[]).filter(neighbor => !_.isEqual(neighbor, prev));
    if (neighbors.length === 1) {
        findSeats(curr, neighbors[0], seats);
    } else {
        for (const neighbor of neighbors) {
            const weight =
                neighbor.dist +
                getWeight(neighbor.pos, curr.pos, prev?.pos) +
                getWeight(curr.pos, neighbor.pos, neighbor.prev?.pos);
            if (weight === prev!.dist) {
                findSeats(curr, neighbor, seats);
            }
        }
    }
}

function populateVertices(map: string[][]) {
    let end: [number, number] | undefined;
    const vertices: Map<string, Vertex> = new Map();
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (map[i][j] === "E") {
                end = [i, j];
            }
            if (map[i][j] !== "#") {
                const neighbors: string[] = [];
                if (map[i - 1][j] !== "#") neighbors.push(JSON.stringify([i - 1, j]));
                if (map[i + 1][j] !== "#") neighbors.push(JSON.stringify([i + 1, j]));
                if (map[i][j - 1] !== "#") neighbors.push(JSON.stringify([i, j - 1]));
                if (map[i][j + 1] !== "#") neighbors.push(JSON.stringify([i, j + 1]));
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
    for (const key of vertices.keys()) {
        const vertex: Vertex = vertices.get(key)!;
        const neighbors = vertex.neighbors!;
        const neighborVertices = [];
        for (const neighbor of neighbors) {
            neighborVertices.push(vertices.get(neighbor as string)!);
        }
        vertex.neighbors = neighborVertices;
    }
    return { vertices, end };
}

function dijkstras(vertices: Map<string, Vertex>, end: [number, number]): Vertex {
    const visited: Set<string> = new Set();
    while (vertices.size !== 0) {
        const uKey = findShortestDist(vertices);
        const u = vertices.get(uKey)!;
        if (uKey === JSON.stringify(end)) {
            return u;
        }
        vertices.delete(uKey);
        visited.add(uKey);
        const neighbors = u.neighbors.filter(
            neighbor => !visited.has(JSON.stringify((neighbor as Vertex).pos))
        ) as Vertex[];
        for (const v of neighbors) {
            const alt = u.dist + getWeight(v.pos, u.pos, u.prev?.pos);
            if (alt < v.dist) {
                v.dist = alt;
                v.prev = u;
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
    neighbors: Vertex[] | string[];
};
