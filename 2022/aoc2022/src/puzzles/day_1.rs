use std::fs;

use itertools::Itertools;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    return contents.split("\n\n")
        .map(|group| group.split("\n")
            .map(|num| num.parse::<i32>().unwrap())
            .sum())
        .max()
        .unwrap();
}

pub fn part2(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    return contents.split("\n\n")
        .map(|group| group.split("\n")
            .map(|num| num.parse::<i32>().unwrap())
            .sum::<i32>())
        .sorted_by(|a, b| Ord::cmp(b, a))
        .take(3)
        .sum();
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input1.txt".to_owned()), 67622);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input1.txt".to_owned()), 201491);
    }
}