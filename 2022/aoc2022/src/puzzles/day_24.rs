use std::{fs, vec, collections::HashSet};

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let mut map = contents.lines()
        .map(|line| line.chars()
            .map(|chr| if chr == '.' {vec![]} else {vec![chr]})
            .collect::<Vec<Vec<char>>>())
        .collect::<Vec<Vec<Vec<char>>>>();

    let positions: HashSet<(usize, usize)> = HashSet::from([(0, 1)]);
    let target = (map.len() - 1, map[0].len() - 2);
    let mut count = 0;
    update_positions(positions, target, &mut map, &mut count);
    return count;
}

pub fn part2(path: String) -> i32 { 
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let mut map = contents.lines()
        .map(|line| line.chars()
            .map(|chr| if chr == '.' {vec![]} else {vec![chr]})
            .collect::<Vec<Vec<char>>>())
        .collect::<Vec<Vec<Vec<char>>>>();

    let map_height = map.len() - 1;
    let map_width = map[0].len() - 1;

    let mut count = 0;

    // go to exit
    let mut positions: HashSet<(usize, usize)> = HashSet::from([(0, 1)]);
    let mut target = (map_height, map_width - 1);
    update_positions(positions, target, &mut map, &mut count);

    // return to entrance
    positions = HashSet::from([(map_height, map_width - 1)]);
    target = (0, 1);
    update_positions(positions, target, &mut map, &mut count);

    // back to exit
    positions = HashSet::from([(0, 1)]);
    target = (map_height, map_width - 1);
    update_positions(positions, target, &mut map, &mut count);

    return count;
}

fn update_positions(
    mut positions: HashSet<(usize, usize)>, 
    target: (usize, usize), 
    map: &mut Vec<Vec<Vec<char>>>, 
    count: &mut i32) {

    while !positions.contains(&target) {
        progress_map(map);
        let mut new_positions: HashSet<(usize, usize)> = HashSet::new();
        for pos in &positions {
            if map[pos.0][pos.1].is_empty() {
                new_positions.insert(*pos);
            }
            if pos.0 != map.len() - 1 && map[pos.0 + 1][pos.1].is_empty() {
                new_positions.insert((pos.0 + 1, pos.1));
            }
            if pos.0 != 0 && map[pos.0 - 1][pos.1].is_empty() {
                new_positions.insert((pos.0 - 1, pos.1));
            }
            if map[pos.0][pos.1 + 1].is_empty() {
                new_positions.insert((pos.0, pos.1 + 1));
            }
            if map[pos.0][pos.1 - 1].is_empty() {
                new_positions.insert((pos.0, pos.1 - 1));
            }
        }
        positions = new_positions;
        *count += 1;
    }
}

fn progress_map(map: &mut Vec<Vec<Vec<char>>>) {
    let mut new_map: Vec<Vec<Vec<char>>> = map.clone();

    for y in 1..map.len() - 1 {
        for x in 1..map[y].len() - 1 {
            new_map[y][x] = vec![];
        }
    }

    for y in 1..map.len() - 1 {
        for x in 1..map[y].len() - 1 {
            if !map[y][x].is_empty() {
                for bliz in &map[y][x] {
                    match bliz {
                        '<' => {
                            if x == 1 {
                                new_map[y][map[y].len() - 2].push('<');
                            } else {
                                new_map[y][x-1].push('<');
                            }
                        }, 
                        '>' => {
                            if x == map[y].len() - 2 {
                                new_map[y][1].push('>');
                            } else {
                                new_map[y][x+1].push('>');
                            }
                        },
                        '^' => {
                            if y == 1 {
                                new_map[map.len() - 2][x].push('^');
                            } else {
                                new_map[y-1][x].push('^');
                            }
                        },
                        'v' => {
                            if y == map.len() - 2 {
                                new_map[1][x].push('v');
                            } else {
                                new_map[y+1][x].push('v');
                            }
                        },
                        _ => unreachable!("Unknown blizzard type: {}", bliz),
                    }
                }
            }
        }
    }
    *map = new_map;
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input24.txt".to_owned()), 255);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input24.txt".to_owned()), 809);
    }
}
