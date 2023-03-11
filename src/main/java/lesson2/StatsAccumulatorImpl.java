package lesson2;

public class StatsAccumulatorImpl implements StatsAccumulator{
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;
    private int count = 0;
    private int sum = 0;

    @Override
    public void add(int value) {
        if (value < min){
            min = value;
        }
        if (value > max){
            max = value;
        }
        count++;
        sum += value;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        return (double) sum / count;
    }
}
