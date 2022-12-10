use std::{fs, str::FromStr, num::ParseIntError};

use itertools::Itertools;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let moves = contents.lines().map(|line| line.parse().unwrap()).collect::<Vec<Move>>();
    let mut head = (0, 0);
    let mut tail = (0, 0);
    let mut history: Vec<(i32, i32)> = Vec::new();
    history.push(tail);
    for a_move in moves {
        match a_move.dir {
            'R' => head.0 += a_move.dist,
            'L' => head.0 -= a_move.dist,
            'U' => head.1 += a_move.dist,
            'D' => head.1 -= a_move.dist,
            _ => panic!("Unknown move direction {}", a_move.dir),
        } 
        while further_than_one(head, tail) {
            let dif_x = head.0 - tail.0;
            let abs_x = dif_x.abs();
            let dif_y = head.1 - tail.1;
            let abs_y = dif_y.abs();
            if abs_x > 0 && abs_y > 0 {
                tail.0 += dif_x/abs_x;
                tail.1 += dif_y/abs_y;
            } else if dif_x.abs() > 0 {
                tail.0 += dif_x/abs_x
            } else {
                tail.1 += dif_y/abs_y
            }
            history.push(tail);
        }
    }
    return history.iter().unique().count() as i32;
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let moves = contents.lines().map(|line| line.parse().unwrap()).collect::<Vec<Move>>();
    let mut rope = Vec::with_capacity(10);
    for _ in 0..10 {
        rope.push((0, 0))
    }
    let mut history: Vec<(i32, i32)> = Vec::new();
    history.push(*rope.last().unwrap());
    for a_move in moves {
        match a_move.dir {
            'R' => rope[0].0 += a_move.dist,
            'L' => rope[0].0 -= a_move.dist,
            'U' => rope[0].1 += a_move.dist,
            'D' => rope[0].1 -= a_move.dist,
            _ => panic!("Unknown move direction {}", a_move.dir),
        } 
        while !all_together(&rope) {
            for x in 0..9 {
                if further_than_one(rope[x], rope[x+1]) {
                    let dif_x = rope[x].0 - rope[x+1].0;
                    let dif_y = rope[x].1 - rope[x+1].1;
                    if dif_x.abs() > 0 && dif_y.abs() > 0 {
                        rope[x+1].0 += dif_x/dif_x.abs();
                        rope[x+1].1 += dif_y/dif_y.abs();
                    } else if dif_x.abs() > 0 {
                        rope[x+1].0 += dif_x/dif_x.abs();
                    } else {
                        rope[x+1].1 += dif_y/dif_y.abs();
                    }
                    if x == 8 {
                        history.push(rope[x+1]);
                    }
                }
            }
        }
    }
    return history.iter().unique().count() as i32;
}

fn all_together(rope: &Vec<(i32, i32)>) -> bool {
    for x in 0..rope.len() - 1 {
        if further_than_one(rope[x], rope[x+1]) {
            return false;
        }
    }
    return true;
}

fn further_than_one(head: (i32, i32), tail: (i32, i32)) -> bool {
    let dif_x = head.0 - tail.0;
    let dif_y = head.1 - tail.1;
    return dif_x.abs() > 1 || dif_y.abs() > 1;
}
struct Move {
    dir: char,
    dist: i32,
}

impl FromStr for Move {
    type Err = ParseIntError;
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let mut pairs = s.split(" ");
        return Ok( Move { 
            dir: pairs.next().unwrap().chars().next().unwrap(), 
            dist: pairs.next().unwrap().parse()?,
        });
    }
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input9.txt".to_owned()), 6284);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input9.txt".to_owned()), 2661);
    }
}