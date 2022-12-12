use std::{fs, cmp::Ordering, collections::{HashMap, HashSet, BinaryHeap}, hash::Hash};

pub fn part1(path: String) -> i32 {
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let vertices = store_vertices(&contents);
    let y_size = contents.lines().count() - 1;
    let x_size = contents.lines().next().unwrap().chars().count() - 1;
    let mut map = HashMap::<Vertex, Vec<Vertex>>::new();
    let mut start = Vertex { x: 9999, y: 9999, height: '0' };
    let mut dest = (0, 0);
    for ((x,y), mut vertex) in vertices.clone() {
        if vertex.height == 'S' {
            vertex.height = 'a';
            start = vertex;
        }
        if vertex.height == 'E' {
            dest = (vertex.x, vertex.y);
        }
        let mut edges: Vec<Vertex> = Vec::new();
        if x > 0 {
            if are_neighbors(vertex, *vertices.get(&(x-1, y)).unwrap()) {
                edges.push(*vertices.get(&(x-1, y)).unwrap());
            }
        }
        if y > 0 {
            if are_neighbors(vertex, *vertices.get(&(x, y-1)).unwrap()) {
                edges.push(*vertices.get(&(x, y-1)).unwrap());
            }
        }
        if x < x_size {
            if are_neighbors(vertex, *vertices.get(&(x+1, y)).unwrap()) {
                edges.push(*vertices.get(&(x+1, y)).unwrap());
            }
        }
        if y < y_size {
            if are_neighbors(vertex, *vertices.get(&(x, y+1)).unwrap()) {
                edges.push(*vertices.get(&(x, y+1)).unwrap());
            }
        }
        map.insert(vertex, edges);
    }
    let dists = dijkstra(start, map);
    return *dists.iter()
        .filter(|(vertex, _)| vertex.x == dest.0 && vertex.y == dest.1)
        .next()
        .unwrap().1 as i32;
}

pub fn part2(path: String) -> i32 {    
    let contents = fs::read_to_string(path)
        .expect("Should have been able to read the file");
    let vertices = store_vertices(&contents);
    let y_size = contents.lines().count() - 1;
    let x_size = contents.lines().next().unwrap().chars().count() - 1;
    let mut map = HashMap::<Vertex, Vec<Vertex>>::new();
    let mut start = Vertex { x: 9999, y: 9999, height: '0' };
    for ((x,y), mut vertex) in vertices.clone() {
        if vertex.height == 'E' {
            vertex.height = 'z';
            start = vertex;
        }
        let mut edges: Vec<Vertex> = Vec::new();
        if x > 0 {
            if are_neighbors(*vertices.get(&(x-1, y)).unwrap(), vertex) {
                edges.push(*vertices.get(&(x-1, y)).unwrap());
            }
        }
        if y > 0 {
            if are_neighbors(*vertices.get(&(x, y-1)).unwrap(), vertex) {
                edges.push(*vertices.get(&(x, y-1)).unwrap());
            }
        }
        if x < x_size {
            if are_neighbors(*vertices.get(&(x+1, y)).unwrap(), vertex) {
                edges.push(*vertices.get(&(x+1, y)).unwrap());
            }
        }
        if y < y_size {
            if are_neighbors(*vertices.get(&(x, y+1)).unwrap(), vertex) {
                edges.push(*vertices.get(&(x, y+1)).unwrap());
            }
        }
        map.insert(vertex, edges);
    }
    let dists = dijkstra(start, map);
    return *dists.iter()
        .filter(|(vertex, _)| vertex.height == 'a')
        .map(|x| x.1)
        .min()
        .unwrap() as i32;
}

fn store_vertices(contents: &str) -> HashMap<(usize, usize), Vertex> {
    let mut vertices: HashMap<(usize, usize), Vertex> = HashMap::new();
    contents.lines()
        .enumerate()
        .for_each(|(y, line)| line.chars()
            .enumerate()
            .for_each(|(x, height)| 
            { 
                vertices.insert((x, y), Vertex::new(x, y, height)); 
            }));
    return vertices;
}

fn are_neighbors(a: Vertex, b: Vertex) -> bool {
    if b.height == 'E' {
        return a.height == 'y' || a.height == 'z';
    }
    return b.height.to_digit(36).unwrap() as i32 - a.height.to_digit(36).unwrap() as i32 <= 1;
}

fn dijkstra(
    start: Vertex,
    adjacency_list: HashMap<Vertex, Vec<Vertex>>,
) -> HashMap<Vertex, usize> {

    let mut distances = HashMap::new();
    let mut visited = HashSet::new();
    let mut to_visit = BinaryHeap::new();

    distances.insert(start, 0);
    to_visit.push(Visit {
        vertex: start,
        distance: 0,
    });

    while let Some(Visit { vertex, distance }) = to_visit.pop() {
        if !visited.insert(vertex) {
            // Already visited this node
            continue;
        }

        if let Some(neighbors) = adjacency_list.get(&vertex) {
            for neighbor in neighbors {
                let new_distance = distance + 1;
                let is_shorter = distances
                    .get(&neighbor)
                    .map_or(true, |&current| new_distance < current);

                if is_shorter {
                    distances.insert(*neighbor, new_distance);
                    to_visit.push(Visit {
                        vertex: *neighbor,
                        distance: new_distance,
                    });
                }
            }
        }
    }
    return distances;
}

#[derive(Debug, Copy, Clone, PartialEq, Eq, Hash)]
struct Vertex {
    x: usize,
    y: usize,
    height: char,
}

impl Vertex {
    fn new(x: usize, y: usize, height: char) -> Vertex {
        Vertex { x, y, height }
    }
}

#[derive(Debug)]
struct Visit<V> {
    vertex: V,
    distance: usize,
}

impl<V> Ord for Visit<V> {
    fn cmp(&self, other: &Self) -> Ordering {
        other.distance.cmp(&self.distance)
    }
}

impl<V> PartialOrd for Visit<V> {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl<V> PartialEq for Visit<V> {
    fn eq(&self, other: &Self) -> bool {
        self.distance.eq(&other.distance)
    }
}

impl<V> Eq for Visit<V> {}

#[cfg(test)]
mod test {
    use super::*;
    
    #[test]
    fn test_part1() {
        assert_eq!(part1("src/inputs/input12.txt".to_owned()), 447);
    }

    #[test]
    fn test_part2() {
        assert_eq!(part2("src/inputs/input12.txt".to_owned()), 446);
    }
}
