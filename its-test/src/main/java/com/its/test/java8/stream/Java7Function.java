package com.its.test.java8.stream;

import java.util.ArrayList;
import java.util.List;

/**
 * description: java7
 * company: tzz
 * @author: tzz
 * date: 2019/11/20 16:06
 */
public class Java7Function {

    public static int getCountEmptyStringUsingJava7(List<String> strings) {
        int count = 0;
        for (String string : strings) {

            if (string.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public static int getCountLength3UsingJava7(List<String> strings) {
        int count = 0;
        for (String string : strings) {

            if (string.length() == 3) {
                count++;
            }
        }
        return count;
    }

    public static List<String> deleteEmptyStringsUsingJava7(List<String> strings) {
        List<String> filteredList = new ArrayList<String>();
        for (String string : strings) {

            if (!string.isEmpty()) {
                filteredList.add(string);
            }
        }
        return filteredList;
    }

    public static String getMergedStringUsingJava7(List<String> strings, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            if (!string.isEmpty()) {
                stringBuilder.append(string);
                stringBuilder.append(separator);
            }
        }
        String mergedString = stringBuilder.toString();
        return mergedString.substring(0, mergedString.length() - 2);
    }

    public static List<Integer> getSquares(List<Integer> numbers) {
        List<Integer> squaresList = new ArrayList<Integer>();
        for (Integer number : numbers) {
            Integer square = new Integer(number.intValue() * number.intValue());

            if (!squaresList.contains(square)) {
                squaresList.add(square);
            }
        }
        return squaresList;
    }

    public static int getMax(List<Integer> numbers) {
        int max = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            Integer number = numbers.get(i);
            if (number.intValue() > max) {
                max = number.intValue();
            }
        }
        return max;
    }

    public static int getMin(List<Integer> numbers) {
        int min = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            Integer number = numbers.get(i);

            if (number.intValue() < min) {
                min = number.intValue();
            }
        }
        return min;
    }

    public static int getSum(List<Integer> numbers) {
        int sum = (int)(numbers.get(0));
        for (int i = 1; i < numbers.size(); i++) {
            sum += (int)numbers.get(i);
        }
        return sum;
    }

    public static int getAverage(List<Integer> numbers) {
        return getSum(numbers) / numbers.size();
    }
}

 