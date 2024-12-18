import { readLines } from "../utils/input";

export function part1(rawInput: string) {
    const lines = readLines(rawInput);
    const program: number[] = lines[4]
        .split(": ")[1]
        .split(",")
        .map(num => parseInt(num));
    const regA = parseInt(lines[0].split(": ")[1]);
    const regB = parseInt(lines[1].split(": ")[1]);
    const regC = parseInt(lines[2].split(": ")[1]);

    return runProgram(program, regA, regB, regC).join(",");
}

function runProgram(program: number[], regA: number, regB: number, regC: number): number[] {
    const output: number[] = [];
    let pointer = 0;
    while (pointer < program.length) {
        const opcode = program[pointer];
        const operand = program[pointer + 1];
        switch (opcode) {
            case 0:
                regA = Math.trunc(regA / 2 ** getCombo(operand, regA, regB, regC));
                break;
            case 1:
                regB = regB ^ operand;
                break;
            case 2:
                regB = getCombo(operand, regA, regB, regC) % 8;
                break;
            case 3:
                if (regA !== 0) {
                    pointer = operand;
                    continue;
                }
                break;
            case 4:
                regB = regB ^ regC;
                break;
            case 5:
                output.push(getCombo(operand, regA, regB, regC) % 8);
                break;
            case 6:
                regB = Math.trunc(regA / 2 ** getCombo(operand, regA, regB, regC));
                break;
            case 7:
                regC = Math.trunc(regA / 2 ** getCombo(operand, regA, regB, regC));
                break;
            default:
                throw "Invalid opcode: " + opcode;
        }
        pointer += 2;
    }
    return output;
}

function getCombo(operand: number, regA: number, regB: number, regC: number): number {
    switch (operand) {
        case 0:
        case 1:
        case 2:
        case 3:
            return operand;
        case 4:
            return regA;
        case 5:
            return regB;
        case 6:
            return regC;
        default:
            throw "Invalid operand: " + operand;
    }
}
