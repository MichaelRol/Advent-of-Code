import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const [rawMap, rawDirections] = rawInput.split("\n\n");
    const map: string[][] = readChars(rawMap);
    const directions: string = rawDirections.replaceAll("\n", "");
    const groupedDirections: [string, number][] = groupDirections(directions);
    let robot: [number, number] = findRobot(map);

    for (const direction of groupedDirections) {
        const dir = direction[0];
        let steps = direction[1];
        if (dir === "<") {
            let pos: [number, number] = [...robot];
            const boxes: [number, number][] = [];
            while (steps > 0) {
                if (map[pos[0]][pos[1] - 1] === "#") {
                    break;
                } else if (map[pos[0]][pos[1] - 1] === "O") {
                    boxes.push([pos[0], pos[1] - 1]);
                } else if (map[pos[0]][pos[1] - 1] === ".") {
                    steps--;
                }
                pos[1]--;
            }
            for (const box of boxes) {
                map[box[0]][box[1]] = ".";
            }
            for (const _ of boxes) {
                map[pos[0]][pos[1]] = "O";
                pos[1]++;
            }
            robot = pos;
        } else if (dir === ">") {
            let pos: [number, number] = [...robot];
            const boxes: [number, number][] = [];
            while (steps > 0) {
                if (map[pos[0]][pos[1] + 1] === "#") {
                    break;
                } else if (map[pos[0]][pos[1] + 1] === "O") {
                    boxes.push([pos[0], pos[1] + 1]);
                } else if (map[pos[0]][pos[1] + 1] === ".") {
                    steps--;
                }
                pos[1]++;
            }
            for (const box of boxes) {
                map[box[0]][box[1]] = ".";
            }
            for (const _ of boxes) {
                map[pos[0]][pos[1]] = "O";
                pos[1]--;
            }
            robot = pos;
        } else if (dir === "^") {
            let pos: [number, number] = [...robot];
            const boxes: [number, number][] = [];
            while (steps > 0) {
                if (map[pos[0] - 1][pos[1]] === "#") {
                    break;
                } else if (map[pos[0] - 1][pos[1]] === "O") {
                    boxes.push([pos[0] - 1, pos[1]]);
                } else if (map[pos[0] - 1][pos[1]] === ".") {
                    steps--;
                }
                pos[0]--;
            }
            for (const box of boxes) {
                map[box[0]][box[1]] = ".";
            }
            for (const _ of boxes) {
                map[pos[0]][pos[1]] = "O";
                pos[0]++;
            }
            robot = pos;
        } else if (dir === "v") {
            let pos: [number, number] = [...robot];
            const boxes: [number, number][] = [];
            while (steps > 0) {
                if (map[pos[0] + 1][pos[1]] === "#") {
                    break;
                } else if (map[pos[0] + 1][pos[1]] === "O") {
                    boxes.push([pos[0] + 1, pos[1]]);
                } else if (map[pos[0] + 1][pos[1]] === ".") {
                    steps--;
                }
                pos[0]++;
            }
            for (const box of boxes) {
                map[box[0]][box[1]] = ".";
            }
            for (const _ of boxes) {
                map[pos[0]][pos[1]] = "O";
                pos[0]--;
            }
            robot = pos;
        }
    }
    let sum = 0;
    for (let i = 1; i < map.length - 1; i++) {
        for (let j = 1; j < map[0].length - 1; j++) {
            if (map[i][j] === "O") {
                sum += 100 * i + j;
            }
        }
    }
    return sum;
}

