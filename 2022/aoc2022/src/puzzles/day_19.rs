use std::{fs, collections::{HashMap, VecDeque}, str::FromStr, num::ParseIntError, slice::Iter};
use crate::puzzles::day_19::MachineType::*;
use regex::Regex;

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let blueprints = contents.lines().map(|line| line.parse::<Blueprint>().unwrap()).collect::<Vec<Blueprint>>();

    for blueprint in blueprints {
        let state: State = State {
            resources: HashMap::from([(MachineType::Ore, 0), (MachineType::Clay, 0), (MachineType::Obsidian, 0), (MachineType::Geode, 0)]),
            robots: HashMap::from([(MachineType::Ore, 1), (MachineType::Clay, 0), (MachineType::Obsidian, 0), (MachineType::Geode, 0)]),
            time: 0,
        };
        let mut queue: VecDeque<State> = VecDeque::new();
        queue.push_back(state);
        while !queue.is_empty() {
            let mut next_state = queue.pop_front().unwrap();
            next_state.robots.into_iter().for_each(|(k, v)| {
                next_state.resources.insert(k, v + next_state.resources.get(&k).unwrap());
            });
            next_state.time += 1;
            if next_state.time == 28 {
                return next_state.resources.get(&MachineType::Geode).unwrap() * blueprint.id as i32;
            }
            queue.push_back(State { resources: next_state.resources, robots: next_state.robots, time: next_state.time });
            if next_state.resources.get(&MachineType::Ore).unwrap() >= blueprint.costs.get(&MachineType::Ore).unwrap().get(&MachineType::Ore).unwrap() {

            }
            
        }



    }

    print!("hi");
    return 0;
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
    .expect("Should have been able to read the file");
    return 0;
}

#[derive(Clone)]
struct State {
    resources: HashMap<MachineType, i32>,
    robots: HashMap<MachineType, i32>,
    time: u8,
}

struct Blueprint {
    id: u8,
    costs: HashMap<MachineType, HashMap<MachineType, i32>>,
}

impl FromStr for Blueprint {
    type Err = ParseIntError;
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let re = Regex::new(r"^Blueprint (\d+): Each ore robot costs (\d+) ore. Each clay robot costs (\d+) ore. Each obsidian robot costs (\d+) ore and (\d+) clay. Each geode robot costs (\d+) ore and (\d+) obsidian.$").unwrap();
        let captures = re.captures(s).unwrap();
        let mut costs: HashMap<MachineType, HashMap<MachineType, i32>> = HashMap::new();
        costs.insert(
            MachineType::Ore, 
            HashMap::from([
                (MachineType::Ore, captures.get(2).unwrap().as_str().parse::<i32>().unwrap())]
            ));
        costs.insert(
            MachineType::Clay, 
            HashMap::from([
                (MachineType::Ore, captures.get(3).unwrap().as_str().parse::<i32>().unwrap())]
            ));
        costs.insert(
            MachineType::Obsidian, 
            HashMap::from([
                (MachineType::Ore, captures.get(4).unwrap().as_str().parse::<i32>().unwrap()),
                (MachineType::Clay, captures.get(5).unwrap().as_str().parse::<i32>().unwrap())
            ]));
        costs.insert(
            MachineType::Geode, 
            HashMap::from([
                (MachineType::Ore, captures.get(6).unwrap().as_str().parse::<i32>().unwrap()),
                (MachineType::Obsidian, captures.get(7).unwrap().as_str().parse::<i32>().unwrap())
            ]));
        return Ok( Blueprint { 
            id: captures.get(1).unwrap().as_str().parse::<u8>().unwrap(),
            costs,
        });
    }
}

#[derive(Eq, PartialEq, Hash, Clone, Copy)]
enum MachineType {
    Ore,
    Clay,
    Obsidian,
    Geode,
}
impl MachineType {
    pub fn iterator() -> Iter<'static, MachineType> {
        static MACHINES: [MachineType; 4] = [Ore, Clay, Obsidian, Geode];
        return MACHINES.iter();
    }
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
