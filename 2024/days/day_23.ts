import { readLines } from "../utils/input";

export function part1(rawInput: string) {
    const lines = readLines(rawInput);
    const graph: Map<string, string[]> = createGraph(lines);
    const results = new Set();
    for (const node of graph.keys()) {
        graph
            .get(node)!
            .flatMap(neighbor =>
                graph
                    .get(neighbor)!
                    .filter(next => next !== node && graph.get(next)!.includes(node))
                    .map(next => [node, neighbor, next])
            )
            .filter(list => list.length === 3 && list.some(name => name.startsWith("t")))
            .map(cycle => JSON.stringify(cycle.sort()))
            .forEach(cycleString => results.add(cycleString));
    }
    return results.size;
}

export function part2(rawInput: string) {
    const lines = readLines(rawInput);
    const graph: Map<string, string[]> = createGraph(lines);
    let largest: string[] = [];
    const checked: Set<number> = new Set();
    for (const node of graph.keys()) {
        const result = findLargestGroup([node], graph, checked);
        if (result.length > largest.length) {
            largest = result;
        }
    }
    return Array.from(largest).sort();
}

function createGraph(lines: string[]) {
    const graph: Map<string, string[]> = new Map();
    for (const line of lines) {
        const [a, b] = line.split("-");
        graph.set(a, (graph.get(a) || []).concat(b));
        graph.set(b, (graph.get(b) || []).concat(a));
    }
    Array.from(graph.keys()).forEach(node => graph.set(node, Array.from(new Set(graph.get(node)))));
    return graph;
}

function findLargestGroup(nodes: string[], graph: Map<string, string[]>, checked: Set<number>): string[] {
    const allNeighbors: Set<string> = new Set();
    checked.add(hashIgnoreOrder(nodes));
    nodes.forEach(node =>
        graph.get(node)!.forEach(next => {
            if (!nodes.includes(next)) {
                allNeighbors.add(next);
            }
        })
    );

    const nexts = Array.from(allNeighbors).filter(
        node => !nodes.includes(node) && nodes.every(neighbor => graph.get(node)?.includes(neighbor))
    );

    if (nexts.length === 0) {
        return nodes;
    }

    return nexts
        .filter(next => !checked.has(hashIgnoreOrder(nodes.concat(next))))
        .map(next => findLargestGroup(nodes.concat(next), graph, checked))
        .reduce((a, b) => longest(a, b), []);
}

function hashIgnoreOrder(values: string[]) {
    let hash = 0;
    for (const value of values) {
        let subHash = 0;
        for (let i = 0; i < value.length; i++) {
            let chr = value.charCodeAt(i);
            subHash = (subHash << 5) - subHash + chr;
            subHash |= 0;
        }
        hash += subHash;
    }
    return hash;
}

function longest(a: string[], b: string[]): string[] {
    if (a.length > b.length) {
        return a;
    }
    return b;
}
