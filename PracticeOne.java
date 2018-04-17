public List<List<Integer>> buySoda(int[] size, int total) {
	List<List<Integer>> result = new ArrayList<List<Integer>>();
	if(size == null || size.length == 0 || total <= 0) {
		return result;
	}
	Arrays.sort(size);
	dfs(0, size, total, new ArrayList<Integer>(),result);
	return result;
}

private void dfs(int start, int[] size, int total, List<Integer> path, List<List<Integer>> result) {

	if(total == 0) {
		result.add(new ArrayList<Integer>(path));
		return;
	}

	for(int i = start; i < size.length; i++) {
		if(total - size[i] >= 0) {
			path.add(size[i]);
			dfs(i, size, total - size[i], path, result);
			path.remove(path.size() - 1);
		}
	}
}

public int getIntersectionNumber(int[] nums1, int[] nums2) {
	if(nums1 == null || nums2 == null || nums1.length != nums2.length) {
		return 0;
	}
	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	int result = 0;
	for(int i = 0; i < nums2.length; i++) {
		map.put(nums2[i], i);
	}
	for(int i = 0; i < nums1.length; i++) {
		int sortedPosition = map.get(nums1[i]);
		for(int j = i + 1; j < nums1.length; j++) {
			if(sortedPosition > map.get(nums1[j])) {
				result++;
			}
		}
	}
	return result;
}

// all positive integers
public int subArraySum(int[] nums, int k) {
	if(nums == null || nums.length == 0) {
		return 0;
	}
	int result = 0;
	int start = 0;
	int sum = 0;
	for(int i = 0; i < nums.length; i++) {
		sum += nums[i];
		while(sum > k && start < i) {
			sum-=nums[start++];
		}
		if(sum == k) {
			result++;
		}
	}	
	return result;
}

