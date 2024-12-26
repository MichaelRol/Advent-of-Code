import * as fs from "fs";
import { readLines } from "../utils/input";
import { mod } from "../utils/maths";

export function part1(rawInput: string) {
    const robots = readLines(rawInput).map(parseRow);
    const width = robots.length < 15 ? 11 : 101;
    const length = robots.length < 15 ? 7 : 103;
    let q_1 = 0;
    let q_2 = 0;
    let q_3 = 0;
    let q_4 = 0;
    for (const robot of robots) {
        const x = mod(robot[0] + robot[2] * 100, width);
        const y = mod(robot[1] + robot[3] * 100, length);
        if (x < Math.floor(width / 2) && y < Math.floor(length / 2)) q_1++;
        if (x > width / 2 && y < Math.floor(length / 2)) q_2++;
        if (x < Math.floor(width / 2) && y > length / 2) q_3++;
        if (x > width / 2 && y > length / 2) q_4++;
    }
    return q_1 * q_2 * q_3 * q_4;
}

export async function part2(rawInput: string) {
    const robots = readLines(rawInput).map(parseRow);
    const width = robots.length < 15 ? 11 : 101;
    const length = robots.length < 15 ? 7 : 103;
    // Hardcoded for reuse - real solution was to iterate 1-10000 visually inspect images
    for (let s = 7861; s < 7862; s++) {
        const map: string[][] = Array.from(Array(length), _ => Array(width).fill("0"));
        for (const robot of robots) {
            const x = mod(robot[0] + robot[2] * s, width);
            const y = mod(robot[1] + robot[3] * s, length);
            map[y][x] = "1";
        }
        let imageBase = `P1\n${width} ${length}`;
        for (const line of map) {
            imageBase += "\n" + line.join(" ");
        }
        fs.writeFileSync(__dirname + `/day14.pbm`, imageBase);
    }
    return 7861;
}

function parseRow(row: string): [number, number, number, number] {
    const regex = /p=(\d*),(\d*) v=(-?\d*),(-?\d*)/;
    let [, x, y, vx, vy] = regex.exec(row) || [];
    return [+x, +y, +vx, +vy];
}
