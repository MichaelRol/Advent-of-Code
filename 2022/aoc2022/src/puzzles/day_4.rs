use std::{fs, num::ParseIntError, str::FromStr};

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    return contents.lines()
        .map(|line| line.parse::<Pair>())
        .filter(|pair| pair.as_ref().unwrap().full_contains())
        .count() as i32;
}

pub fn part2(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    return contents.lines()
        .map(|line| line.parse::<Pair>())
        .filter(|pair| pair.as_ref().unwrap().any_overlap())
        .count() as i32;
}

struct Pair {
    a_min: i32,
    a_max: i32,
    b_min: i32,
    b_max: i32
}

impl Pair {
    fn full_contains(&self) -> bool {
        return (self.a_min >= self.b_min && self.a_max <= self.b_max) || (self.b_min >= self.a_min && self.b_max <= self.a_max);
    }

    fn any_overlap(&self) -> bool {
        return (self.a_max >= self.b_min && self.a_max <= self.b_max) || (self.a_min >= self.b_min && self.a_min <= self.b_max) || 
               (self.b_max >= self.a_min && self.b_max <= self.a_max) || (self.b_min >= self.a_min && self.b_min <= self.a_max);
    }
}

impl FromStr for Pair {
    type Err = ParseIntError;
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let mut pairs = s.split(",").flat_map(|s1| s1.split("-"));
        return Ok( Pair { 
            a_min: pairs.next().unwrap().parse()?, 
            a_max: pairs.next().unwrap().parse()?,
            b_min: pairs.next().unwrap().parse()?,
            b_max: pairs.next().unwrap().parse()?,
        });
    }
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input4.txt".to_owned()), 511);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input4.txt".to_owned()), 821);
    }
}