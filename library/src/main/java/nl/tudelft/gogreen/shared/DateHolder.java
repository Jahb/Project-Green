package nl.tudelft.gogreen.shared;

import lombok.Data;

@Data
public class DateHolder {
    private double total;
    private double average;
    private double[] data;
    private int days;

    /**
     * Create a new DateHolder object, the input is an array.
     * {1,2,3,4,5,6}, where 1 through 4 are the values for the days, 5 the average and 6 the total
     * @param data the array as specified
     */
    public DateHolder(double[] data) {
        this.days = data.length - 2;
        this.data = new double[days];
        System.arraycopy(data, 0, this.data, 0, days);
        this.total = data[days];
        this.average = data[days + 1];
    }

}
