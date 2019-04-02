package nl.tudelft.gogreen.shared;

public enum DatePeriod {
    WEEK(7),
    MONTH(30),
    YEAR(365);

    private int index;

    DatePeriod(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Get the next date when referring to the current one.
     * @return the next date!
     */
    public DatePeriod getNext() {
        switch (this) {
            case WEEK:
                return MONTH;
            case MONTH:
                return YEAR;
            default:
                return WEEK;
        }
    }
}

