package nl.tudelft.gogreen.shared;

import lombok.Data;

@Data
public class DateHolder {
    private double total;
    private double average;
    private double[] data;
    private int days;

    public DateHolder(double[] data) {
        this.days = data.length - 2;
        this.data = new double[days];
        System.arraycopy(data, 0, this.data, 0, days);
        this.total = data[days];
        this.average = data[days + 1];
    }

}
