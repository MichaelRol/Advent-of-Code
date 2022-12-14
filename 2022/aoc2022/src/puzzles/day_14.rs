use std::fs;

use itertools::Itertools;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let (mut rock, highest_y, lowest_x) = produce_rocks_1(contents);
    let mut count = 0;
    loop {
        let mut sand: (usize, usize) = (0, 500 - lowest_x,);
        loop {
            if rock[sand.0 + 1][sand.1] == '.' {
                sand =  (sand.0 + 1, sand.1);
            } else if rock[sand.0 + 1][sand.1 - 1] == '.' {
                sand =  (sand.0 + 1, sand.1 - 1);
            } else if rock[sand.0 + 1][sand.1 + 1] == '.' {
                sand =  (sand.0 + 1, sand.1 + 1);
            } else {
                rock[sand.0][sand.1] = 'X';
                break;
            }
            if sand.0 >= highest_y {
                return count;
            }
        }
        count += 1;
    }

}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
        let (mut rock, _, lowest_x) = produce_rocks_2(contents);
        let mut count = 0;
        loop {
            let mut sand: (usize, usize) = (0, 500 - lowest_x,);
            loop {
                if rock[sand.0 + 1][sand.1] == '.' {
                    sand =  (sand.0 + 1, sand.1);
                } else if rock[sand.0 + 1][sand.1 - 1] == '.' {
                    sand =  (sand.0 + 1, sand.1 - 1);
                } else if rock[sand.0 + 1][sand.1 + 1] == '.' {
                    sand =  (sand.0 + 1, sand.1 + 1);
                } else {
                    if sand == (0, 500 -lowest_x) {
                        return count + 1;
                    }
                    rock[sand.0][sand.1] = 'X';
                    break;
                }
            }
            count += 1;
        }
}

fn produce_rocks_1(contents: String) -> (Vec<Vec<char>>, usize, usize) {
    let highest_y = contents.lines()
    .map(|line| line.split(" -> ")
        .map(|pair| pair.split(",").last().unwrap().parse::<usize>().unwrap())
        .max().unwrap())
    .max().unwrap();
    let highest_x = contents.lines()
    .map(|line| line.split(" -> ")
        .map(|pair| pair.split(",").next().unwrap().parse::<usize>().unwrap())
        .max().unwrap())
    .max().unwrap() + 1;
    let lowest_x = contents.lines()
    .map(|line| line.split(" -> ")
        .map(|pair| pair.split(",").next().unwrap().parse::<usize>().unwrap())
        .min().unwrap())
    .min().unwrap() - 1;
    let mut vec = vec![vec!['.'; (highest_x - lowest_x) + 1]; highest_y + 1];
    add_rocks(contents, lowest_x, &mut vec);
    return (vec, highest_y, lowest_x);
}

fn produce_rocks_2(contents: String) -> (Vec<Vec<char>>, usize, usize) {
    let highest_y = contents.lines()
    .map(|line| line.split(" -> ")
        .map(|pair| pair.split(",").last().unwrap().parse::<usize>().unwrap())
        .max().unwrap())
    .max().unwrap() + 2;
    let highest_x = 500 + (highest_y);
    let lowest_x = 500 - (highest_y);
    let mut vec = vec![vec!['.'; (highest_x - lowest_x) + 1]; highest_y + 1];
    add_rocks(contents, lowest_x, &mut vec);
    for x in 0..=highest_x - lowest_x {
        vec[highest_y][x] = 'X';
    }
    return (vec, highest_y, lowest_x);
}

fn add_rocks(contents: String, lowest_x: usize, vec: &mut Vec<Vec<char>>) {
    let rows = contents.lines()
        .map(|line| line.split(" -> ")
            .map(|pair| pair.split(",")
                .map(|num| num.parse::<usize>().unwrap())
                .next_tuple::<(usize, usize)>()).tuple_windows());
    for row in rows {
        for (a, b) in row {
            let first = a.unwrap();
            let last = b.unwrap();
            if first.0 > last.0 {
                for x in last.0 - lowest_x..=first.0 - lowest_x {
                    vec[first.1][x] = 'X';
                }
            } else if first.0 < last.0 {
                for x in first.0 - lowest_x..=last.0 - lowest_x {
                    vec[first.1][x] = 'X';
                }
            } else if first.1 > last.1 {
                for y in last.1..=first.1 {
                    vec[y][first.0 - lowest_x] = 'X';
                }
            } else if first.1 < last.1 {
                for y in first.1..=last.1 {
                    vec[y][first.0 - lowest_x] = 'X';
                }
            }
        }
    }
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input14.txt".to_owned()), 901);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input14.txt".to_owned()), 24589);
    }
}
