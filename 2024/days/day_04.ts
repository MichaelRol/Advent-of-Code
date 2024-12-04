import { readChars } from "../utils/input";

export function part1(rawInput: string) {
    const grid: string[][] = readChars(rawInput);
    let count = 0;
    for (let i = 0; i < grid.length; i++) {
        for (let j = 0; j < grid[0].length; j++) {
            if (grid[i][j] === "X") {
                const directions = getValidDirections(i, j, grid.length, grid[0].length);
                for (const direction of directions) {
                    if (direction(i, j, grid) === "MAS") count++;
                }
            }
        }
    }
    return count;
}

export function part2(rawInput: string) {
    const grid: string[][] = readChars(rawInput);
    let count = 0;
    for (let i = 1; i < grid.length - 1; i++) {
        for (let j = 1; j < grid[0].length - 1; j++) {
            if (grid[i][j] === "A") {
                if (checkForXmas(i, j, grid)) count++;
            }
        }
    }
    return count;
}

const Directions = {
    N: (i: number, j: number, grid: string[][]) => grid[i - 1][j] + grid[i - 2][j] + grid[i - 3][j],
    S: (i: number, j: number, grid: string[][]) => grid[i + 1][j] + grid[i + 2][j] + grid[i + 3][j],
    W: (i: number, j: number, grid: string[][]) => grid[i][j - 1] + grid[i][j - 2] + grid[i][j - 3],
    E: (i: number, j: number, grid: string[][]) => grid[i][j + 1] + grid[i][j + 2] + grid[i][j + 3],
    NE: (i: number, j: number, grid: string[][]) => grid[i - 1][j + 1] + grid[i - 2][j + 2] + grid[i - 3][j + 3],
    NW: (i: number, j: number, grid: string[][]) => grid[i - 1][j - 1] + grid[i - 2][j - 2] + grid[i - 3][j - 3],
    SE: (i: number, j: number, grid: string[][]) => grid[i + 1][j + 1] + grid[i + 2][j + 2] + grid[i + 3][j + 3],
    SW: (i: number, j: number, grid: string[][]) => grid[i + 1][j - 1] + grid[i + 2][j - 2] + grid[i + 3][j - 3]
};

function getValidDirections(
    i: number,
    j: number,
    height: number,
    width: number
): ((i: number, j: number, grid: string[][]) => string)[] {
    const directions = [];
    if (i >= 3) {
        directions.push(Directions.N);
        if (j >= 3) directions.push(Directions.NW);
        if (j <= height - 4) directions.push(Directions.NE);
    }
    if (i <= width - 4) {
        directions.push(Directions.S);
        if (j >= 3) directions.push(Directions.SW);
        if (j <= height - 4) directions.push(Directions.SE);
    }
    if (j >= 3) {
        directions.push(Directions.W);
    }
    if (j <= height - 4) {
        directions.push(Directions.E);
    }
    return directions;
}

function checkForXmas(i: number, j: number, grid: string[][]) {
    const NW = grid[i - 1][j - 1];
    const SE = grid[i + 1][j + 1];

    const NE = grid[i - 1][j + 1];
    const SW = grid[i + 1][j - 1];

    return (
        ((NW === "M" && SE === "S") || (NW === "S" && SE === "M")) &&
        ((NE === "M" && SW === "S") || (NE === "S" && SW === "M"))
    );
}
