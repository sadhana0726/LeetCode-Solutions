class Solution {
    private static class Node {
        int prod;
        int[] counts;

        Node(int k) {
            prod = 1;
            counts = new int[k];
        }
    }

    private Node[] tree;
    private int n;
    private int kVal;

    private Node merge(Node left, Node right) {
        Node res = new Node(kVal);
        res.prod = (int) (((long) left.prod * right.prod) % kVal);
        
        for (int r = 0; r < kVal; r++) {
            res.counts[r] = left.counts[r];
        }

        for (int r = 0; r < kVal; r++) {
            if (right.counts[r] > 0) {
                int newRem = (int) (((long) left.prod * r) % kVal);
                res.counts[newRem] += right.counts[r];
            }
        }
        return res;
    }

    private void build(int node, int start, int end, int[] nums) {
        if (start == end) {
            int rem = (int) (nums[start] % kVal);
            tree[node].prod = rem;
            tree[node].counts[rem] = 1;
            return;
        }
        int mid = start + (end - start) / 2;
        build(2 * node, start, mid, nums);
        build(2 * node + 1, mid + 1, end, nums);
        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    private void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            int rem = (int) (val % kVal);
            tree[node].prod = rem;
            for (int r = 0; r < kVal; r++) {
                tree[node].counts[r] = 0;
            }
            tree[node].counts[rem] = 1;
            return;
        }
        int mid = start + (end - start) / 2;
        if (idx <= mid) {
            update(2 * node, start, mid, idx, val);
        } else {
            update(2 * node + 1, mid + 1, end, idx, val);
        }
        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    private Node query(int node, int start, int end, int l, int r) {
        if (l <= start && end <= r) {
            return tree[node];
        }
        int mid = start + (end - start) / 2;
        if (r <= mid) {
            return query(2 * node, start, mid, l, r);
        }
        if (l > mid) {
            return query(2 * node + 1, mid + 1, end, l, r);
        }
        Node left = query(2 * node, start, mid, l, r);
        Node right = query(2 * node + 1, mid + 1, end, l, r);
        return merge(left, right);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.kVal = k;
        
        tree = new Node[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            tree[i] = new Node(k);
        }

        build(1, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int idx = queries[q][0];
            int val = queries[q][1];
            int start = queries[q][2];
            int targetX = queries[q][3];

            update(1, 0, n - 1, idx, val);

            Node res = query(1, 0, n - 1, start, n - 1);
            ans[q] = res.counts[targetX];
        }

        return ans;
    }
}