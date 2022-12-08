use std::fs;

use itertools::Itertools;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let len = contents.find("\n").unwrap();
    let forest = contents.chars()
        .filter(|chr| chr != &'\n')
        .map(|num| num.to_digit(10).unwrap() as i32)
        .collect::<Vec<i32>>();
    let depth = forest.len() / len;
    let mut visible:Vec<usize> = Vec::new();
    for y in 0..depth {
        let mut max = -1;
        for x in 0..len {
            let height = forest[y*len + x];
            if height > max {
                max = height;
                visible.push(y*len + x);
            }
            if height == 9 {
                break;
            }
        } 
        max = -1;
        for x in (0..len).rev() {
            let height = forest[y*len + x];
            if forest[y*len + x] > max {
                max = forest[y*len + x];
                visible.push(y*len + x);
            }
            if height == 9 {
                break;
            }
        } 
    }
    for x in (0..len).rev() {
        let mut max = -1;
        for y in 0..depth {
            let height = forest[y*len + x];
            if forest[y*len + x] > max {
                max = forest[y*len + x];
                visible.push(y*len + x);
            }
            if height == 9 {
                break;
            }
        } 
        max = -1;
        for y in (0..depth).rev() {
            let height = forest[y*len + x];
            if forest[y*len + x] > max {
                max = forest[y*len + x];
                visible.push(y*len + x);
            }
            if height == 9 {
                break;
            }
        } 
    }
    let some = visible.iter().unique().collect::<Vec<&usize>>();
    return some.len() as i32;
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
    .expect("Should have been able to read the file");

    let len = contents.find("\n").unwrap();
    let forest = contents.chars()
        .filter(|chr| chr != &'\n')
        .map(|num| num.to_digit(10).unwrap() as i32)
        .collect::<Vec<i32>>();
    let depth = forest.len() / len;

    let mut scores: Vec<i32> = forest.clone();
    
    for y in 1..depth {
        for x in 1..len {
            let height = forest[y*len + x];
            let mut left = 0;
            let mut right = 0;
            let mut up = 0;
            let mut down = 0;
            let mut i = (x - 1) as i32;
            while i >= 0 {
                left += 1;
                if forest[y*len + i as usize] >= height {
                    break;
                }
                i -= 1;
            }
            i = (x + 1) as i32;
            while i < len as i32 {
                right += 1;
                if forest[y*len + i as usize] >= height {
                    break;
                }
                i += 1;
            }
            let mut j = (y - 1) as i32;
            while j >= 0 {
                up += 1;
                if forest[(j as usize *len + x as usize) as usize] >= height {
                    break;
                }
                j -= 1;
            }
            j = (y + 1) as i32;
            while j < depth as i32 {
                down += 1;
                if forest[(j as usize *len + x as usize)] >= height {
                    break;
                }
                j += 1;
            }
            scores[y*len + x as usize] = left * right * up * down;
        }
    }
    return *scores.iter().max().unwrap();
}