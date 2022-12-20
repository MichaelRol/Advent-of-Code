use std::{fs, collections::VecDeque};

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let mut nums = contents.lines()
        .map(|line| line.parse::<i32>().unwrap())
        .enumerate()
        .collect::<VecDeque<(usize, i32)>>();
    
    let mut count = 0;

    while count < nums.len() {
        let to_process = nums.pop_front().unwrap();
        if to_process.1 == 0 || to_process.1.abs() as usize % nums.len() == 0 {
            nums.push_front(to_process);
            count += 1;
            if count == nums.len() {
                break;
            }
            while nums.get(0).unwrap().0 != count {
                nums.rotate_left(1);
            }
        } else if to_process.1 > 0 {
            nums.rotate_left(to_process.1.abs() as usize % nums.len());
            nums.push_front(to_process);
            count += 1;
            if count == nums.len() {
                break;
            }
            while nums.get(0).unwrap().0 != count {
                nums.rotate_left(1);
            }
        } else {
            nums.rotate_right(to_process.1.abs() as usize % nums.len());
            nums.push_front(to_process);
            count += 1;
            if count == nums.len() {
                break;
            }
            while nums.get(0).unwrap().0 != count {
                nums.rotate_right(1);
            }
        }
    }

    while nums.get(0).unwrap().1 != 0 {
        nums.rotate_right(1);
    }
    let mut sum = 0;
    let turns = 1000 % nums.len();
    nums.rotate_left(turns);
    sum += nums.get(0).unwrap().1;
    nums.rotate_left(1000 % nums.len());
    sum += nums.get(0).unwrap().1;
    nums.rotate_left(1000 % nums.len());
    sum += nums.get(0).unwrap().1;
    
    return sum;
}

pub fn part2(path: String) -> i128 {    
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let mut nums = contents.lines()
        .map(|line| line.parse::<i128>().unwrap() * 811589153)
        .enumerate()
        .collect::<VecDeque<(usize, i128)>>();
    
    for _ in 0..10 {
        let mut count = 0;
        while nums.get(0).unwrap().0 != count {
            nums.rotate_left(1);
        }
        while count < nums.len() {
            let to_process = nums.pop_front().unwrap();
            if to_process.1 == 0 || to_process.1.abs() as usize % nums.len() == 0 {
                nums.push_front(to_process);
                count += 1;
                if count == nums.len() {
                    break;
                }
                while nums.get(0).unwrap().0 != count {
                    nums.rotate_left(1);
                }
            } else if to_process.1 > 0 {
                nums.rotate_left(to_process.1.abs() as usize % nums.len());
                nums.push_front(to_process);
                count += 1;
                if count == nums.len() {
                    break;
                }
                while nums.get(0).unwrap().0 != count {
                    nums.rotate_left(1);
                }
            } else {
                nums.rotate_right(to_process.1.abs() as usize % nums.len());
                nums.push_front(to_process);
                count += 1;
                if count == nums.len() {
                    break;
                }
                while nums.get(0).unwrap().0 != count {
                    nums.rotate_right(1);
                }
            }
        }
    }

    while nums.get(0).unwrap().1 != 0 {
        nums.rotate_right(1);
    }
    let mut sum = 0;
    let turns = 1000 % nums.len();
    nums.rotate_left(turns);
    sum += nums.get(0).unwrap().1;
    nums.rotate_left(turns);
    sum += nums.get(0).unwrap().1;
    nums.rotate_left(turns);
    sum += nums.get(0).unwrap().1;
    
    return sum;
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input20.txt".to_owned()), 11123);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input20.txt".to_owned()), 4248669215955);
    }
}
