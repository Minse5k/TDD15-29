package com.back;

public class Calc {
    public static int run(String expression) {
        String normalized = expression
                .replace("-(", "-1 * (")
                .replace("(", "( ")
                .replace(")", " )")
                .trim();
        String[] parts = normalized.split("\\s+");

        return calc(parts, 0, parts.length-1);
    }

    private static int calc(String[] parts, int start, int end) {
        if(start==end) {
            return Integer.parseInt(parts[start]);
        }

        int depth = 0;
        for(int i=end; i>start; i--) {
            switch (parts[i]) {
                case ")" -> depth++;
                case "(" -> depth--;
                case "+" -> {
                    if(depth==0) return calc(parts, start, i-1) + calc(parts, i+1, end);
                }
                case "-" -> {
                    if(depth==0) return calc(parts, start, i-1) - calc(parts, i+1, end);
                }
            }
        }

        depth = 0;
        for(int i=end; i>start; i--) {
            switch (parts[i]) {
                case ")" -> depth++;
                case "(" -> depth--;
                case "*" -> {
                    if(depth==0) return calc(parts, start, i-1) * calc(parts, i+1, end);
                }
            }
        }

        // 괄호 밖에 연산자가 없으면 전체가 괄호로 감싸진 식
        return calc(parts, start+1, end-1);
    }
}
