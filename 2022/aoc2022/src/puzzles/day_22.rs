use std::{fs, ops::ControlFlow};

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let mut both = contents.split("\n\n");
    let mut map = both.next().unwrap().lines()
        .map(|line| line.chars().collect())
        .collect::<Vec<Vec<char>>>();
    let directions = both.next().unwrap().chars();

    let mut pos: (usize, usize) = (0, 0);
    let mut dir: i8 = 0;
    let map_width = map.iter().map(|line| line.len()).max().unwrap();
    let map_height = map.len();

    // some rows aren't as long as others, fill missing space with spaces
    for y in 0..map_height {
        while map[y].len() != map_width {
            map[y].push(' ');
        }
    }

    // find the starting spot: first available spot on the top row from the left
    for x in 0..map_width {
        if map[0][x] == '.' {
            pos.1 = x;
            break;
        }
    }

    let mut buffer = vec![];
    for chr in directions {
        if chr.is_alphabetic() {
            make_move(&mut buffer, &mut dir, &map, &mut pos, map_width, map_height, chr);
        } else if chr.is_numeric() {
            buffer.push(chr);
        }
    }
    if !buffer.is_empty() {
        make_move(&mut buffer, &mut dir, &map, &mut pos, map_width, map_height, '_');
    }
    return (1000 * (pos.0 + 1) + 4 * (pos.1 + 1) + dir as usize) as i32;
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let mut both = contents.split("\n\n");
    let mut map = both.next().unwrap().lines()
        .map(|line| line.chars().collect())
        .collect::<Vec<Vec<char>>>();
    let directions = both.next().unwrap().chars();

    let mut pos: (usize, usize) = (0, 0);
    let mut dir: i8 = 0;
    let map_width = map.iter().map(|line| line.len()).max().unwrap();
    let map_height = map.len();

    // some rows aren't as long as others, fill missing space with spaces
    for y in 0..map_height {
        while map[y].len() != map_width {
            map[y].push(' ');
        }
    }

    // find the starting spot: first available spot on the top row from the left
    for x in 0..map_width {
        if map[0][x] == '.' {
            pos.1 = x;
            break;
        }
    }

    let mut buffer = vec![];
    for chr in directions {
        if chr.is_alphabetic() {
            make_move_2(&mut buffer, &mut dir, &map, &mut pos, map_width, map_height, chr);
        } else if chr.is_numeric() {
            buffer.push(chr);
        }
    }
    if !buffer.is_empty() {
        make_move_2(&mut buffer, &mut dir, &map, &mut pos, map_width, map_height, '_');
    }
    return (1000 * (pos.0 + 1) + 4 * (pos.1 + 1) + dir as usize) as i32;
}

fn find_zone(pos: (usize, usize)) -> u8 {
    if pos.0 >= 150 {
        return 1;
    }
    if pos.1 < 50 {
        return 2;
    }
    if pos.1 >= 100 {
        return 6;
    }
    if pos.0 < 50 {
        return 5;
    }
    if pos.0 < 100 {
        return 4;
    }
    return 3;
}

fn make_move(buffer: &mut Vec<char>, dir: &mut i8, map: &Vec<Vec<char>>, pos: &mut (usize, usize), map_width: usize, map_height: usize, chr: char) {
    let dist = buffer.iter().collect::<String>().parse::<u8>().unwrap();
    *buffer = vec![];
    for _ in 0..dist {
        match *dir {
            0 => if let ControlFlow::Break(_) = move_right(map, pos, map_width) { break; },
            1 => if let ControlFlow::Break(_) = move_down(map, pos, map_height) { break; },
            2 => if let ControlFlow::Break(_) = move_left(map, pos, map_width) { break; },
            3 => if let ControlFlow::Break(_) = move_up(map, pos, map_height) { break; },
            _ => unreachable!("Dir should only within 0-3."),
        }
    }
    if chr == 'R' {
        *dir = (*dir + 1).rem_euclid(4);
    } else if chr == 'L' {
        *dir = (*dir - 1).rem_euclid(4);
    }
}

