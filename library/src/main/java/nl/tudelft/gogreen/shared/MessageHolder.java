package nl.tudelft.gogreen.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageHolder<T> {
    @NonNull
    private String message;
    private T data;

}
