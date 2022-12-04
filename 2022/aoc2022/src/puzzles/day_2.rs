use std::fs;
use std::collections::HashMap;

pub fn part1(path: String) -> i32 {

    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let result = contents.lines().map(|line| calc_score1(line.to_string())).sum::<i32>();
    return result;
}

fn calc_score1(line: String) -> i32 {
    let moves_map: HashMap<&str, i32> = HashMap::from([
        ("X", 1),
        ("Y", 2),
        ("Z", 3),
        ("A", 1),
        ("B", 2),
        ("C", 3),
    ]);

    let moves: Vec<&str> = line.split(" ").collect();
    let op = moves_map.get(moves[0]).expect("Invalid input character");
    let me = moves_map.get(moves[1]).expect("Invalid input character");
    if op == me {
        return me + 3;
    } else if (*me == 2 && *op == 1) || (*me == 3 && *op == 2) || (*me == 1 && *op == 3) {
        return me + 6;
    }
    return *me;
}

pub fn part2(path: String) -> i32 {

    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let result = contents.lines().map(|line| calc_score2(line.to_string())).sum::<i32>();
    return result;

}

fn calc_score2(line: String) -> i32 {
    let moves_map: HashMap<&str, i32> = HashMap::from([
        ("X", 0),
        ("Y", 1),
        ("Z", 2),
        ("A", 0),
        ("B", 1),
        ("C", 2),
    ]);

    let moves: Vec<&str> = line.split(" ").collect();
    let op = moves_map.get(moves[0]).expect("Invalid input character");
    let me = moves_map.get(moves[1]).expect("Invalid input character");
    if *me == 0 {
        return ((op + 2) % 3) + 1;
    } else if *me == 1 {
        return (op + 1) + 3;
    } else {
        return ((op + 1) % 3) + 7;
    }
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input2.txt".to_owned()), 11841);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input2.txt".to_owned()), 13022);
    }
}