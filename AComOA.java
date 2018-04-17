// warehouse
public Point[] kClosest(Point[] points, Point origin, int k) {
	if(points == null || points.length <= k) {
		return points;
	}
	int length = points.length;(
	PriorityQueue<Point> pq = new PriorityQueue<Point>(length, new Comparator<Point>(){
		public int compare(Point p1, Point, p2) {
			return getDistance(p1, origin) - getDistance(p2, origin);
		}
	});
	for(Point point : points) {
		pq.offer(point);
	}
	Point[] result = new Point[k];
	for(int i = 0; i < k; i++) {
		result[i] = pq.poll();
	}
	return result;
}

private int getDistance(Point p, Point origin) {
	return (p.x - origin.x) *(p.x - origin.x) - (p.y - origin.y) * (p.y - origin.y);
}

// golf event - start from 4 corner cells
private class Point {
	public int row;
	public int column;
	public int height;
	public Point(int row, int column, int height) {
		this.row = row;
		this.column = column;
		this.height = height;
	}
}

public int shortestDistance(int[][] matrix) {
	int row = matrix.length;
	int column = matrix[0].length;
	PriorityQueue<Point> pq = new PriorityQueue<Point>(row * column, new Comparator<Point>(){
		public int compare(Point p1, Point p2) {
			return p1.height - p2.height;
		}
	});

	for(int i = 0; i < row; i++) {
		for(int j = 0; j < column; j++) {
			if(matrix[i][j] > 1) {
				pq.offer(new Point(i, j, matrix[i][j]));
			}
		}
	}

	int result = 0;
	Queue<Point> queue = new LinkedList<Point>();
	queue.offer(new Point(0, 0, matrix[0][0]));
	queue.offer(new Point(0, column - 1, matrix[0][column - 1]));
	queue.offer(new Point(row - 1, 0, matrix[row - 1][0]));
	queue.offer(new Point(row - 1, column - 1, matrix[row - 1][column - 1]));

	while(!pq.isEmpty()) {
		Point target = pq.poll();
		int step = bfs(matrix, queue, target);
		if(step == -1) {
			return -1;
		} 
		result += step;
		queue.clear();
		Point lastPoint = new Point(target.x, target.y, matrix[target.x][target.y]);
		queue.offer(lastPoint);
	}
	return result;
}

private int[] deltax = {0, 0, -1, 1};
private int[] deltay = {1, -1, 0, 0};

private int bfs(int[][] matrix, Queue<Point> queue, Point target) {

	int row = matrix.length;
	int column = matrix[0].length;
	boolean[][] visited = new boolean[row][column];
	int step = 0;
	while(!queue.isEmpty()) {
		int size = queue.size();
		for(int i = 0; i < size; i++) {
			Point current = queue.poll();
			visited[current.x][current.y] = true;
			if(current.x == target.x && current.y == target.y) {
				matrix[current.x][current.y] = 1;
				return step;
			}
			if(matrix[current.x][current.y] == 0 || matrix[current.x][current.y] > 1) {
				continue;
			}
			for(int k = 0; k < 4; k++) {
				int neighbor_row = row + deltax[k];
				int neighbor_column = column + deltay[k];
				if(isValid(neighbor_row, neighbor_column, matrix, visited)) {
					queue.offer(new Point(neighbor_row, neighbor_column, matrix[neighbor_row][neighbor_column]));
				}
			}
		}
		step++;
	}
	return -1;
}

private boolean isValid(int row, int column, int[][] matrix, boolean[][] visited) {
	if(row < 0 || column < 0 || row >= matrix.length || column >= matrix[0].length || visited[row][column]) {
		return false;
	}
	return matrix[row][column] != 0;
}


public int buyFruit(List<List<String>> codeList, List<String> shoppingCart) {
	List<String> list = new ArrayList<String>();
	for(List<String> code : codeList) {
		for(String fruit : code) {
			list.add(fruit);
		}
	}
	for(int i = 0; i + list.size() <= shoppingCart.size(); i++) {
		for(int j = 0; j < list.size(); j++) {
			if(list.get(j).equals("anything")) {
				if(i == list.size() - 1) {
					return 1;
				}
				continue;
			}
			if(!list.get(j).equals(shoppingCart.get(i + j))) {
				break;
			}
			if(j == list.size() - 1) {
				return 1;
			}
		}
	}
	return 0;
}

// LC 763
public List<Integer> partitionLabels(String S) {
    if(S == null || S.length() == 0) {
        return new ArrayList<Integer>();
    }
    List<Integer> result = new ArrayList<Integer>();
    int[] buffer = new int[26];
    for(int i = 0; i < S.length(); i++) {
        buffer[S.charAt(i) - 'a'] = i;
    }
    int start = 0;
    int last = 0;
    for(int i = 0; i < S.length(); i++) {
        last = Math.max(last, buffer[S.charAt(i) - 'a']);
        if(last == i) {
            result.add(last - start + 1);
            start = last + 1;
        }
    }
    return result;
}

//product with lables
public List<Integer> getFullLables(List<String> targetList, List<String> availableTagList) {
	List<Integer> result = new ArrayList<Integer>();
	Set<String> dict = new HashSet<String>(targetList);
	int start = 0;
	int end = 0;
	Map<String, Integer> map = new HashMap<String, Integer>();
	int minLength = Integer.MAX_VALUE;

	while(end < availableTagList.size()) {
		String current = availableTagList.get(end++);
		if(dict.contains(current)) {
			if(!map.containsKey(current)) {
				map.put(current, 0);
			}
			map.put(current, map.get(current) + 1);
		}
		while(map.size() == dict.size()) {
			String previous = availableTagList.get(start++);
			if(map.containsKey(previous)) {
				map.put(previous, map.get(previous) - 1);
				if(map.get(previous) == 0) {
					map.remove(previous);
				}
			}
		}
		if(minLength > end - start) {
			minLength = Math.min(minLength, end - start);
			result.add(0, start);
			result.add(1, end);
		}
	}

	if(minLength == Integer.MAX_VALUE) {
		result.add(0);
		return result;
	}
	return result;
}

// get menu recommendation
public String[][] recommend(String[][] menus, String[][] persons) {

	Map<String, Set<String>> map = new HashMap<String, Set<String>>();
	for(String[] menu : menus) {
		String dishName = menu[0];
		String dishSource = menu[1];
		if(!map.containsKey(dishSource)) {
			map.put(dishSource, new HashSet<String>());
		}
		map.get(dishSource).add(dishName);
	}

	Map<String, List<String>> personMap = new HashMap<String, List<String>>();
	for(String[] person : persons) {
		String personName = person[0];
		String personDishSource = person[1];
		if(personDishSource.equals("*")) {
			List<String> items = new ArrayList<String>();
			for(Set<String> dishes : map.values()) {
				for(String dish : dishes) {
					item.add(dish);
				}
			}
			personMap.put(personName, items);
		}
		if(!map.containsKey(personDishSource)) {
			continue;
		}
		List<String> items = new ArrayList<String>(map.get(personDishSource));
		personMap.put(personName, items);
	}
	String[][] result = new String[personMap.size()][2];
	int index = 0;
	for(String personName : personMap.keySet()) {
		result[index][0] = personName;
		result[index][1] = personMap.get(personName).get(0);
		index++;
	}
	return result;
}

// word game
public List<String> wordGame(String str, int k) {
	List<String> result = new ArrayList<String>();
	int[] buffer = new int[26];
	int count = 0;

	for(int end = 0; end < str.length(); end++) {
		if(buffer[str.charAt(end) - 'a']++ == 0) {
			count++;
		}
		if(end >= k - 1) {
			int start = i - k + 1;
			if(count == k) {
				String item = str.substring(start, end + 1);
				if(!result.contains(item)) {
					result.add(item);
				}
			}
			if(--buffer[str.charAt(start)] == 0) {
				count--;
			}
		}
	}
	return result;

}
