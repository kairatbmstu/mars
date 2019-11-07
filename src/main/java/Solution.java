

import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    static long result = 18;

    static Stack<Character> stack = new Stack<Character>();

    static List<Long> list = new ArrayList<Long>();

    static int operationsNum = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //String input = "8880=(80) (800) (8) 10";
        String input = "1=1 2 3 4 5 6 7 8 9 10 (11 ((12 13)) 14)";
        //String input = "3628800=1 2 3 4 5 6 7 8 9 10";
        //String input = "30 =(((3 3 5)))";
        //String input = "178=55 100 2    (1)   (1)    (   1    (   1   1  1  1   )    ) (    (   13   1   1    ) 1   ) 1 (4)";
        //String input = in.nextLine();
        String splitted[] = input.split("=");
        result = Long.parseLong(splitted[0].trim());
        String expression = splitted[1].trim();
        Pattern pattern = Pattern.compile("([0-9]+)");
        expression = expression.replaceAll("\\)\\s+\\)","\\)\\)");
        expression = expression.replaceAll("\\(\\s+\\(","\\(\\(");
        expression = expression.replaceAll("(\\d)(\\s+)(\\))","$1$3");
        expression = expression.replaceAll("(\\()(\\s+)(\\d)","$1$3");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            String item = matcher.group(1);
            if (item.contains(" ")) {
                operationsNum++;
            } else {
                long val = Integer.parseInt(item);
                list.add(val);
            }
        }

        long r = rootRecursive(list, 0);

        if (r == -1) {
            System.out.println(r);
            return;
        }

        input = result+"=";

        List<String> rrrr = new ArrayList();
        pattern = Pattern.compile("([\\(\\)0-9]+)");
        matcher = pattern.matcher(expression);
        while (matcher.find()) {
            String item = matcher.group(1);
            rrrr.add(item);
        }

        Iterator<String> it1 = rrrr.iterator();
        Iterator<Character> it2 = stack.iterator();

        StringBuilder sb = new StringBuilder();
        sb.append(input);
        while (it1.hasNext()) {
            String digit = it1.next();
            sb.append(digit);
            if (it2.hasNext()) {
                Character cha = it2.next();
                sb.append(cha);
            }
        }

        System.out.println(sb.toString());
    }

    public static int rootRecursive(List<Long> list, int index) {
        if (list.size() == 1){
            Long a = list.get(0);
            if (result == a.intValue()){
                return 1;
            } else {
                return -1;
            }
        }
        int r = recursive(list, index, list.get(0));
        if (r == -1) {
        }
        if (r == 1) {
            return 1;
        }

        return -1;
    }


    public static int recursive(List<Long> list, int index, long sum) {
        Long a = list.get(index);
        Long b = list.get(index + 1);

        long c1 = sum + b;
        long c2 = sum - b;
        long c3 = sum * b;

        long delta1 = (result - c1);
        long delta2 = (result - c2);
        long delta3 = (result - c3);

        if ((index + 2) == list.size()) {
            if (delta1 == 0 || delta2 == 0 || delta3 == 0) {
                if (delta3 == 0) {
                    stack.push('*');
                }
                if (delta1 == 0) {
                    stack.push('+');
                }
                if (delta2 == 0) {
                    stack.push('-');
                }
                return 1;
            } else {
                return -1;
            }
        }

        long[] mindis = new long[]{delta1, delta2, delta3};
        Arrays.sort(mindis);
        for (long min : mindis) {
            if (min == delta1) {
                stack.push('+');
                int r = recursive(list, index + 1, c1);
                if (r == -1) {
                    stack.pop();
                }
                if (r == 1) {
                    return 1;
                }
            }
            if (min == delta2) {
                stack.push('-');
                int r = recursive(list, index + 1, c2);
                if (r == -1) {
                    stack.pop();
                }
                if (r == 1) {
                    return 1;
                }
            }
            if (min == delta3) {
                stack.push('*');
                int r = recursive(list, index + 1, c3);
                if (r == -1) {
                    stack.pop();
                }
                if (r == 1) {
                    return 1;
                }
            }
        }

        return -1;
    }


}



