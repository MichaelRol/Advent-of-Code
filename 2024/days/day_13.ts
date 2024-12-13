import { round } from "lodash";

export function part1(rawInput: string) {
    const machineStrings: string[] = rawInput.split("\n\n");
    return machineStrings
        .map(parse)
        .map(machine => solve(machine[0], machine[1], machine[2], machine[3], machine[4], machine[5]))
        .filter(
            (result: [number, number]) =>
                Math.abs(result[0] - round(result[0])) < 0.01 && Math.abs(result[1] - round(result[1])) < 0.01
        )
        .map((result: [number, number]) => 3 * round(result[0]) + round(result[1]))
        .reduce((a, b) => a + b);
}

export function part2(rawInput: string) {
    const machineStrings: string[] = rawInput.split("\n\n");
    return machineStrings
        .map(parse)
        .map(machine =>
            solve(
                machine[0] + 10000000000000,
                machine[1] + 10000000000000,
                machine[2],
                machine[3],
                machine[4],
                machine[5]
            )
        )
        .filter(
            (result: [number, number]) =>
                Math.abs(result[0] - round(result[0])) < 0.01 && Math.abs(result[1] - round(result[1])) < 0.01
        )
        .map((result: [number, number]) => 3 * round(result[0]) + round(result[1]))
        .reduce((a, b) => a + b);
}

function solve(t_X: number, t_Y: number, w: number, x: number, y: number, z: number): [number, number] {
    if (w > y) {
        const f = (t_X * y) / w;
        const g = (x * y) / w;
        const h = (t_Y - f) / (z - g);
        return [(f - g * h) / y, h];
    } else {
        const f = (t_Y * w) / y;
        const g = (z * w) / y;
        const h = (t_X - f) / (x - g);
        return [(f - g * h) / w, h];
    }
}

function parse(row: string) {
    const regex =
        /.*X(\+|=)(?<AX>\d*), Y(\+|=)(?<AY>\d*)\n.*X(\+|=)(?<BX>\d*), Y(\+|=)(?<BY>\d*)\n.*X(\+|=)(?<X>\d*), Y(\+|=)(?<Y>\d*)/;
    const groups = regex.exec(row)?.groups!;
    return [+groups["X"], +groups["Y"], +groups["AX"], +groups["BX"], +groups["AY"], +groups["BY"]];
}
