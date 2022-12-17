use std::{fs, num::ParseIntError, str::FromStr};

use itertools::Itertools;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    return contents.lines()
        .map(|line| line.parse::<Sensor>().unwrap())
        .filter(|sensor| sensor.does_reach_y(2000000))
            .flat_map(|sensor| sensor.find_xs_a_y(2000000))
        .unique()
        .count() as i32;
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
    .expect("Should have been able to read the file");

    return 0;
}

struct Sensor {
    x: i32,
    y: i32,
    beacon_x: i32,
    beacon_y: i32,
}

impl Sensor {

    fn distance_from_beacon(&self) -> i32 {
        return (self.y - self.beacon_y).abs() + (self.x - self.beacon_x).abs();
    }

    fn does_reach_y(&self, y: i32) -> bool {
        let dist = self.distance_from_beacon();
        return (self.y > y && self.y - dist < y) || (self.y < y && self.y + dist > y);
    }

    fn find_xs_a_y(&self, y: i32) -> Vec<i32> {
        let dist_from_beacon = self.distance_from_beacon();
        let dist_from_y = (self.y - y).abs();
        let mut xs: Vec<i32> = Vec::new();
        for x in self.x..=self.x + (dist_from_beacon - dist_from_y) {
            if self.beacon_x != x || self.beacon_y != y {
                xs.push(x);
            }
        }
        for x in self.x - (dist_from_beacon - dist_from_y)..self.x {
            if self.beacon_x != x || self.beacon_y != y {
                xs.push(x);
            }
        }
        return xs;
    }
}

impl FromStr for Sensor {
    type Err = ParseIntError;
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let input = s.replace("Sensor at x=", "");
        let mut ints = input
        .split(": closest beacon is at x=").
            flat_map(|text| text.split(", y=")
                .map(|num| num.parse::<i32>().unwrap()));
        return Ok( Sensor { 
            x: ints.next().unwrap(),
            y: ints.next().unwrap(),
            beacon_x: ints.next().unwrap(),
            beacon_y: ints.next().unwrap(),
        });
    }
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input15.txt".to_owned()), 0);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input15.txt".to_owned()), 0);
    }
}
