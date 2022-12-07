use std::fs;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    
    let mut total: i32 = 0;
    let mut files: Vec<i32> = Vec::new();
    files.push(0);
    for line in contents.lines() {
        if line.starts_with("$ cd ..") {
            let num = files.pop().unwrap();
            if num <= 100000 {
                total += num;
            }
            *files.last_mut().unwrap() += num;
        } else if line.starts_with("$ cd") {
            files.push(0);
        } else if line.starts_with("$ ls") || line.starts_with("dir") {
            continue;
        } else {
            *files.last_mut().unwrap() += line.split(" ").next().unwrap().parse::<i32>().unwrap();
        }
    }
    return total;
}

pub fn part2(path: String) -> i32 {   
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let mut files: Vec<i32> = Vec::new();
    let mut dirs: Vec<i32> = Vec::new();
    files.push(0);
    for line in contents.lines() {
        if line.starts_with("$ cd ..") {
            let num = files.pop().unwrap();
            dirs.push(num);
            *files.last_mut().unwrap() += num;
        } else if line.starts_with("$ cd") {
            files.push(0);
        } else if line.starts_with("$ ls") || line.starts_with("dir") {
            continue;
        } else {
            *files.last_mut().unwrap() += line.split(" ").next().unwrap().parse::<i32>().unwrap();
        }
    }
    let target = files.iter().sum::<i32>() - 40000000;
    return *dirs.iter().filter(|num| num > &&target).min().unwrap();
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input7.txt".to_owned()), 1391690);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input7.txt".to_owned()), 5469168);
    }
}
