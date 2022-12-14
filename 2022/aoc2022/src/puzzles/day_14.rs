use std::fs;
use std::collections::HashSet;

use itertools::Itertools;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let (mut rock, lowest) = produce_rock(contents);
    let mut count = 0;
    loop {
        let mut sand: (u16, u16) = (500, 0);
        loop {
            let new_pos = next_position(sand, rock.clone());
            if new_pos == sand {
                rock.insert(new_pos);
                break;
            }
            sand = new_pos;
            if new_pos.1 > lowest {
                return count;
            }
        }
        count += 1;
    }
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let (mut rock, mut lowest) = produce_rock(contents);
    lowest += 2;
    for x in 0..1000 {
        rock.insert((x, lowest));
    }
    let mut count = 0;
    loop {
        let mut sand: (u16, u16) = (500, 0);
        loop {
            let new_pos = next_position(sand, rock.clone());
            if new_pos == sand {
                if sand == (500, 0) {
                    return count + 1;
                }
                rock.insert(new_pos);
                break;
            }
            sand = new_pos;
        }
        count += 1;
    }
}

fn next_position(sand: (u16, u16), rock: HashSet<(u16, u16)>) -> (u16, u16) {
    if !rock.contains(&(sand.0, sand.1 + 1)) {
        return (sand.0, sand.1 + 1);
    }
    if !rock.contains(&(sand.0 - 1, sand.1 + 1)) {
        return (sand.0 - 1, sand.1 + 1);
    }
    if !rock.contains(&(sand.0 + 1, sand.1 + 1)) {
        return (sand.0 + 1, sand.1 + 1);
    }
    return sand;
}

fn produce_rock(contents: String) -> (HashSet<(u16, u16)>, u16) {
    let mut rock: HashSet<(u16, u16)> = HashSet::new();
    let mut lowest = 0;
    let rows = contents.lines()
        .map(|line| line.split(" -> ")
            .map(|pair| pair.split(",")
                .map(|num| num.parse::<u16>().unwrap())
                .next_tuple::<(u16, u16)>()).tuple_windows());
    for row in rows {
        for (a, b) in row {
            let first = a.unwrap();
            let last = b.unwrap();
            if first.1 > lowest {
                lowest = first.1;
            }
            if last.1 > lowest {
                lowest = last.1;
            }
            if first.0 > last.0 {
                for x in last.0..=first.0 {
                    rock.insert((x, first.1));
                }
            } else if first.0 < last.0 {
                for x in first.0..=last.0 {
                    rock.insert((x, first.1));
                }
            } else if first.1 > last.1 {
                for y in last.1..=first.1 {
                    rock.insert((first.0, y));
                }
            } else if first.1 < last.1 {
                for y in first.1..=last.1 {
                    rock.insert((first.0, y));
                }
            }
        }
    }
    return (rock, lowest);
}


#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input14.txt".to_owned()), 901);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input14.txt".to_owned()), 24589);
    }
}
