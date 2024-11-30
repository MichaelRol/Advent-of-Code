export function readLines(input: string): string[] {
    return input.split("\n");
}

export function readChars(input: string, delimiter = ""): string[][] {
    return input.split("\n").map(line => line.split(delimiter));
}
