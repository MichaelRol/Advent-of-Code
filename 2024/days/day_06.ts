import _ from "lodash";
import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const map: string[][] = readChars(rawInput);
    const start: [number, number] = findStart(map);
    const guard: Guard = new Guard(Direction.NORTH, start);
    map[start[0]][start[1]] = "X";
    let count = 1;
    while (
        typeof map[guard.next()[0]] !== "undefined" &&
        typeof map[guard.next()[0]][guard.next()[1]] !== "undefined"
    ) {
        const nextCoords: [number, number] = guard.next();
        const next: string = map[nextCoords[0]][nextCoords[1]];
        if (next === "#") {
            guard.turn();
        } else if (next === ".") {
            map[nextCoords[0]][nextCoords[1]] = "X";
            count++;
            guard.move();
        } else if (next === "X") {
            guard.move();
        }
    }
    return count;
}

export async function part2(rawInput: string) {
    const map: string[][] = readChars(rawInput);
    const start: [number, number] = findStart(map);

    const path: [number, number][] = findPath(_.cloneDeep(map), start);
    map[start[0]][start[1]] = ".";
    let promises = [];
    for (const potential of path) {
        promises.push(testMap(map, start, potential));
    }
    return Promise.all(promises).then(nums => nums.reduce((a, b) => a + b));
}

async function testMap(map: string[][], start: [number, number], barrier: [number, number]): Promise<number> {
    const guard: Guard = new Guard(Direction.NORTH, start);
    const visited: Map<number, Direction[]> = new Map();
    visited.set(start[0] * 1000 + start[1], [Direction.NORTH]);
    map[barrier[0]][barrier[1]] = "#";
    while (
        typeof map[guard.next()[0]] !== "undefined" &&
        typeof map[guard.next()[0]][guard.next()[1]] !== "undefined"
    ) {
        const nextCoords: [number, number] = guard.next();
        const next: string = map[nextCoords[0]][nextCoords[1]];
        const nextKey = nextCoords[0] * 1000 + nextCoords[1];
        if (next === "#") {
            guard.turn();
        } else if (visited.get(nextKey)?.includes(guard.getDirection())) {
            map[barrier[0]][barrier[1]] = ".";
            return 1;
        } else if (next === ".") {
            visited.set(nextKey, (visited.get(nextKey) || []).concat(guard.getDirection()));
            guard.move();
        }
    }
    map[barrier[0]][barrier[1]] = ".";
    return 0;
}

function findStart(map: string[][]): [number, number] {
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map.length; j++) {
            if (map[i][j] === "^") {
                return [i, j];
            }
        }
    }
    throw "Could not find start position";
}

function findPath(map: string[][], start: [number, number]): [number, number][] {
    const guard: Guard = new Guard(Direction.NORTH, start);
    map[start[0]][start[1]] = "X";
    let path = [];
    while (
        typeof map[guard.next()[0]] !== "undefined" &&
        typeof map[guard.next()[0]][guard.next()[1]] !== "undefined"
    ) {
        const nextCoords: [number, number] = guard.next();
        const next: string = map[nextCoords[0]][nextCoords[1]];
        if (next === "#") {
            guard.turn();
        } else if (next === ".") {
            map[nextCoords[0]][nextCoords[1]] = "X";
            path.push(nextCoords);
            guard.move();
        } else if (next === "X") {
            guard.move();
        }
    }
    return path;
}

enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

const directions = [Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST];

class Guard {
    private direction: Direction;
    private position: [number, number];

    public constructor(direction: Direction, position: [number, number]) {
        this.direction = direction;
        this.position = position;
    }

    getDirection() {
        return this.direction;
    }

    turn() {
        this.direction = directions[(this.direction + 1) % 4];
    }

    next(): [number, number] {
        switch (this.direction) {
            case Direction.NORTH:
                return [this.position[0] - 1, this.position[1]];
            case Direction.EAST:
                return [this.position[0], this.position[1] + 1];
            case Direction.SOUTH:
                return [this.position[0] + 1, this.position[1]];
            case Direction.WEST:
                return [this.position[0], this.position[1] - 1];
        }
    }

    move() {
        this.position = this.next();
    }
}
