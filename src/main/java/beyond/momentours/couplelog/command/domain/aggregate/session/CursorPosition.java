package beyond.momentours.couplelog.command.domain.aggregate.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursorPosition implements Serializable {
    private int row;
    private int column;
}
