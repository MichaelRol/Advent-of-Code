use std::{fs, num::ParseIntError, str::FromStr, collections::VecDeque};

use itertools::Itertools;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let lines = contents.clone();
    let mut items = lines.split("\n\n").map(|sec| sec.lines().collect::<Vec<&str>>()[1].split(": ")
        .skip(1)
        .next()
        .unwrap()
        .split(", ")
        .map(|num| num.parse().unwrap())
        .collect::<VecDeque<u128>>())
    .collect::<Vec<VecDeque<u128>>>();
    let mut monkeys = contents.split("\n\n").map(|line| line.parse::<Monkey>().unwrap()).collect::<Vec<Monkey>>();
    
    for _ in 0..20 {
        for mon in 0..8 {
            while !items[mon].is_empty() {
                monkeys[mon].count += 1;
                let mut worry = items[mon].pop_front().unwrap();
                worry = inspect(worry, monkeys[mon].op_op, monkeys[mon].op_num);
                worry /= 3;
                let curr_monkey = &monkeys[mon];
                if worry % monkeys[mon].test_num == 0 {
                    items[curr_monkey.true_monkey].push_back(worry);
                } else {
                    items[curr_monkey.false_monkey].push_back(worry);
                }
            }
        }
    }
    let sorted: Vec<u128> = monkeys.iter().map(|monk| monk.count).sorted().rev().collect();
    return (sorted[0] * sorted[1]) as i32;
}

pub fn part2(path: String) -> i128 {    
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let lines = contents.clone();
    let mut items = lines.split("\n\n").map(|sec| sec.lines().collect::<Vec<&str>>()[1].split(": ")
        .skip(1)
        .next()
        .unwrap()
        .split(", ")
        .map(|num| num.parse().unwrap())
        .collect::<VecDeque<u128>>())
    .collect::<Vec<VecDeque<u128>>>();
    let mut monkeys = contents.split("\n\n").map(|line| line.parse::<Monkey>().unwrap()).collect::<Vec<Monkey>>();
    let lcm = monkeys.iter().map(|m| m.test_num).reduce(|x, y| x*y).unwrap();
    for _ in 0..10000 {
        for mon in 0..8 {
            while !items[mon].is_empty() {
                monkeys[mon].count += 1;
                let mut worry = items[mon].pop_front().unwrap();
                worry = inspect(worry, monkeys[mon].op_op, monkeys[mon].op_num);
                let curr_monkey = &monkeys[mon];
                worry = worry % lcm;
                if worry % monkeys[mon].test_num == 0 {
                    items[curr_monkey.true_monkey].push_back(worry);
                } else {
                    items[curr_monkey.false_monkey].push_back(worry);
                }
            }
        }
    }
    let sorted: Vec<u128> = monkeys.iter().map(|monk| monk.count).sorted().rev().collect();
    return (sorted[0] * sorted[1]) as i128;
}

fn inspect(item: u128, op_op: char, mut op_num: u128, ) -> u128 {
    if op_num == 0 {
        op_num = item;
    }
    match op_op {
        '+' => return item + op_num,
        '*' => return item * op_num,
        _ => unreachable!("Unknown operation"),
    }
}

#[derive(Clone)]
struct Monkey {
    op_num: u128,
    op_op: char,
    test_num: u128,
    true_monkey: usize,
    false_monkey: usize,
    count: u128,
}

impl FromStr for Monkey {
    type Err = ParseIntError;
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let lines = s.clone().lines().collect::<Vec<&str>>();
        let mut op_line = lines[2].chars().rev();
        let op_num_som = op_line.next().unwrap();
        let op_num: u128;
        if op_num_som == 'd' {
            op_num = 0;
        } else {
            op_num = op_num_som.to_digit(10).unwrap() as u128;
        }
        let op_line = lines[2].split(" ").collect::<Vec<&str>>();
        let op_op = op_line[6].chars().next().unwrap();
        let test_num: u128 = lines[3].split("by ").last().unwrap().parse().unwrap();
        let true_monkey: usize = lines[4].split("monkey ").last().unwrap().parse().unwrap();
        let false_monkey: usize = lines[5].split("monkey ").last().unwrap().parse().unwrap();
        return Ok( Monkey { 
            op_num,
            op_op,
            test_num,
            true_monkey,
            false_monkey,
            count: 0,
        });
    }
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input11.txt".to_owned()), 90882);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input11.txt".to_owned()), 30893109657);
    }
}
