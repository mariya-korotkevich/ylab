package io.ylab.intensive.lesson02.statsAccumulator;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulator s = new StatsAccumulatorImpl();
        s.add(1);
        s.add(2);
        System.out.println(s.getAvg()); // 1.5 - среднее арифметическое элементов
        s.add(0);
        System.out.println(s.getMin()); // 0 - минимальное из переданных значений
        s.add(3);
        s.add(8);
        System.out.println(s.getMax()); // 8 - максимальный из переданных
        System.out.println(s.getCount()); // 5 - количество переданных элементов
        System.out.println(s.getAvg()); //2.8 - среднее арифметическое
    }
}
