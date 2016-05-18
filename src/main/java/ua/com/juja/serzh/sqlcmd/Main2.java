package ua.com.juja.serzh.sqlcmd;

import java.util.*;

/**
 * Created by serzh on 17.05.16.
 */
public class Main2 {
    public static void main(String[] args) {
//        System.out.println(Arrays.toString(generatePrimeNumbers(5)));
        System.out.println(countSpecMult(3, 200));
    }


//    http://www.codewars.com/kata/special-multiples/train/java
    public static long countSpecMult(long n, long mxval) {
        long[] nums = generatePrimeNumbers(n);
        List<Long> list = new ArrayList<>();
        firstRow:
        for (long i = 2; i < mxval; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i % nums[j] != 0) {
                    continue firstRow;
                }
            }
            list.add(i);
        }
        return list.size();
    }

    static long[] generatePrimeNumbers(long num) {
        long limit = 0;
        List<Long> list = new ArrayList<>();
        for (long i = 2; limit < num; i++) {
            boolean isPrime = true;
            for (long j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                list.add(i);
                limit++;
            }
        }
        long[] result = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }


    public static long sumDigNthTerm(long initval, long[] patternl, int nthterm) {
        long resultTemp = initval;
        int count = 0;
        for (int i = 0; i < nthterm - 1; i++) {
            if (count == patternl.length) {
                count = 0;
            }
            resultTemp += patternl[count];
            count++;
        }

        long result = 0;
        while (resultTemp > 9) {
            long temp = resultTemp % 10;
            resultTemp /= 10;
            result += temp;
        }
        result += resultTemp;
        return result;
    }
}
