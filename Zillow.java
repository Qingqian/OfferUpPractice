public int getGraphValue(Map<Integer, Set<Integer>> graph, int node, Map<Integer, Integer> buffer) {

	if(buffer.containsKey(node)) {
		return buffer.get(node);
	}

	int result = 0;
	if(graph.get(node).size() == 0) {
		result = 1;
	}
	for(int neighbor : graph.get(node)) {
		result += getGraphValue(graph, neighbor);
	}
	buffer.put(node, result);
	return result;
}

private class QueueNode {
	public int index;
	public char letter;
	public QueueNode(int index, char letter) {
		this.index = index;
		this.letter = letter;
	}
}

public List<Integer> findFirstUniqueCharacterInStream(List<Character> input) {
	// use queue and hashtable
	Map<Character, Integer> map = new HashMap<Character, Integer>();
	Queue<QueueNode> queue = new LinkedList<QueueNode>();
	List<Integer> result = new ArrayList<Integer>();

	for(int i = 0; i < input.size(); i++) {
		char current = input.get(i);
		if(!map.containsKey(current)) {
			map.put(current, 0);
		}
		map.put(current, map.get(current)) + 1;
		while(!queue.isEmpty() && map.get(queue.peek().letter) > 1) {
			queue.poll();
		}
		queue.offer(new QueueNode(i, current));
		result.add(queue.peek().index);
	}
	return result;
}