mod puzzles;
mod utils;

use std::env;
use std::time::Instant;

fn main() {
    let args: Vec<String> = env::args().collect();
    if args.len() == 1 {
        run_all();
    } else if (args.len()) == 3 {
        let day = args.get(1).unwrap();
        let part = args.get(2).unwrap();
        run(day, part);
    } else {
        print!("Invalid number of arguments. Must be 0 or 2 (day number and part number).\n")
    }
}

fn run(day: &str, part: &str) {
    let result: i32;
    let now = Instant::now();
    match day {
        "1" => {
            if part == "1" {
                result = puzzles::day_1::part1(get_path(day));
            } else if part == "2" {
                result = puzzles::day_1::part2(get_path(day));
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "2" => {
            if part == "1" {
                result = puzzles::day_2::part1(get_path(day));
            } else if part == "2" {
                result = puzzles::day_2::part2(get_path(day));
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "3" => {
            if part == "1" {
                result = puzzles::day_3::part1(get_path(day));
            } else if part == "2" {
                result = puzzles::day_3::part2(get_path(day));
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "4" => {
            if part == "1" {
                result = puzzles::day_4::part1(get_path(day));
            } else if part == "2" {
                result = puzzles::day_4::part2(get_path(day));
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        _ => {
            print!("Unknown day number: {day}\n");
            return;
        }
    }
    let elapsed = now.elapsed();
    print!("Day {day} Part {part}: {result}, Time elapsed: {elapsed:.2?}\n");
}

fn run_all() {
    let now = Instant::now();
    print!("Day 1 Part 1: {}\n", puzzles::day_1::part1(get_path("1")));
    print!("Day 1 Part 2: {}\n", puzzles::day_1::part2(get_path("1")));
    print!("Day 2 Part 1: {}\n", puzzles::day_2::part1(get_path("2")));
    print!("Day 2 Part 2: {}\n", puzzles::day_2::part2(get_path("2")));
    print!("Day 3 Part 1: {}\n", puzzles::day_3::part1(get_path("3")));
    print!("Day 3 Part 2: {}\n", puzzles::day_3::part2(get_path("3")));
    print!("Day 4 Part 1: {}\n", puzzles::day_4::part1(get_path("4")));
    print!("Day 4 Part 2: {}\n", puzzles::day_4::part2(get_path("4")));
    let elapsed = now.elapsed();
    print!("Total time: {:.2?}", elapsed);
}

fn get_path(day: &str) -> String {
    return "src/inputs/input".to_owned() + day.trim() + ".txt";
}