fn move_right(map: &Vec<Vec<char>>, pos: &mut (usize, usize), map_width: usize) -> ControlFlow<()> {
    let mut next_char = map[pos.0][(pos.1 + 1) % map_width];
    if next_char == '.' { 
        pos.1 = (pos.1 + 1) % map_width; 
    } else if next_char == ' ' {
        let mut temp = pos.1;
        while next_char == ' ' {
            temp = (temp + 1) % map_width;
            next_char = map[pos.0][temp];
        }
        if next_char == '.' {
            pos.1 = temp;
        } else {
            return ControlFlow::Break(());
        }
    } else { 
        return ControlFlow::Break(()); 
    }
    ControlFlow::Continue(())
}

fn move_down(map: &Vec<Vec<char>>, pos: &mut (usize, usize), map_height: usize) -> ControlFlow<()> {
    let mut next_char = map[(pos.0 + 1) % map_height][pos.1];
    if next_char == '.' { 
        pos.0 = (pos.0 + 1) % map_height; 
    } else if next_char == ' ' {
        let mut temp = pos.0;
        while next_char == ' ' {
            temp = (temp + 1) % map_height;
            next_char = map[temp][pos.1];
        }
        if next_char == '.' {
            pos.0 = temp;
        } else {
            return ControlFlow::Break(());
        }
    } else { 
        return ControlFlow::Break(()); 
    }
    ControlFlow::Continue(())
}

fn move_up(map: &Vec<Vec<char>>, pos: &mut (usize, usize), map_height: usize) -> ControlFlow<()> {
    let mut next_char = map[(pos.0 as i32 - 1).rem_euclid(map_height as i32) as usize][pos.1];
    if next_char == '.' { 
        pos.0 = (pos.0 - 1).rem_euclid(map_height); 
    } else if next_char == ' ' {
        let mut temp = pos.0;
        while next_char == ' ' {
            temp = (temp as i32 - 1).rem_euclid(map_height as i32) as usize;
            next_char = map[temp % map_height][pos.1];
        }
        if next_char == '.' {
            pos.0 = temp;
        } else {
            return ControlFlow::Break(());
        }
    } else { 
        return ControlFlow::Break(()); 
    }
    ControlFlow::Continue(())
}

fn move_left(map: &Vec<Vec<char>>, pos: &mut (usize, usize), map_width: usize) -> ControlFlow<()> {
    let mut next_char = map[pos.0][(pos.1 as i32 - 1).rem_euclid(map_width as i32) as usize];
    if next_char == '.' { 
        pos.1 = (pos.1 - 1).rem_euclid(map_width); 
    } else if next_char == ' ' {
        let mut temp = pos.1;
        while next_char == ' ' {
            temp  = (temp as i32 - 1).rem_euclid(map_width as i32) as usize;
            next_char = map[pos.0][temp];
        }
        if next_char == '.' {
            pos.1 = temp;
        } else {
            return ControlFlow::Break(());
        }
    } else { 
        return ControlFlow::Break(()); 
    }
    ControlFlow::Continue(())
}

fn make_move_2(buffer: &mut Vec<char>, dir: &mut i8, map: &Vec<Vec<char>>, pos: &mut (usize, usize), map_width: usize, map_height: usize, chr: char) {
    let dist = buffer.iter().collect::<String>().parse::<u8>().unwrap();
    *buffer = vec![];
    for _ in 0..dist {
        match *dir {
            0 => if let ControlFlow::Break(_) = move_right_2(map, pos, dir, map_width) { break; },
            1 => if let ControlFlow::Break(_) = move_down_2(map, pos, dir, map_height) { break; },
            2 => if let ControlFlow::Break(_) = move_left_2(map, pos, dir) { break; },
            3 => if let ControlFlow::Break(_) = move_up_2(map, pos, dir) { break; },
            _ => unreachable!("Dir should only within 0-3."),
        }
    }
    if chr == 'R' {
        *dir = (*dir + 1).rem_euclid(4);
    } else if chr == 'L' {
        *dir = (*dir - 1).rem_euclid(4);
    }
}

