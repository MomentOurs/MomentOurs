package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.query.service.QueryCoupleService;
import beyond.momentours.couplelog.command.domain.aggregate.entity.CoupleLog;
import beyond.momentours.couplelog.command.domain.repository.CoupleLogRepository;
import beyond.momentours.couplelog.command.domain.vo.request.CoupleLogRequestVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.domain.aggregate.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class CommandCoupleLogServiceImplTests {
    @Mock
    private CoupleLogRepository coupleLogRepository;

    @Mock
    private QueryCoupleService coupleService;

    @InjectMocks
    private CommandCoupleLogService service;

    private CustomUserDetails user;
    private CoupleListDTO couple;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .memberId(1L)
                .build();
        user = new CustomUserDetails(member);

        couple = new CoupleListDTO();
        couple.setCoupleId(1L);
    }

    @Test
    @DisplayName("새로운 커플로그 작성 테스트")
    void createNewCoupleLog() {
        // given
        CoupleLogRequestVO requestVO = CoupleLogRequestVO.builder()
                .coupleId(1L)
                .textContent("테스트 커플로그 텍스트")
                .build();

        when(coupleService.getCoupleByMemberId(user.getMember().getMemberId())).thenReturn(couple);

        // when
        service.createNewCoupleLog(requestVO, user);

        // then
        verify(coupleLogRepository, times(1)).save(any(CoupleLog.class));

        // 실제 save method에 전달된 파라미터를 확인하기 위한 ArgumentCaptor
        ArgumentCaptor<CoupleLog> captor = ArgumentCaptor.forClass(CoupleLog.class);
        // 캡쳐 수행
        verify(coupleLogRepository).save(captor.capture());
        // 캡쳐된 파라미터 분석
        CoupleLog savedLog = captor.getValue();
        assertAll(
                () -> assertEquals(requestVO.getTextContent(), savedLog.getCoupleLogContent()),
                () -> assertEquals(requestVO.getCoupleId(), savedLog.getCoupleId()),
                () -> assertEquals(user.getMember().getMemberId(), savedLog.getMemberId())
        );
    }
}