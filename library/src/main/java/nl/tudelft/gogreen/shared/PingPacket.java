package nl.tudelft.gogreen.shared;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PingPacket {
    @NonNull
    private PingPacketData dataType;
    @NonNull
    private String data;
}
