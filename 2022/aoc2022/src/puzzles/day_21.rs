use std::{fs, collections::HashMap, str::FromStr, num::ParseIntError, vec};

pub fn part1(path: String) -> i128 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");

    let mut monkeys = contents.lines()
        .map(|line| line.parse::<Node<String>>().unwrap())
        .map(|node| (node.id.clone(), node))
        .collect::<HashMap<String, Node<String>>>();

    while monkeys["root"].val.parse::<i128>().is_err() {
        let mut to_remove: Vec<String> = Vec::new();
        for (id, node) in monkeys.clone() {
            if !node.children.is_empty() {
                let child0_val = monkeys[&node.children[0]].val.parse::<i128>();
                let child1_val = monkeys[&node.children[1]].val.parse::<i128>();
                if child0_val.is_ok() && child1_val.is_ok() {
                    let this_monkey = monkeys.get_mut(&id).unwrap();
                    if node.val == "+" {
                        this_monkey.val = (child0_val.unwrap() + child1_val.unwrap()).to_string();
                    } else if node.val == "-" {
                        this_monkey.val = (child0_val.unwrap() - child1_val.unwrap()).to_string();
                    } else if node.val == "*" {
                        this_monkey.val = (child0_val.unwrap() * child1_val.unwrap()).to_string();
                    } else if node.val == "/" {
                        this_monkey.val = (child0_val.unwrap() / child1_val.unwrap()).to_string();
                    }
                    to_remove.push(node.children[0].clone());
                    to_remove.push(node.children[1].clone());
                    this_monkey.children = vec![];
                }
            }
        }
        for child in to_remove {
            monkeys.remove(&child);
        }
    }
    return monkeys["root"].val.parse::<i128>().unwrap();
}

pub fn part2(path: String) -> i128 {    
    let contents = fs::read_to_string(path)
    .expect("Should have been able to read the file");

    let mut monkeys = contents.lines()
        .map(|line| line.parse::<Node<String>>().unwrap())
        .map(|node| (node.id.clone(), node))
        .collect::<HashMap<String, Node<String>>>();

    monkeys.clone().iter().for_each(|(id, node)| {
        if !node.children.is_empty() {
            monkeys.get_mut(&node.children[0]).unwrap().parent = id.to_string();
            monkeys.get_mut(&node.children[1]).unwrap().parent = id.to_string();
        }
    });

    let start = &"humn".to_string();
    let monkeys_clone = monkeys.clone();
    let mut parent = &monkeys_clone[start].parent;
    let mut path = vec![parent];
    while monkeys.get_mut(parent).unwrap().parent != "root".to_string() {
        parent = &monkeys_clone[parent].parent;
        path.push(parent);
    }

    let target = monkeys_clone["root"].children.iter()
        .filter(|child| child != &parent)
        .next()
        .unwrap();
    let mut target_value = calc_value(monkeys, target);

    let monkeys_clone_2 = monkeys_clone.clone();
    for &step in path.iter().rev() {
        let child = monkeys_clone_2[step].children.iter()
            .filter(|child| !path.contains(&&child) && *child != &"humn".to_string())
            .next()
            .unwrap();
        let mut child_left = true;
        if *child == monkeys_clone_2[step].children[1] {
            child_left = false;
        }
        let child_value = calc_value(monkeys_clone.clone(), child);
        if  monkeys_clone[step].val == "+" {
            target_value = target_value - child_value;
        } else if monkeys_clone[step].val == "-" {
            if child_left {
                target_value = child_value - target_value;
            } else {
                target_value = target_value + child_value;
            }
        } else if monkeys_clone[step].val == "*" {
            target_value = target_value / child_value;
        } else if monkeys_clone[step].val == "/" {
            if child_left {
                target_value = target_value / child_value;
            } else {
                target_value = target_value * child_value;
            }
        }
    }

    return target_value;
}

fn calc_value(mut monkeys: HashMap<String, Node<String>>, target: &String) -> i128 {
    while monkeys[target].val.parse::<i128>().is_err() {
        for (id, node) in monkeys.clone() {
            if !node.children.is_empty() {
                let child0_val = monkeys[&node.children[0]].val.parse::<i128>();
                let child1_val = monkeys[&node.children[1]].val.parse::<i128>();
                if child0_val.is_ok() && child1_val.is_ok() {
                    let this_monkey = monkeys.get_mut(&id).unwrap();
                    if node.val == "+" {
                        this_monkey.val = (child0_val.unwrap() + child1_val.unwrap()).to_string();
                    } else if node.val == "-" {
                        this_monkey.val = (child0_val.unwrap() - child1_val.unwrap()).to_string();
                    } else if node.val == "*" {
                        this_monkey.val = (child0_val.unwrap() * child1_val.unwrap()).to_string();
                    } else if node.val == "/" {
                        this_monkey.val = (child0_val.unwrap() / child1_val.unwrap()).to_string();
                    }
                    this_monkey.children = vec![];
                }
            }
        }
    }
    return monkeys[target].val.parse::<i128>().unwrap();
}

#[derive(Debug, Clone)]
struct Node<T>
where
    T: PartialEq,
{
    id: String,
    val: T,
    children: Vec<String>,
    parent: String,
}

impl FromStr for Node<String> {
    type Err = ParseIntError;
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let mut split = s.split(": ");
        let id = split.next().unwrap().to_string();
        let the_rest = split.next().unwrap();
        if the_rest.contains("+") || the_rest.contains("*") || the_rest.contains("/") || the_rest.contains("-") {
            let mut eq = the_rest.split(" ");
            let child1 = eq.next().unwrap().to_string();
            let op = eq.next().unwrap();
            let child2 = eq.next().unwrap().to_string();
            return Ok ( Node {
                id,
                val: op.to_string(),
                children: vec![child1, child2],
                parent: "".to_string(),
            })
        }
        return Ok( Node { 
            id,
            val: the_rest.to_string(),
            children: vec![],
            parent: "".to_string(),
        });
    }
}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input21.txt".to_owned()), 66174565793494);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input21.txt".to_owned()), 3327575724809);
    }
}
