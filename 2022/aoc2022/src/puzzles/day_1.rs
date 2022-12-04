use std::fs;

pub fn part1(path: String) -> i32 {

    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let groups = contents.split("\n\n");
    let mut max = 0;
    for group in groups {
        let values = group.split("\n");
        let mut sum = 0;
        for value in values {
            match value.parse::<i32>() {
                Ok(n) => sum += n,
                Err(_) => continue,
              }
        }
        if sum > max {
            max = sum;
        }

    }
    return max;
}

pub fn part2(path: String) -> i32 {

    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let groups = contents.split("\n\n");
    let mut max0 = 0;
    let mut max1 = 0;
    let mut max2 = 0;
    for group in groups {
        let values = group.split("\n");
        let mut sum = 0;
        for value in values {
            match value.parse::<i32>() {
                Ok(n) => sum += n,
                Err(_) => continue,
              }
        }
        if sum > max0 {
            max2 = max1;
            max1 = max0;
            max0 = sum;
        } else if sum > max1 {
            max2 = max1;
            max1 = sum;
        } else if sum > max2 {
            max2 = sum;
        }
    }
    return max0 + max1 + max2;
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input1.txt".to_owned()), 67622);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input1.txt".to_owned()), 201491);
    }
}