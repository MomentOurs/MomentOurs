package beyond.momentours.couple.query.service;

import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.mapper.CoupleConverter;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleMatchingStatus;
import beyond.momentours.couple.query.repository.CoupleMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueryCoupleServiceImplTests {
    @Mock
    private CoupleMapper coupleMapper;

    @Mock
    private CoupleConverter converter;

    @InjectMocks
    private QueryCoupleServiceImpl coupleService;

    @Test
    @DisplayName("특정 커플번호로 활성상태인 커플 조회 테스트")
    void getActivatedCoupleById() {
        // given
        Long coupleId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2024, 12, 12, 0, 0);
        CoupleList testCouple = new CoupleList(
                1L,
                "테스트 커플 이름",
                "test_photo.png",
                startDate,
                true,
                CoupleMatchingStatus.PROFILE_COMPLETED,
                1L,
                2L
        );

        when(coupleMapper.getCoupleByCoupleId(coupleId)).thenReturn(testCouple);

        CoupleListDTO expectedDTO = CoupleListDTO.builder()
                .coupleId(coupleId)
                .coupleName("테스트 커플 이름")
                .couplePhoto("test_photo.png")
                .coupleStartDate(startDate)
                .coupleStatus(true)
                .coupleMatchingStatus(CoupleMatchingStatus.PROFILE_COMPLETED)
                .memberId1(1L)
                .memberId2(2L)
                .build();
        when(converter.fromEntityToCoupleDTO(any(CoupleList.class))).thenReturn(expectedDTO);

        // when
        CoupleListDTO result = coupleService.getCoupleById(coupleId);

        // then
        verify(coupleMapper, times(1)).getCoupleByCoupleId(coupleId);
        verify(converter, times(1)).fromEntityToCoupleDTO(any(CoupleList.class));
        assertNotNull(result);
        assertEquals(result, expectedDTO);
        assertEquals(result.getCoupleId(), coupleId);
        assertEquals(result.getCoupleStatus(), true);
    }
}