# OfferUpPractice
PhonePractice

1. Buy soda

List<List<Integer>> buySoda(int[] sizes, int n);
大概是说超市有不同pack的汽水，比如散装，6瓶装，12瓶装之类的。假设要买n瓶，返回所有的买法。
Sample input: sizes=[1,6,12], n=12
Sampel output: [ [1,1,...1], [1,1,1,1,1,1,6], [6,6], [12] ]

基本上就是leetcode的combination sum II套了个实际的场景. 我先用dfs写了。Follow up问了complexity，然后问能不能加快，我加了一些pruning。然后问能不能polynomial time....我理解的面试官的意思：dfs的解法worst case相当于一个depth=n的tree的一部分，那么complexity在 (sizes.length)^n 级别，因为n可以非常大，所以很容易指数爆炸，但是一般来说pack sizes只有那么几种，所以她想让我写 n^(sizes.length) 级别的解法。我表示迷茫。如果没过就是这里followup跪了。


2. 一个没有排序的数组比如31254，和他自己的有序序列12345按照如下方式写成两排：然后每两个相同的数字画线，这样有几个交点。
3 1 2 5 4
1 2 3 4 5
