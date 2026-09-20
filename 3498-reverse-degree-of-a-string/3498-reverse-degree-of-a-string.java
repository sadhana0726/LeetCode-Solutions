class Solution {
    public int reverseDegree(String s) {
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int reversedAlphabetPos = 26 - (s.charAt(i) - 'a');
            int stringPos = i + 1;
            total += reversedAlphabetPos * stringPos;
        }
        return total;
    }
}