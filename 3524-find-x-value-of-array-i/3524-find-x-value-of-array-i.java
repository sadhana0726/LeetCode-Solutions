class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] result = new long[k];
        long[] dp = new long[k];

        for (int num : nums) {
            long[] nextDp = new long[k];
            int rem = num % k;

            nextDp[rem]++;

            for (int r = 0; r < k; r++) {
                if (dp[r] > 0) {
                    int nextRem = (int) ((1L * r * rem) % k);
                    nextDp[nextRem] += dp[r];
                }
            }

            for (int r = 0; r < k; r++) {
                result[r] += nextDp[r];
            }

            dp = nextDp;
        }

        return result;
    }
}