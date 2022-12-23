use std::{fs, collections::{HashSet, HashMap}};

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let mut elves: HashSet<(i32, i32)> = HashSet::new();
    let mut moves: HashMap<(i32, i32), Option<(i32, i32)>> = HashMap::new();
    let mut direction = 0;
    let mut count = 0;
    for (y, line) in contents.lines().enumerate() {
        for (x, char) in line.chars().enumerate() {
            if char == '#' {
                count += 1;
                elves.insert((y as i32, x as i32));
            }
        }
    }

    for _ in 0..10 {
        moves.clear();
        for elf in &elves {
            if !no_neighbors(*elf, &elves) {
                let mut attempt = direction;
                for _ in 0..4 {
                    match attempt {
                        0 => {
                            if can_move_north(*elf, &elves) {
                                if moves.contains_key(&(elf.0 - 1, elf.1)) {
                                    moves.insert((elf.0 - 1, elf.1), None);
                                } else {
                                    moves.insert((elf.0 - 1, elf.1), Some(*elf));
                                }
                                break;
                            }
                        },
                        1 => {
                            if can_move_south(*elf, &elves) {
                                if moves.contains_key(&(elf.0 + 1, elf.1)) {
                                    moves.insert((elf.0 + 1, elf.1), None);
                                } else {
                                    moves.insert((elf.0 + 1, elf.1), Some(*elf));
                                }
                                break;
                            }
                        },
                        2 => {
                            if can_move_west(*elf, &elves) {
                                if moves.contains_key(&(elf.0, elf.1 - 1)) {
                                    moves.insert((elf.0, elf.1 - 1), None);
                                } else {
                                    moves.insert((elf.0, elf.1 - 1), Some(*elf));
                                }
                                break;
                            }
                        },
                        3 => {
                            if can_move_east(*elf, &elves) {
                                if moves.contains_key(&(elf.0, elf.1 + 1)) {
                                    moves.insert((elf.0, elf.1 + 1), None);
                                } else {
                                    moves.insert((elf.0, elf.1 + 1), Some(*elf));
                                }
                                break;
                            }
                        },
                        _ => unreachable!("Direction should be 0-3")
                    }
                    attempt = (attempt + 1) % 4;
                }
            }
        }

        for (mv, elf) in &moves {
            if elf.is_some() {
                elves.remove(&elf.unwrap());
                elves.insert(*mv);
            }
        }
        direction = (direction + 1) % 4;
    }

    let mut max_x = i32::MIN;
    let mut min_x = i32::MAX;
    let mut max_y = i32::MIN;
    let mut min_y = i32::MAX;
    for (y, x) in elves {
        if x < min_x {
            min_x = x;
        }
        if x > max_x {
            max_x = x;
        }
        if y < min_y {
            min_y = y;
        }
        if y > max_y {
            max_y = y
        }
    }
    return ((max_x + 1 - min_x) * (max_y + 1 - min_y)) - count;
}

pub fn part2(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let mut elves: HashSet<(i32, i32)> = HashSet::new();
    let mut moves: HashMap<(i32, i32), Option<(i32, i32)>> = HashMap::new();
    let mut direction = 0;
    for (y, line) in contents.lines().enumerate() {
        for (x, char) in line.chars().enumerate() {
            if char == '#' {
                elves.insert((y as i32, x as i32));
            }
        }
    }

    for x in 1.. {
        moves.clear();
        for elf in &elves {
            if !no_neighbors(*elf, &elves) {
                let mut attempt = direction;
                for _ in 0..4 {
                    match attempt {
                        0 => {
                            if can_move_north(*elf, &elves) {
                                if moves.contains_key(&(elf.0 - 1, elf.1)) {
                                    moves.insert((elf.0 - 1, elf.1), None);
                                } else {
                                    moves.insert((elf.0 - 1, elf.1), Some(*elf));
                                }
                                break;
                            }
                        },
                        1 => {
                            if can_move_south(*elf, &elves) {
                                if moves.contains_key(&(elf.0 + 1, elf.1)) {
                                    moves.insert((elf.0 + 1, elf.1), None);
                                } else {
                                    moves.insert((elf.0 + 1, elf.1), Some(*elf));
                                }
                                break;
                            }
                        },
                        2 => {
                            if can_move_west(*elf, &elves) {
                                if moves.contains_key(&(elf.0, elf.1 - 1)) {
                                    moves.insert((elf.0, elf.1 - 1), None);
                                } else {
                                    moves.insert((elf.0, elf.1 - 1), Some(*elf));
                                }
                                break;
                            }
                        },
                        3 => {
                            if can_move_east(*elf, &elves) {
                                if moves.contains_key(&(elf.0, elf.1 + 1)) {
                                    moves.insert((elf.0, elf.1 + 1), None);
                                } else {
                                    moves.insert((elf.0, elf.1 + 1), Some(*elf));
                                }
                                break;
                            }
                        },
                        _ => unreachable!("Direction should be 0-3")
                    }
                    attempt = (attempt + 1) % 4;
                }
            }
        }

        let mut moved = false;
        for (mv, elf) in &moves {
            if elf.is_some() {
                moved = true;
                elves.remove(&elf.unwrap());
                elves.insert(*mv);
            }
        }
        if !moved {
            return x;
        }
        direction = (direction + 1) % 4;
    }
    return -1;
}


fn can_move_north(elf: (i32, i32), elves: &HashSet<(i32, i32)>) -> bool {
    return !elves.contains(&(elf.0 - 1, elf.1 + 1)) &&
        !elves.contains(&(elf.0 - 1, elf.1)) && 
        !elves.contains(&(elf.0 - 1, elf.1 - 1));
}


fn can_move_south(elf: (i32, i32), elves: &HashSet<(i32, i32)>) -> bool {
    return !elves.contains(&(elf.0 + 1, elf.1 + 1)) &&
        !elves.contains(&(elf.0 + 1, elf.1)) && 
        !elves.contains(&(elf.0 + 1, elf.1 - 1));
}


fn can_move_west(elf: (i32, i32), elves: &HashSet<(i32, i32)>) -> bool {
    return !elves.contains(&(elf.0 - 1, elf.1 - 1)) &&
        !elves.contains(&(elf.0, elf.1 - 1)) && 
        !elves.contains(&(elf.0 + 1, elf.1 - 1));
}


fn can_move_east(elf: (i32, i32), elves: &HashSet<(i32, i32)>) -> bool {
    return !elves.contains(&(elf.0 - 1, elf.1 + 1)) &&
        !elves.contains(&(elf.0, elf.1 + 1)) && 
        !elves.contains(&(elf.0 + 1, elf.1 + 1));
}

fn no_neighbors(elf: (i32, i32), elves: &HashSet<(i32, i32)>) -> bool {
    return !elves.contains(&(elf.0 + 1, elf.1 + 1)) && 
        !elves.contains(&(elf.0 + 1, elf.1)) && 
        !elves.contains(&(elf.0 + 1, elf.1 - 1)) && 
        !elves.contains(&(elf.0, elf.1 + 1)) && 
        !elves.contains(&(elf.0, elf.1 - 1)) && 
        !elves.contains(&(elf.0 - 1, elf.1 + 1)) &&
        !elves.contains(&(elf.0 - 1, elf.1)) && 
        !elves.contains(&(elf.0 - 1, elf.1 - 1));
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input23.txt".to_owned()), 3882);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input23.txt".to_owned()), 1116);
    }
}
