package UsefulFunctions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static UsefulFunctions.ArrayFunctions.concatenate;

public class Combinations {

    public static BigInteger factorialBigInteger(int n) {
        if (n < 0) return BigInteger.ZERO;
        if (n < 2) return BigInteger.ONE;
        BigInteger factorial = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }
    public static long factorialLong(int n) {
        if (n < 0) return 0L;
        if (n < 2) return 1L;
        long factorial = 1;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
            if (factorial < 0) throw new RuntimeException("Arguments too large!");
        }
        return factorial;
    }
    public static BigInteger nChooseMBigInteger(int n, int m) {
        BigInteger factorial1 = factorialBigInteger(n);
        BigInteger factorial2 = factorialBigInteger(m);
        BigInteger factorial3 = factorialBigInteger(n-m);
        return factorial1.divide(factorial2.multiply(factorial3));
    }
    public static long nChooseMOrderMattersLong(int n, int m) {
        long factorial1 = factorialLong(n);
        long factorial2 = factorialLong(n-m);
        if (factorial1 < 0 || factorial2 < 0) throw new RuntimeException("Arguments too large!");
        return factorial1 / factorial2;
    }
    public static int[][] chooseNElements(int[] arr, int n) {
        return chooseNElements(arr, n, false);
    }
    public static int[][] chooseNElementsOrderMatters(int[] arr, int n) {
        return chooseNElements(arr, n, true);
    }
    public static int[][] chooseNElements(int[] arr, int n, boolean orderMatters) {
        if (n == 0) return new int[][] {};
        if (n == 1) {
            int[][] output = new int[arr.length][];
            for (int i = 0; i < arr.length; i++) {
                output[i] = new int[] {arr[i]};
            }
            return output;
        }
        List<int[]> chosen = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            int[] chooseNextFrom;
            if (orderMatters) chooseNextFrom = ArrayFunctions.removeIndex(arr, i);
            else chooseNextFrom = ArrayFunctions.subArray(arr, i, arr.length-1);
            int[][] previousOutput = chooseNElements(chooseNextFrom, n-1, orderMatters);

            for (int[] ints : previousOutput) {
                chosen.add(ArrayFunctions.concatenate(new int[] {arr[i]}, ints));
            }
        }

        return Converter.arrListToArrInt(chosen);
    }
    public static long[] findPermutations(long n) {
        int[] digits = Converter.digitArray(n);
        int[][] permutations = findPermutations(digits);
        List<Long> perms = new ArrayList<>();

        for (int[] permutation : permutations) {
            long perm = Converter.digitFromArrayLong(permutation);
            if (Converter.digitArray(perm).length != digits.length) continue;
            perms.add(perm);
        }

        return Converter.listToArrLong(perms);
    }
    public static int[] findPermutations(int n) {
        int[] digits = Converter.digitArray(n);
        int[][] permutations = findPermutations(digits);
        List<Integer> perms = new ArrayList<>();

        for (int[] permutation : permutations) {
            int perm = (int) Converter.digitFromArrayLong(permutation);
            if (Converter.digitArray(perm).length != digits.length) continue;
            perms.add(perm);
        }

        return Converter.listToArrInt(perms);
    }
    public static int[][] findPermutations(int[] arr) {
        if (arr.length == 0) return new int[][] {};
        if (arr.length == 1) return new int[][] {arr};
        int[][] permutations = new int[(int) Combinations.factorialLong(arr.length)][];
        arr = ArrayFunctions.mergeSort(arr);
        int[] newArr0 = new int[arr.length];
        System.arraycopy(arr, 0, newArr0, 0, newArr0.length);
        permutations[0] = newArr0;
        for (int i = 1; i < permutations.length; i++) {
            arr = swapAndSort(arr);
            int[] newArr = new int[arr.length];
            System.arraycopy(arr, 0, newArr, 0, newArr.length);
            permutations[i] = newArr;
        }
        return permutations;
    }
    private static int[] swapAndSort(int[] arr) {
        int i;
        for (i = arr.length - 2; i >= 0; i--) {
            if (arr[i] < arr[i + 1]) {
                break;
            }
        }
        if (i < 0) return arr;
        int ceilingIndex = i + 1;
        for (int j = i+1; j < arr.length; j++) {
            if (arr[j] <= arr[i]) continue;
            if (arr[j] < arr[ceilingIndex]) ceilingIndex = j;
        }
        int temp = arr[i];
        arr[i] = arr[ceilingIndex];
        arr[ceilingIndex] = temp;

        int[] arrToBeSorted = new int[arr.length-i-1];
        System.arraycopy(arr, i+1, arrToBeSorted, 0, arrToBeSorted.length);
        arrToBeSorted = ArrayFunctions.mergeSort(arrToBeSorted);
        int[] restOfArr = new int[i+1];
        System.arraycopy(arr, 0, restOfArr, 0, restOfArr.length);
        return concatenate(restOfArr, arrToBeSorted);
    }
    public static boolean isPermutationOf(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) return false;
        arr1 = ArrayFunctions.mergeSort(arr1);
        arr2 = ArrayFunctions.mergeSort(arr2);
        return Arrays.equals(arr1, arr2);
    }
    public static int[][] combinationsOfGrowingNumbers(int start, int end, int amount) {
        if (amount == 1) {
            int[][] combinations = new int[end-start+1][];

            for (int i = 0; i <= end-start; i++) {
                combinations[i] = new int[] {start+i};
            }

            return combinations;
        }

        List<int[]> combinations = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            int[][] nextCombinations = combinationsOfGrowingNumbers(i, end, amount-1);

            for (int[] nextCombination : nextCombinations) {
                combinations.add(ArrayFunctions.concatenate(new int[] {i}, nextCombination));
            }
        }
        return Converter.arrListToArrInt(combinations);
    }
}