fn move_right_2(map: &Vec<Vec<char>>, pos: &mut (usize, usize), dir: &mut i8, map_width: usize) -> ControlFlow<()> {
    if pos.1 == map_width - 1 || map[pos.0][pos.1 + 1] == ' ' {
        let zone = find_zone(*pos);
        match zone {
            1 => {
                let next_pos = (149, pos.0 - 100);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 3;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            2 | 5 => {
                let next_pos = (pos.0, pos.1 + 1);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            3 => {
                let next_pos = (149 - pos.0, 149);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 2;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            4 => {
                let next_pos = (49, pos.0 + 50);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 3;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            6 => {
                let next_pos = (149 - pos.0, 99);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 2;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            _ => unreachable!("Zone {} does not exist.", zone)
        }
    } else if map[pos.0][pos.1 + 1] == '.' {
        pos.1 += 1;
        return ControlFlow::Continue(()); 
    } else {
        return ControlFlow::Break(());
    }
    return ControlFlow::Continue(()); 
}

fn move_down_2(map: &Vec<Vec<char>>, pos: &mut (usize, usize), dir: &mut i8, map_height: usize) -> ControlFlow<()> {
    if pos.0 == map_height - 1 || map[pos.0 + 1][pos.1] == ' ' {
        let zone = find_zone(*pos);
        match zone {
            1  => {
                let next_pos = (0, 100 + pos.1);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 1;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            2 | 4 | 5 => {
                let next_pos = (pos.0 + 1, pos.1);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            3 => {
                let next_pos = (pos.1 + 100, 49);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 2;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            6 => {
                let next_pos = (pos.1 - 50, 99);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 2;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            _ => unreachable!("Zone {} does not exist.", zone)
        }
    } else if map[pos.0 + 1][pos.1] == '.' {
        pos.0 += 1;
        return ControlFlow::Continue(()); 
    } else {
        return ControlFlow::Break(());
    }
    return ControlFlow::Continue(()); 
}

fn move_up_2(map: &Vec<Vec<char>>, pos: &mut (usize, usize), dir: &mut i8) -> ControlFlow<()> {
    if pos.0 == 0 || map[pos.0 - 1][pos.1] == ' ' {
        let zone = find_zone(*pos);
        match zone {
            2  => {
                let next_pos = (50 + pos.1, 50);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 0;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            1 | 3 | 4 => {
                let next_pos = (pos.0 - 1, pos.1);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            5 => {
                let next_pos = (pos.1 + 100, 0);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 0;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            6 => {
                let next_pos = (199, pos.1 - 100);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 3;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            _ => unreachable!("Zone {} does not exist.", zone)
        }
    } else if map[pos.0 - 1][pos.1] == '.' {
        pos.0 -= 1;
        return ControlFlow::Continue(()); 
    } else {
        return ControlFlow::Break(());
    }
    return ControlFlow::Continue(()); 
}

fn move_left_2(map: &Vec<Vec<char>>, pos: &mut (usize, usize), dir: &mut i8) -> ControlFlow<()> {
    if pos.1 == 0 || map[pos.0][pos.1 - 1] == ' ' {
        let zone = find_zone(*pos);
        match zone {
            1 => {
                let next_pos = (0, pos.0 - 100);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 1;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            3 | 6 => {
                let next_pos = (pos.0, pos.1 - 1);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            2 => {
                let next_pos = (149 - pos.0, 50);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 0;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            4 => {
                let next_pos = (100, pos.0 - 50);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 1;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            5 => {
                let next_pos = (149 - pos.0, 0);
                if map[next_pos.0][next_pos.1] == '.' {
                    pos.0 = next_pos.0;
                    pos.1 = next_pos.1;
                    *dir = 0;
                } else {
                    return ControlFlow::Break(()); 
                }
            },
            _ => unreachable!("Zone {} does not exist.", zone)
        }
    } else if map[pos.0][pos.1 - 1] == '.' {
        pos.1 -= 1;
        return ControlFlow::Continue(()); 
    } else {
        return ControlFlow::Break(())
    }
    return ControlFlow::Continue(()); 
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input22.txt".to_owned()), 131052);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input22.txt".to_owned()), 4578);
    }
}
