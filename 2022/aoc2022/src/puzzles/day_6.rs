use std::fs;

use itertools::Itertools;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
        return find_all_unique(contents, 4);
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
    .expect("Should have been able to read the file");
    return find_all_unique(contents, 14);
}

// this can probably be improved with a more efficient all_unique() function
fn find_all_unique(input: String, num: usize) -> i32 {
    return (input.as_bytes().windows(num)
        .position(|w| w.iter().all_unique()).unwrap() + num) as i32;
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input6.txt".to_owned()), 1198);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input6.txt".to_owned()), 3120);
    }
}
