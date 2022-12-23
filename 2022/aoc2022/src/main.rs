mod puzzles;

use std::env;
use std::time::{Instant, Duration};

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
    let result: String;
    let now: Instant;
    let elapsed: Duration;
    match day {
        "1" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_1::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                now = Instant::now();
                result = puzzles::day_1::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "2" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_2::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                now = Instant::now();
                result = puzzles::day_2::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "3" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_3::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                now = Instant::now();
                result = puzzles::day_3::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "4" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_4::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                now = Instant::now();
                result = puzzles::day_4::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "5" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_5::part1(get_path(day));
                elapsed = now.elapsed();
            } else if part == "2" {
                now = Instant::now();
                result = puzzles::day_5::part2(get_path(day));
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "6" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_6::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_6::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "7" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_7::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_7::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "8" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_8::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_8::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "9" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_9::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_9::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "10" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_10::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_10::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "11" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_11::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_11::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "12" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_12::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_12::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "13" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_13::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_13::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "14" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_14::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_14::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "15" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_15::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_15::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "18" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_18::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_18::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "20" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_20::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_20::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "21" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_21::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_21::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        "22" => {
            if part == "1" {
                now = Instant::now();
                result = puzzles::day_22::part1(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else if part == "2" {
                let now = Instant::now();
                result = puzzles::day_22::part2(get_path(day)).to_string();
                elapsed = now.elapsed();
            } else {
                print!("Part number must be 1 or 2, but was {part}.\n");
                return;
            }
        },
        _ => {
            print!("Unknown day number: {day}\n");
            return;
        },
    }
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
    print!("Day 5 Part 1: {}\n", puzzles::day_5::part1(get_path("5")));
    print!("Day 5 Part 2: {}\n", puzzles::day_5::part2(get_path("5")));
    print!("Day 6 Part 1: {}\n", puzzles::day_6::part1(get_path("6")));
    print!("Day 6 Part 2: {}\n", puzzles::day_6::part2(get_path("6")));
    // print!("Day 7 Part 1: {}\n", puzzles::day_7::part1(get_path("7")));
    // print!("Day 7 Part 2: {}\n", puzzles::day_7::part2(get_path("7")));
    // print!("Day 8 Part 1: {}\n", puzzles::day_8::part1(get_path("8")));
    // print!("Day 8 Part 2: {}\n", puzzles::day_8::part2(get_path("8")));
    // print!("Day 9 Part 1: {}\n", puzzles::day_9::part1(get_path("9")));
    // print!("Day 9 Part 2: {}\n", puzzles::day_9::part2(get_path("9")));
    // print!("Day 10 Part 1: {}\n", puzzles::day_10::part1(get_path("10")));
    // print!("Day 10 Part 2: {}\n", puzzles::day_10::part2(get_path("10")));
    // print!("Day 11 Part 1: {}\n", puzzles::day_11::part1(get_path("11")));
    // print!("Day 11 Part 2: {}\n", puzzles::day_11::part2(get_path("11")));
    // print!("Day 12 Part 1: {}\n", puzzles::day_12::part1(get_path("12")));
    // print!("Day 12 Part 2: {}\n", puzzles::day_12::part2(get_path("12")));
    // print!("Day 13 Part 1: {}\n", puzzles::day_13::part1(get_path("13")));
    // print!("Day 13 Part 2: {}\n", puzzles::day_13::part2(get_path("13")));
    // print!("Day 14 Part 1: {}\n", puzzles::day_14::part1(get_path("14")));
    // print!("Day 14 Part 2: {}\n", puzzles::day_14::part2(get_path("14")));
    // print!("Day 15 Part 1: {}\n", puzzles::day_15::part1(get_path("15")));
    // print!("Day 15 Part 2: {}\n", puzzles::day_15::part2(get_path("15")));
    // print!("Day 18 Part 1: {}\n", puzzles::day_18::part1(get_path("18")));
    // print!("Day 18 Part 2: {}\n", puzzles::day_18::part2(get_path("18")));
    // print!("Day 20 Part 1: {}\n", puzzles::day_20::part1(get_path("20")));
    // print!("Day 20 Part 2: {}\n", puzzles::day_20::part2(get_path("20")));
    // print!("Day 21 Part 1: {}\n", puzzles::day_21::part1(get_path("21")));
    // print!("Day 21 Part 2: {}\n", puzzles::day_21::part2(get_path("21")));
    print!("Day 22 Part 1: {}\n", puzzles::day_22::part1(get_path("22")));
    print!("Day 22 Part 2: {}\n", puzzles::day_22::part2(get_path("22")));
    let elapsed = now.elapsed();
    print!("Total time: {:.2?}", elapsed);
}

fn get_path(day: &str) -> String {
    return "src/inputs/input".to_owned() + day.trim() + ".txt";
}
