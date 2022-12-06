use std::{fs, str::FromStr, num::ParseIntError};

pub fn part1(path: String) -> String {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    
    let mut parts = contents.split("\n\n");
    let stack_string = parts.next()
        .expect("Could not extract stacks from input.")
        .chars()
        .rev()
        .collect::<String>();
    let mut stacks = produce_stacks(stack_string);

    let moves = parts.next().expect("Could not extract moves from input.");
    moves.lines()
        .map(|line| line.parse::<Move>().expect("Could not parse move."))
        .for_each(|m0ve| m0ve.apply9000(&mut stacks));

    let tops: String = stacks.into_iter().map(|mut st| st.pop().unwrap()).collect();
    return tops;
}

pub fn part2(path: String) -> String {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    
    let mut parts = contents.split("\n\n");
    let stack_string = parts.next()
        .expect("Could not extract stacks from input.")
        .chars()
        .rev()
        .collect::<String>();
    let mut stacks = produce_stacks(stack_string);

    let moves = parts.next().expect("Could not extract moves from input.");
    moves.lines()
        .map(|line| line.parse::<Move>().expect("Could not parse move."))
        .for_each(|m0ve| m0ve.apply9001(&mut stacks));

    let tops: String = stacks.into_iter().map(|mut st| st.pop().unwrap()).collect();
    return tops;
}

fn produce_stacks(stack_string: String) -> Vec<Vec<char>> {
    let mut stacks: Vec<Vec<char>> = Vec::new();
    let rows = stack_string.lines();
    let num_of_stacks = (rows.clone().next().unwrap().len() + 1) / 4;
    for _ in 0..num_of_stacks {
        let stack: Vec<char> = Vec::new();
        stacks.push(stack);
    }
    for line in rows {
        let mut pos = 0;
        for chr in line.chars() {
            if chr.is_alphabetic() {
                let stack_num = (num_of_stacks - 1) - ((pos - 1) / 4);
                stacks[stack_num].push(chr);
            }
            pos += 1;
        }
    }
    return stacks;
}

struct Move {
    src: usize,
    dest: usize,
    count: usize
}

impl Move {
    fn apply9000(&self, stacks: &mut Vec<Vec<char>>) {
        for _ in 0..self.count {
            let in_transit = stacks[self.src - 1].pop().expect("This stack is empty!");
            stacks[self.dest - 1].push(in_transit)
        }
    }

    fn apply9001(&self, stacks: &mut Vec<Vec<char>>) {
        let mut temp: Vec<char> = Vec::with_capacity(self.count);
        for _ in 0..self.count {
            let in_transit = stacks[self.src - 1].pop().expect("This stack is empty!");
            temp.push(in_transit)
        }
        for _ in 0..self.count {
            let in_transit = temp.pop().expect("This stack is empty!");
            stacks[self.dest - 1].push(in_transit)
        }
    }
}

impl FromStr for Move {
    type Err = ParseIntError;
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        // bit hacky, but fortunately every part of the string we want to remove contains an "o" character.
        let ints: Vec<&str> = s.split(" ").collect();
        return Ok( Move { 
            count: ints[1].parse()?, 
            src: ints[3].parse()?, 
            dest: ints[5].parse()?, 
        });
    }
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input5.txt".to_owned()), "LBLVVTVLP");
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input5.txt".to_owned()), "TPFFBDRJD");
    }
}