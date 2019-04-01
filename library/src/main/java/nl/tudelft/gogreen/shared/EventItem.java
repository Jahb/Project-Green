package nl.tudelft.gogreen.shared;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Helper Class that creates and Event object which stores a Label and a Description.
 * Can be removed if believed to be inefficent.
 */
@AllArgsConstructor
@Data
public class EventItem {
    private String name;
    private String description;
    private String time;
    private String date;
}
