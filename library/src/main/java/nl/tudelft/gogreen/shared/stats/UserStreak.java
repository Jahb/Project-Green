package nl.tudelft.gogreen.shared.stats;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class UserStreak {
    @NonNull
    private String username;
    @NonNull
    private Date startDate;
    private int days;
}
