export function readLines(input: string): string[] {
    return input.split("\n");
}

export function readChars<T>(input: string, delimiter = "", postProcessor: (x: string) => T = identity): T[][] {
    return input
        .trim()
        .split("\n")
        .map(line => line.split(delimiter).map(x => postProcessor(x)));
}

export function splitSections(input: string, delimiter = "\n\n"): string[] {
    return input.split(delimiter);
}

function identity(x: any) {
    return x;
}
