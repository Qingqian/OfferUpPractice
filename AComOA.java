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
				if(j == list.size() - 1) {
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
			if(minLength > end - start) {
				minLength = Math.min(minLength, end - start);
				result.add(0, start);
				result.add(1, end);
			}
			String previous = availableTagList.get(start++);
			if(map.containsKey(previous)) {
				map.put(previous, map.get(previous) - 1);
				if(map.get(previous) == 0) {
					map.remove(previous);
				}
			}
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
// union find - item association
private class UnionFind{
	private Map<String, String> parent;
	private int[] count;
	private String rootWithMaxCount;
	private int maxCount;

	public UnionFind(String[][] items) {
		this.parent = new HashMap<String, String>();
		for(String[] item : items) {
			String first = item[0];
			String second = item[1];
			map.put(first, first);
			map.put(second, second);
		}
		this.count = new int[map.size()];
		Arrays.fill(count, 1);
		rootWithMaxCount = null;
		maxCount = 1;
	}

	public String find(String str) {
		if(map.get(str).equals(str)) {
			return str;
		}
		String root = find(map.get(str));
		map.put(str, root);
		return map.get(str);
	}

	public void union(String str1, String str2) {
		String root1 = find(str1);
		String root2 = find(str2);
		if(root1.equals(root2)) {
			return;
		}
		map.put(root2, root1);
		count[root1] += count[root2];
		if(maxCount < count[root1]) {
			maxCount = count[root1];
			rootWithMaxCount = root1;
		}
	}

	public List<String> getWordsWithLargestUnion() {
		List<String> result = new ArrayList<String>();
		for(String key : parent.keyset()) {
			String root = find(key);
			if(root.equals(rootWithMaxCount)) {
				result.add(key);
			}
		}
		return result;
	}
}
public List<String> itemAssociation(String[][] items) {
	UnionFind uf = new UnionFind(items);
	for(String[] item : items) {
		uf.union(item[0], item[1]);
	}
	return uf.getWordsWithLargestUnion();
}

public int throwBaseBall(String[] score) {
	if(score == null || score.length == 0) {
		return 0;
	}
	Stack<Integer> stack = new Stack<Integer>();
	int result = 0;

	for(int i = 0; i < score.length; i++) {
		String current = score[i];
		int value = 0;
		if(!stack.isEmpty() && current.equals("X")) {
			value = stack.peek() * 2;
			result += value;
			stack.push(value);
		} else if(!stack.isEmpty() && current.equals("Z")) {
			value = stack.pop();
			result -= value;
		} else if(current.equals("+")) {
			if(stack.isEmpty()) {
				value = 0;
			} else {
				int first = stack.pop();
				if(stack.isEmpty()) {
					value = first;
				} else {
					value = first + stack.peek();
				}
				stack.push(first);
			}
			result += value;
			stack.push(value);
		} else {
			value = Integer.parseInt(current);
			result += value;
			stack.push(value);
		}
	}
	return result;
}

private class TreeNode {
	public int val;
	public TreeNode left;
	public TreeNode right;
	public TreeNode(int value) {
		this.val = value;
		this.left = this.right = null;
	}
}

public int distance(int[] input, int number1, int number2) {
	if(input == null || inpput.length < 2) {
		return -1;
	}
	Arrays.sort(input);
	TreeNode root = buildTree(input, 0, input.length - 1);
	return findDistance(root, number1, number2);
}

private TreeNode buildTree(int[] input, int start, int end) {
	if(start > end) {
		return null;
	}
	int middle = start + (end - start) / 2;
	TreeNode current = new TreeNode(input[middle]);
	current.left = buildTree(input, start, middle - 1);
	current.right = buildTree(intput, middle + 1, end);
	return current;
}

private int findDistance(TreeNode root, int number1, int number2) {

	int firstLevel = getDepthFromRoot(root, number1, 0);
	int secondLevel = getDepthFromRoot(root, number2, 0);
	// one of the number doesn't exist in the bst
	if(firstLevel == 0 || secondLevel == 0) {
		return -1;
	}
	TreeNode lcaNode = getLCABST(root, number1, number2);
	if(lcaNode == null) {
		return -1;
	}
	int lcaLevel = getDepthFromRoot(root, lcaNode.val, 0);
	return firstLevel + secondLevel - 2 * lcaLevel;
}

private int getDepthFromRoot(TreeNode root, int number, int level) {
	if(root == null) {
		return 0;
	}
	if(root.val == number) {
		return level;
	}
	if(root.val > number) {
		return getDepthFromRoot(root.left, number, level + 1);
	} else {
		return getDepthFromRoot(root.right, number, level + 1);
	}
}

private TreeNode getLCABST(TreeNode root, int number1, int number2) {
	if(root == null) {
		return null;
	}
	if(root.val > number1 && root.val > number2) {
		return getLCABST(root.left, number1, number2);
	}
	if(root.val < number1 && root.val < number2) {
		return getLCABST(root.right, number1, number2);
	}
	return root;
}

public List<String> mostCommonWord(String paragraph, List<String> banned) {
	List<String> result = new ArrayList<String>();
 	if(paragraph == null || paragraph.length() == 0) {
 		return result;
 	}
 	String[] words = paragraph.trim().toLowerCase().replaceAll("\\p{P}", " ").split("\\s+");
 	Set<String> excludeList = new HashSet(banned);
 	Map<String, Integer> map = new HashMap<String, Integer>();
 	int maxCount = 0;
 	for(String word : words) {
 		if(excludeList.contains(word) || !isValidWord(word)) {
 			continue;
 		}
 		if(!map.containsKey(word)) {
 			map.put(word, 0);
 		}
 		map.put(word, map.get(word) + 1);
 		if(maxCount < map.get(word)) {
 			maxCount = map.get(word);
 		}
 	}
 	for(String word : map.keySet()) {
 		if(map.get(word) == count) {
 			result.add(word);
 		}
 	}
 	return result;
}

private boolean isValidWord(String str) {
	for(char current : str.toCharArray()) {
		if(!Character.isDigit(current)) {
			return false;
		}
	}
	return true;
}


public List<String> reorderLogFile(int logFileSize, List<String> lines) {
	if(logFileSize == 0 || lines == null || lines.size() == 0) {
		return lines;
	}
	List<String> result = new ArrayList<String>();
	List<String> numberLines = new ArrayList<String>();
	PriorityQueue<String[]> wordPriorityQueue = new PriorityQueue<String[]>(logFileSize, new Comparator<String[]>(){
		public int compare(String[] line1, String[] line2) {
			int compareResult = line1[1].compareTo(line2[1]);
			if(compareResult != 0) {
				return compareResult;
			} else {
				return line1[0].compareTo(line2[0]);
			}
		}
	});
	for(String line : lines) {
		int firstSpaceIndex = line.indexOf(" ");
		String lineId = line.substring(0, firstSpaceIndex);
		String linewords = line.substring(firstSpaceIndex + 1);
		if(linewords.length() > 0 && Character.isDigit(linewords.charAt(0))) {
			numberLines.add(line);
		} else if(linewords.length() > 0 && Character.isLetter(linewords.charAt(0))) {
			wordPriorityQueue.offer(new String[] {lineId, linewords});
		}
	}
	while(!wordPriorityQueue.isEmpty()) {
		String[] word = wordPriorityQueue.poll();
		result.add(word[0] + " " + word[1]);
	}
	for(int i = 0; i < numberLines.size(); i++) {
		result.add(numberLines.get(i));
	}
	return result;
}