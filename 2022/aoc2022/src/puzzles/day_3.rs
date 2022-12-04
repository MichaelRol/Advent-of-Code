extern crate itertools;

use itertools::Itertools;
use std::fs;

pub fn part1(path: String) -> i32 {

    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let result = contents.lines().map(|line| calc_priority(line.to_string())).sum::<i32>();
    return result;
}

pub fn part2(path: String) -> i32 {

    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    return contents.lines()
            .collect::<Vec<&str>>()
            .into_iter()
            .tuples()
            .map(|(a, b, c)| find_common_letter_three(a, b, c))
            .map(|character| find_priority(character))
            .sum::<i32>();
}

fn calc_priority(bag: String) -> i32 {
    let (pocket0, pocket1) = bag.split_at(bag.len() / 2);
    let duplicate = find_common_letter(pocket0, pocket1);
    return find_priority(duplicate);
}

fn find_common_letter(string0: &str, string1: &str) -> char {
    for char0 in string0.chars() {
        for char1 in string1.chars() {
            if char0 == char1 {
                return char0;
            }
        }
    }
    panic!("No duplicate letters were found");
}

fn find_common_letter_three(string0: &str, string1: &str, string2: &str) -> char {
    let mut common: Vec<char> = Vec::new();
    for char0 in string0.chars() {
        for char1 in string1.chars() {
            if char0 == char1 {
                common.push(char0);
            }
        }
    }
    return find_common_letter(&common.into_iter().collect::<String>(), string2);
}

fn find_priority(the_char: char) -> i32 {
    if the_char.is_lowercase() {
        return the_char as i32 - 96;
    }
    return the_char as i32 - 38;
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input3.txt".to_owned()), 8349);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input3.txt".to_owned()), 2681);
    }
}