import _ from "lodash";

export function part1(rawInput: string) {
    const rawOutputs = rawInput
        .split("\n\n")[0]
        .split("\n")
        .map(row => row.split(": "));
    const outputs: Map<string, number> = new Map();
    for (const output of rawOutputs) {
        outputs.set(output[0], parseInt(output[1]));
    }
    const gates: Gate[] = rawInput
        .split("\n\n")[1]
        .split("\n")
        .map(row => row.split(" "))
        .map(values => {
            return {
                in_a: values[0],
                in_b: values[2],
                gate: verifyGate(values[1]),
                out: values[4]
            };
        });

    run(gates, outputs);

    const zs = Array.from(outputs.keys())
        .filter(key => key.startsWith("z"))
        .sort();
    return parseInt(
        zs
            .map(z => String(outputs.get(z)!))
            .reverse()
            .join(""),
        2
    );
}

export function part2(rawInput: string) {
    const gates: Gate[] = rawInput
        .split("\n\n")[1]
        .split("\n")
        .map(row => row.split(" "))
        .map(values => {
            return {
                in_a: values[0],
                in_b: values[2],
                gate: verifyGate(values[1]),
                out: values[4]
            };
        });

    for (let i = 1; i < 2 ** 44; i++) {
        const outputs: Map<string, number> = new Map();
        const bin = i.toString(2).padStart(45, "0").split("").reverse();
        let count = 0;
        for (const char of bin) {
            outputs.set("x" + count.toString().padStart(2, "0"), parseInt(char));
            outputs.set("y" + count.toString().padStart(2, "0"), parseInt(char));
            count++;
        }
        run([...gates], outputs);

        const zs = Array.from(outputs.keys())
            .filter(key => key.startsWith("z"))
            .sort();
        const output = parseInt(
            zs
                .map(z => String(outputs.get(z)!))
                .reverse()
                .join(""),
            2
        );
        const xs = Array.from(outputs.keys())
            .filter(key => key.startsWith("x"))
            .sort();
        const x = xs
            .map(x => String(outputs.get(x)!))
            .reverse()
            .join("");
        const ys = Array.from(outputs.keys())
            .filter(key => key.startsWith("y"))
            .sort();
        const y = ys
            .map(y => String(outputs.get(y)!))
            .reverse()
            .join("");

        const correct = parseInt(x, 2) + parseInt(y, 2);

        if (!_.isEqual(output, correct)) {
            console.log(i.toString(2));
            console.log(output.toString(2));
            console.log(correct.toString(2));
            break;
        }
    }

    console.log("");
}

function run(gates: Gate[], outputs: Map<string, number>) {
    while (gates.length !== 0) {
        const gate = gates.shift()!;
        if (outputs.has(gate.in_a) && outputs.has(gate.in_b)) {
            switch (gate.gate) {
                case "AND":
                    outputs.set(gate.out, outputs.get(gate.in_a)! & outputs.get(gate.in_b)!);
                    break;
                case "XOR":
                    outputs.set(gate.out, outputs.get(gate.in_a)! ^ outputs.get(gate.in_b)!);
                    break;
                case "OR":
                    outputs.set(gate.out, outputs.get(gate.in_a)! | outputs.get(gate.in_b)!);
                    break;
            }
        } else {
            gates.push(gate);
        }
    }
}

type Gate = {
    in_a: string;
    in_b: string;
    gate: "AND" | "XOR" | "OR";
    out: string;
};

function verifyGate(value: string): "AND" | "XOR" | "OR" {
    if (value === "AND" || value === "XOR" || value === "OR") {
        return value;
    }
    throw "Unknown gate value: " + value;
}
