use std::{fs, collections::{HashSet, VecDeque}};

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let cubes = contents.lines()
        .map(|line| line.split(",")
            .map(|num| num.parse::<i8>().unwrap())
            .collect::<Vec<i8>>())
        .collect::<HashSet<Vec<i8>>>();

    return cubes.iter()
        .flat_map(|tri| find_faces(tri.to_vec()))
        .filter(|cube| !cubes.contains(cube))
        .count() as i32;      
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
    .expect("Should have been able to read the file");

    let cubes = contents.lines()
        .map(|line| line.split(",")
            .map(|num| num.parse::<i8>().unwrap())
            .collect::<Vec<i8>>())
        .collect::<HashSet<Vec<i8>>>();

    return find_outer_faces(cubes);
}

fn find_faces(pos: Vec<i8>) -> Vec<Vec<i8>> {
    return vec![vec![pos[0] + 1, pos[1], pos[2]], vec![pos[0] - 1, pos[1], pos[2]], vec![pos[0], pos[1] + 1, pos[2]], 
            vec![pos[0], pos[1] - 1, pos[2]], vec![pos[0], pos[1], pos[2] + 1], vec![pos[0], pos[1], pos[2] - 1]]
}

fn find_outer_faces(cubes: HashSet<Vec<i8>>) -> i32 {
    let max_x = cubes.iter().map(|cube| cube[0]).max().unwrap();
    let max_y = cubes.iter().map(|cube| cube[1]).max().unwrap();
    let max_z = cubes.iter().map(|cube| cube[2]).max().unwrap();
    let mut touched: HashSet<Vec<i8>> = HashSet::new();
    let mut queue: VecDeque<Vec<i8>> = VecDeque::new();
    let mut count = 0;
    queue.push_back(vec![-1, -1, -1]);
    while !queue.is_empty() {
        let faces = find_faces(queue.pop_front().unwrap());
        for face in faces {
            if cubes.contains(&face) {
                count += 1;
                continue;
            }
            if !touched.contains(&face) {
                if face[0] >= -1 && face[0] <= max_x + 1 && face[1] >= -1 && face[1] <= max_y + 1 && face[2] >= -1 && face[2] <= max_z + 1 {
                    touched.insert(face.clone());
                    queue.push_back(face);
                }
            }
        }
    }

    return count;
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input18.txt".to_owned()), 4332);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input18.txt".to_owned()), 2524);
    }
}
