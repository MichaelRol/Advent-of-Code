use std::fs;

pub fn part1(path: String) -> i32 {

    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let result = contents.lines().map(|line| calc_score1(line.to_string())).sum::<i32>();
    return result;
}

fn calc_score1(line: String) -> i32 {
    let moves: Vec<&str> = line.split(" ").collect();
    let op = to_num(moves[0]);
    let me = to_num(moves[1]);
    if op == me {
        return me + 4;
    } else if (me == 1 && op == 0) || (me == 2 && op == 1) || (me == 0 && op == 2) {
        return me + 7;
    }
    return me + 1;
}

pub fn part2(path: String) -> i32 {

    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let result = contents.lines().map(|line| calc_score2(line.to_string())).sum::<i32>();
    return result;

}

fn calc_score2(line: String) -> i32 {
    let moves: Vec<&str> = line.split(" ").collect();
    let op = to_num(moves[0]);
    let me = to_num(moves[1]);
    match me {
        0 => return ((op + 2) % 3) + 1,
        1 => return (op + 1) + 3,
        _ => return ((op + 1) % 3) + 7,
    }
}

fn to_num(letter: &str) -> i32 {
    match letter {
        "X" => return 0,
        "Y" => return 1,
        "Z" => return 2,
        "A" => return 0,
        "B" => return 1,
        "C" => return 2,
        _ => panic!("Unknown move entered: {}", letter)
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