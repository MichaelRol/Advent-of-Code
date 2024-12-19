import _ from "lodash";
import { readLines } from "../utils/input";

export function part1(rawInput: string) {
    const lines = readLines(rawInput);
    const program: number[] = lines[4]
        .split(": ")[1]
        .split(",")
        .map(num => parseInt(num));
    const regA = BigInt(parseInt(lines[0].split(": ")[1]));
    const regB = BigInt(parseInt(lines[1].split(": ")[1]));
    const regC = BigInt(parseInt(lines[2].split(": ")[1]));

    return runProgram(program, regA, regB, regC).join(",");
}

export function part2(rawInput: string) {
    const lines = readLines(rawInput);
    const program: number[] = lines[4]
        .split(": ")[1]
        .split(",")
        .map(num => parseInt(num));
    const regB = BigInt(parseInt(lines[1].split(": ")[1]));
    const regC = BigInt(parseInt(lines[2].split(": ")[1]));
    let result = BigInt(0);
    const lastI = Array(program.length).fill(0);
    for (let index = program.length - 1; index >= 0; index--) {
        const target = program.slice(index);
        let lastOutput: number[] = [];
        for (let i = lastI[index]; i < 8; i++) {
            const output = runProgram(program, result, regB, regC);
            lastOutput = output;
            if (_.isEqual(output, target)) {
                if (index === 0) {
                    return result;
                }
                result = result << BigInt(3);
                lastI[index] = i;
                break;
            }
            result++;
        }
        if (!_.isEqual(lastOutput, target)) {
            index++;
        }
        if (index === 0 && !_.isEqual(lastOutput, program)) {
            index++;
        }
    }
    throw "Could not find solution";
}

function runProgram(program: number[], regA: bigint, regB: bigint, regC: bigint): number[] {
    const output: number[] = [];
    let pointer = 0;
    while (pointer < program.length) {
        const opcode = program[pointer];
        const operand = program[pointer + 1];
        switch (opcode) {
            case 0:
                regA = regA / BigInt(2) ** getCombo(operand, regA, regB, regC);
                break;
            case 1:
                regB = regB ^ BigInt(operand);
                break;
            case 2:
                regB = getCombo(operand, regA, regB, regC) % BigInt(8);
                break;
            case 3:
                if (regA !== BigInt(0)) {
                    pointer = operand;
                    continue;
                }
                break;
            case 4:
                regB = regB ^ regC;
                break;
            case 5:
                output.push(Number(getCombo(operand, regA, regB, regC) % BigInt(8)));
                break;
            case 6:
                regB = regA / BigInt(2) ** getCombo(operand, regA, regB, regC);
                break;
            case 7:
                regC = regA / BigInt(2) ** getCombo(operand, regA, regB, regC);
                break;
            default:
                throw "Invalid opcode: " + opcode;
        }
        pointer += 2;
    }
    return output;
}

function getCombo(operand: number, regA: bigint, regB: bigint, regC: bigint): bigint {
    switch (operand) {
        case 0:
        case 1:
        case 2:
        case 3:
            return BigInt(operand);
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
