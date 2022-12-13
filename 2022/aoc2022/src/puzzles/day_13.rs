use std::{fs, cmp::Ordering};

use itertools::Itertools;
use json::JsonValue;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    return contents.split("\n\n")
        .map(|section| section.lines()
            .map(|line| json::parse(line).unwrap())
            .collect::<Vec<JsonValue>>())
        .map(|pair| compare_pairs(pair))
        .enumerate()
        .filter(|x| x.1 == Ordering::Less)
        .map(|x| x.0 + 1)
        .sum::<usize>() as i32;
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file") + "\n[[2]]\n[[6]]";
    return contents.lines()
        .map(|line| json::parse(line))
        .filter(|line_opt| line_opt.is_ok())
        .map(|line| line.unwrap()).sorted_by(|a, b| compare_pairs(vec!(a.clone(), b.clone())))
        .enumerate()
        .filter(|(_, v)| *v == json::parse("[[2]]").unwrap() || *v == json::parse("[[6]]").unwrap())
        .map(|x| x.0 + 1)
        .product::<usize>() as i32;
}

fn compare_pairs(pair: Vec<JsonValue>) -> Ordering {
    let left = pair[0].members();
    let mut right = pair[1].members();
    for left_member in left {
        let right_member = right.next();
        if right_member.is_none() {
            return Ordering::Greater;
        }
        if left_member.is_number() && right_member.unwrap().is_number() {
            if left_member.as_i32() < right_member.unwrap().as_i32() {
                return Ordering::Less;
            } else if left_member.as_i32() > right_member.unwrap().as_i32() {
                return Ordering::Greater;
            }
            continue;
        }
        if left_member.is_array() && right_member.unwrap().is_array() {
            let result = compare_pairs(vec![left_member.clone(), right_member.unwrap().clone()]);
            if result == Ordering::Equal {
                continue;
            }
            return result;
        }
        if left_member.is_array() {
            let result = compare_pairs(vec![left_member.clone(), JsonValue::Array(vec![right_member.unwrap().clone()])]);
            if result == Ordering::Equal {
                continue;
            }
            return result;
        }
        let result = compare_pairs(vec![JsonValue::Array(vec![left_member.clone()]), right_member.unwrap().clone()]);
        if result == Ordering::Equal {
            continue;
        }
        return result;
    }
    let right_member = right.next();
    if right_member.is_some() {
        return Ordering::Less;
    }
    return Ordering::Equal;
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input13.txt".to_owned()), 5208);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input13.txt".to_owned()), 25792);
    }
}
