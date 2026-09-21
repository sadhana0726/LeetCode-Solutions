import java.util.*;

class Solution {
    public final long MOD = 1000000007;
    public int numFactoredBinaryTrees(int[] arr) {
        Arrays.sort(arr);
        long[] dp = new long[arr.length];
        HashMap<Integer, Long> map = new HashMap<>();
        for(int i=0;i<arr.length;i++){
            dp[i]=1;
            for(int j=0;j<arr.length;j++){
                if(arr[i] % arr[j] == 0){
                    int right = arr[i] / arr[j];

                    if(map.containsKey(right)){
                        dp[i] = dp[i] + dp[j] * map.get(right);
                        dp[i] %= MOD;
                    }
                }
            }
            map.put(arr[i], dp[i]);
        }
            long ans = 0;
            for(int i=0; i<arr.length;i++){
                ans = ans+dp[i];
                ans %=MOD;
            }
            return (int) ans;
        
    }
}