export function part2(rawInput: string) {
    const [rawMap, rawDirections] = rawInput.split("\n\n");
    const map: string[][] = doubleMapWidth(readChars(rawMap));
    const directions: string = rawDirections.replaceAll("\n", "");
    const groupedDirections: [string, number][] = groupDirections(directions);
    let robot: [number, number] = findRobot(map);
    for (const direction of groupedDirections) {
        const dir = direction[0];
        let steps = direction[1];
        if (dir === "<") {
            let pos: [number, number] = [...robot];
            const boxes: [number, number][] = [];
            while (steps > 0) {
                if (map[pos[0]][pos[1] - 1] === "#") {
                    break;
                } else if (map[pos[0]][pos[1] - 1] === "]") {
                    boxes.push([pos[0], pos[1] - 1]);
                    pos[1]--;
                } else if (map[pos[0]][pos[1] - 1] === ".") {
                    steps--;
                }
                pos[1]--;
            }
            for (const box of boxes) {
                map[box[0]][box[1] - 1] = ".";
                map[box[0]][box[1]] = ".";
            }
            for (const _ of boxes) {
                map[pos[0]][pos[1]] = "[";
                map[pos[0]][pos[1] + 1] = "]";
                pos[1] += 2;
            }
            robot = pos;
        } else if (dir === ">") {
            let pos: [number, number] = [...robot];
            const boxes: [number, number][] = [];
            while (steps > 0) {
                if (map[pos[0]][pos[1] + 1] === "#") {
                    break;
                } else if (map[pos[0]][pos[1] + 1] === "[") {
                    boxes.push([pos[0], pos[1] + 1]);
                    pos[1]++;
                } else if (map[pos[0]][pos[1] + 1] === ".") {
                    steps--;
                }
                pos[1]++;
            }
            for (const box of boxes) {
                map[box[0]][box[1]] = ".";
                map[box[0]][box[1] + 1] = ".";
            }
            for (const _ of boxes) {
                map[pos[0]][pos[1]] = "]";
                map[pos[0]][pos[1] - 1] = "[";
                pos[1] -= 2;
            }
            robot = pos;
        } else if (dir === "^") {
            let pos: [number, number] = [...robot];
            for (let _ = 0; _ < steps; _++) {
                if (map[pos[0] - 1][pos[1]] === "#") {
                    break;
                } else if (map[pos[0] - 1][pos[1]] === "[") {
                    const boxes: [[number, number], [number, number]][] | null = findBoxes(
                        [
                            [pos[0] - 1, pos[1]],
                            [pos[0] - 1, pos[1] + 1]
                        ],
                        -1,
                        map
                    );
                    if (!boxes) {
                        break;
                    }
                    for (const box of boxes) {
                        map[box[0][0]][box[0][1]] = ".";
                        map[box[1][0]][box[1][1]] = ".";
                    }
                    for (const box of boxes) {
                        map[box[0][0] - 1][box[0][1]] = "[";
                        map[box[1][0] - 1][box[1][1]] = "]";
                    }
                } else if (map[pos[0] - 1][pos[1]] === "]") {
                    const boxes: [[number, number], [number, number]][] | null = findBoxes(
                        [
                            [pos[0] - 1, pos[1] - 1],
                            [pos[0] - 1, pos[1]]
                        ],
                        -1,
                        map
                    );
                    if (!boxes) {
                        break;
                    }
                    for (const box of boxes) {
                        map[box[0][0]][box[0][1]] = ".";
                        map[box[1][0]][box[1][1]] = ".";
                    }
                    for (const box of boxes) {
                        map[box[0][0] - 1][box[0][1]] = "[";
                        map[box[1][0] - 1][box[1][1]] = "]";
                    }
                }
                pos[0]--;
            }
            robot = pos;
        } else if (dir === "v") {
            let pos: [number, number] = [...robot];
            for (let _ = 0; _ < steps; _++) {
                if (map[pos[0] + 1][pos[1]] === "#") {
                    break;
                } else if (map[pos[0] + 1][pos[1]] === "[") {
                    const boxes: [[number, number], [number, number]][] | null = findBoxes(
                        [
                            [pos[0] + 1, pos[1]],
                            [pos[0] + 1, pos[1] + 1]
                        ],
                        1,
                        map
                    );
                    if (!boxes) {
                        break;
                    }
                    for (const box of boxes) {
                        map[box[0][0]][box[0][1]] = ".";
                        map[box[1][0]][box[1][1]] = ".";
                    }
                    for (const box of boxes) {
                        map[box[0][0] + 1][box[0][1]] = "[";
                        map[box[1][0] + 1][box[1][1]] = "]";
                    }
                } else if (map[pos[0] + 1][pos[1]] === "]") {
                    const boxes: [[number, number], [number, number]][] | null = findBoxes(
                        [
                            [pos[0] + 1, pos[1] - 1],
                            [pos[0] + 1, pos[1]]
                        ],
                        1,
                        map
                    );
                    if (!boxes) {
                        break;
                    }
                    for (const box of boxes) {
                        map[box[0][0]][box[0][1]] = ".";
                        map[box[1][0]][box[1][1]] = ".";
                    }
                    for (const box of boxes) {
                        map[box[0][0] + 1][box[0][1]] = "[";
                        map[box[1][0] + 1][box[1][1]] = "]";
                    }
                }
                pos[0]++;
            }
            robot = pos;
        }
    }
    let sum = 0;
    for (let i = 1; i < map.length - 1; i++) {
        for (let j = 1; j < map[0].length - 1; j++) {
            if (map[i][j] === "[") {
                sum += 100 * i + j;
            }
        }
    }
    return sum;
}

function groupDirections(directions: string): [string, number][] {
    const groupedDirections: [string, number][] = [];
    let count: number = 0;
    let curr: string | null = null;
    for (const direction of directions) {
        if (curr && direction !== curr) {
            groupedDirections.push([curr, count]);
            count = 0;
        }
        curr = direction;
        count++;
    }
    groupedDirections.push([curr!, count]);
    return groupedDirections;
}

function findRobot(map: string[][]): [number, number] {
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (map[i][j] === "@") {
                map[i][j] = ".";
                return [i, j];
            }
        }
    }
    throw "Could not find robot in map";
}

function doubleMapWidth(map: string[][]): string[][] {
    const doubledMap = [];
    for (const line of map) {
        const newLine = [];
        for (const char of line) {
            if (char === "O") {
                newLine.push("[", "]");
            } else if (char === "@") {
                newLine.push("@", ".");
            } else {
                newLine.push(char, char);
            }
        }
        doubledMap.push(newLine);
    }
    return doubledMap;
}

function findBoxes(
    box: [[number, number], [number, number]],
    direction: number,
    map: string[][]
): [[number, number], [number, number]][] | null {
    const left = box[0];
    const right = box[1];
    const leftCell = map[left[0] + direction][left[1]];
    const rightCell = map[right[0] + direction][right[1]];

    if (rightCell === "#" || leftCell === "#") {
        return null;
    }
    const boxes: [[number, number], [number, number]][] = [box];
    if (leftCell === "[" && rightCell === "]") {
        const newBoxes = findBoxes(
            [
                [left[0] + direction, left[1]],
                [right[0] + direction, right[1]]
            ],
            direction,
            map
        );
        if (newBoxes === null) {
            return null;
        }
        boxes.push(...newBoxes);
    }
    if (leftCell == "]") {
        const newBoxes = findBoxes(
            [
                [left[0] + direction, left[1] - 1],
                [left[0] + direction, left[1]]
            ],
            direction,
            map
        );
        if (newBoxes === null) {
            return null;
        }
        boxes.push(...newBoxes);
    }
    if (rightCell == "[") {
        const newBoxes = findBoxes(
            [
                [right[0] + direction, right[1]],
                [right[0] + direction, right[1] + 1]
            ],
            direction,
            map
        );
        if (newBoxes === null) {
            return null;
        }
        boxes.push(...newBoxes);
    }
    return boxes;
}
