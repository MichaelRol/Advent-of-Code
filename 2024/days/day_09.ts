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
                memIndex++;
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
    const nums: number[] = rawInput.split("").map(num => parseInt(num));
    const length = nums.length;
    let lastInputIndex = (length - 1) % 2 === 0 ? length - 1 : length - 2;
    let lastCellCount = nums.at(lastInputIndex)!;
    let toMove: number[] = [];
    for (let x = lastInputIndex; x > 0; x -= 2) {
        toMove.push(x);
    }
    let count = 0;
    let memIndex = 0;
    for (let i = 0; i < nums.length; i++) {
        if (i > toMove[0]) {
            break;
        }
        if (i % 2 === 0) {
            if (!toMove.includes(i)) {
                memIndex += nums.at(i)!;
                continue;
            }
            const numOfCells = lastInputIndex === i ? lastCellCount : nums.at(i)!;
            const cellNum = i / 2;
            for (let j = 0; j < numOfCells; j++) {
                count += memIndex * cellNum;
                memIndex++;
            }
        } else {
            let gapSize = nums.at(i)!;
            const moved: number[] = [];
            for (let x = 0; x < toMove.length; x++) {
                const numOfCells = nums.at(toMove.at(x)!)!;
                if (numOfCells <= gapSize) {
                    const cellNum = toMove.at(x)! / 2;
                    for (let j = 0; j < numOfCells; j++) {
                        count += memIndex * cellNum;
                        memIndex++;
                        gapSize--;
                    }
                    moved.push(toMove.at(x)!);
                    if (gapSize === 0) break;
                }
            }
            toMove = toMove.filter(num => !moved.includes(num) && num > i);
            memIndex += gapSize;
        }
    }
    return count;
}
