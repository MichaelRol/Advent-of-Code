use std::{fs, num::ParseIntError, str::FromStr, vec::IntoIter};

use itertools::Itertools;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    
    let ranges = contents.lines()
        .map(|line| line.parse::<Sensor>().unwrap())
        .filter(|sensor| sensor.does_reach_y(2000000))
        .map(|sensor| sensor.find_range_at_y(2000000))
        .sorted_by(|a, b| a.0.cmp(&b.0));

    return merge_ranges(ranges).iter()
        .map(|(min, max)| max - min)
        .sum();
}

pub fn part2(path: String) -> i64 {    
    let contents = fs::read_to_string(path)
    .expect("Should have been able to read the file");

    let sorted_sensors = contents.lines()
        .map(|line| line.parse::<Sensor>().unwrap())
        .sorted_by(|a, b| a.x.cmp(&b.x))
        .collect::<Vec<Sensor>>();

    for y in 0..=4000000 {
        let ranges = sorted_sensors.iter()
            .filter(|sensor| sensor.does_reach_y(y))
            .map(|sensor| sensor.find_range_at_y(y))
            .map(|(a, b)| if a < 0 { (0, b)} else {(a, b)})
            .map(|(a, b)| if b > 4000000 { (a, 4000000)} else {(a, b)})
            .sorted_by(|a, b| a.0.cmp(&b.0));
        let merged = merge_ranges(ranges);
        if merged.len() > 1 {
            return (merged[0].1 + 1) as i64 * 4000000 + y as i64;
        }
    }
    return -1;
}

fn merge_ranges(ranges: IntoIter<(i32, i32)>) -> Vec<(i32, i32)> {
    let mut new_ranges = Vec::new();
    let mut min = 0;
    let mut max = 0;
    ranges.tuple_windows().for_each( |(ranges1, ranges2)| {
        if ranges1.0 < min {
            min = ranges1.0;
        }
        if ranges1.1 >= ranges2.0 - 1 || ranges1.1 < max {
            if max < ranges2.1 {
                max = ranges2.1
            }
            if max < ranges1.1 {
                max = ranges1.1
            }
        } else {
            new_ranges.push((min, max));
            min = ranges2.0;
            max = ranges2.1;
        }
    });
    new_ranges.push((min, max));
    return new_ranges;
}

#[derive(Clone)]
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
        if self.y == y {
            return true;
        }
        return (self.y > y && self.y - dist < y) || (self.y < y && self.y + dist > y);
    }

    fn find_range_at_y(&self, y: i32, ) -> (i32, i32) {
        let dist_from_beacon = self.distance_from_beacon();
        let dist_from_y = (self.y - y).abs();
        let range_left = self.x - (dist_from_beacon - dist_from_y);
        let range_right = self.x + (dist_from_beacon - dist_from_y);
        return (range_left, range_right);
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
        assert_eq!(part1("src/inputs/input15.txt".to_owned()), 4424278);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input15.txt".to_owned()), 10382630753392);
    }
}
