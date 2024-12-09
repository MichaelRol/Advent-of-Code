export function part1(rawInput: string) {
    const length = rawInput.length;
    let lastInputIndex = (length - 1) % 2 === 0 ? length - 1 : length - 2;
    let lastCellCount = parseInt(rawInput.at(lastInputIndex)!);
    let lastCellNum = lastInputIndex / 2;
    let count = 0;
    let memIndex = 0;
    for (let i = 0; i < rawInput.length; i++) {
        if (i > lastInputIndex) {
            break;
        }
        if (i % 2 === 0) {
            const numOfCells = lastInputIndex === i ? lastCellCount : parseInt(rawInput.at(i)!);
            const cellNum = i / 2;
            for (let j = 0; j < numOfCells; j++) {
                count += memIndex * cellNum;
                memIndex++;
            }
        } else {
            let gapSize = parseInt(rawInput.at(i)!);
            while (gapSize > 0) {
                count += lastCellNum * memIndex;
                memIndex++
                gapSize--;
                lastCellCount--;
                if (lastCellCount == 0) {
                    lastInputIndex -= 2;
                    lastCellCount = parseInt(rawInput.at(lastInputIndex)!);
                    lastCellNum = lastInputIndex / 2;
                }
            }
        }
    }
    return count;
}

export function part2(rawInput: string) {
    const length = rawInput.length;
    let lastInputIndex = (length - 1) % 2 === 0 ? length - 1 : length - 2;
    let lastCellCount = parseInt(rawInput.at(lastInputIndex)!);
    const alreadyMoved: number[] = [];
    let count = 0;
    let memIndex = 0;
    for (let i = 0; i < rawInput.length; i++) {
        if (alreadyMoved.includes(i)) {
            memIndex += parseInt(rawInput.at(i)!)
            continue;
        }
        if (i > lastInputIndex) {
            break;
        }
        if (i % 2 === 0) {
            const numOfCells = lastInputIndex === i ? lastCellCount : parseInt(rawInput.at(i)!);
            const cellNum = i / 2;
            for (let j = 0; j < numOfCells; j++) {
                count += memIndex * cellNum;
                memIndex++;
            }
        } else {
            let gapSize = parseInt(rawInput.at(i)!);
            let x = lastInputIndex;
            for (; x > i; x -= 2) {
                if (alreadyMoved.includes(x)) continue;
                const numOfCells = parseInt(rawInput.at(x)!);
                if (numOfCells <= gapSize) {
                    const cellNum = x / 2;
                    for (let j = 0; j < numOfCells; j++) {
                        count += memIndex * cellNum;
                        memIndex++;
                        gapSize--;
                    }
                    alreadyMoved.push(x);
                    if (gapSize === 0) break;
                }
            }
            memIndex += gapSize;
        }
    }
    return count;
}