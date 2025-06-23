package beyond.momentours.like.command.domain.dto;

import beyond.momentours.like.command.domain.aggregate.LikeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequestDTO {
    private LikeType type;
    private Long targetId;
}
