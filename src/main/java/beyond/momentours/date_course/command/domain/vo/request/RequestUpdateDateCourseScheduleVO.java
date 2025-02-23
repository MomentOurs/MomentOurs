package beyond.momentours.date_course.command.domain.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateDateCourseScheduleVO {

    @NotNull(message = "시작 날짜를 입력해야 합니다.")
    private LocalDateTime courseStartDate;

    @NotNull(message = "종료 날짜를 입력해야 합니다.")
    private LocalDateTime courseEndDate;
}