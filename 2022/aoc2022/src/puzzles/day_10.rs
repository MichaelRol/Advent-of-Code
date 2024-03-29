use std::fs;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    
    let mut x = 1;
    let mut total = 0;
    let mut cycles = 1;
    for line in contents.lines() {
        if line == "noop"{
            cycles += 1;
            if (cycles - 20) % 40 == 0 {
                total += cycles * x;
            }
        } else {
            cycles += 1;
            if (cycles - 20) % 40 == 0 {
                total += cycles * x;
            }
            cycles += 1;
            let num: i32 = line.split(" ").last().unwrap().parse().unwrap();
            x += num;
            if (cycles - 20) % 40 == 0 {
                total += cycles * x;
            }
        }
    }
    return total;
}

pub fn part2(path: String) -> String {    
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    
    let mut x = 1;
    let mut cycle: i32 = 1;
    let crt: &mut Vec<char> = &mut Vec::new();
    crt.push('\n');
    for line in contents.lines() {
        if line == "noop"{
            add_pixel(crt, cycle, x);
            cycle += 1;
        } else {
            add_pixel(crt, cycle, x);
            cycle += 1;
            if (cycle - 1) % 40 == 0 {
                crt.push('\n');
                cycle = 1;
            }
            add_pixel(crt, cycle, x);
            cycle += 1;
            let num: i32 = line.split(" ").last().unwrap().parse().unwrap();
            x += num;
        }
        if (cycle - 1) % 40 == 0 {
            crt.push('\n');
            cycle = 1;
        }
    }
    return crt.iter().collect::<String>();
}

fn add_pixel(crt: &mut Vec<char>, cycle: i32, x: i32) {
    if ((cycle-1) - x).abs() <= 1 {
        crt.push('#');
    } else {
        crt.push('.');
    }
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input10.txt".to_owned()), 13680);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input10.txt".to_owned()), "\n###..####..##..###..#..#.###..####.###..
#..#....#.#..#.#..#.#.#..#..#.#....#..#.
#..#...#..#....#..#.##...#..#.###..###..
###...#...#.##.###..#.#..###..#....#..#.
#....#....#..#.#....#.#..#....#....#..#.
#....####..###.#....#..#.#....####.###..\n");
    }
}
