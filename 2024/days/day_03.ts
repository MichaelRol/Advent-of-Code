export function part1(rawInput: string) {
    const regex = /mul\((?<first>\d{1,3}),(?<second>\d{1,3})\)/g;
    var match;
    var total = 0;
    do {
        match = regex.exec(rawInput);
        if (match) {
            total += Number(match.groups?.first) * Number(match.groups?.second);
        }
    } while (match);
    return total;
}

export function part2(rawInput: string) {
    const fullRegex = /mul\((?<first>\d{1,3}),(?<second>\d{1,3})\)/g;
    var match;
    var muls = [];
    do {
        match = fullRegex.exec(rawInput);
        if (match) {
            muls.push(Number(match.groups?.first) * Number(match.groups?.second));
        }
    } while (match);
    const regex = /mul\(\d{1,3},\d{1,3}\)/;
    const splits = rawInput.split(regex);
    var _do = true;
    var total = 0;
    for (const [i, prefix] of splits.entries()) {
        if (prefix.lastIndexOf("do()") > prefix.lastIndexOf("don't()")) {
            _do = true;
            total += muls[i] || 0;
        } else if (prefix.lastIndexOf("do()") === prefix.lastIndexOf("don't()")) {
            if (_do) total += muls[i] || 0;
        } else {
            _do = false;
        }
    }
    return total;
}
