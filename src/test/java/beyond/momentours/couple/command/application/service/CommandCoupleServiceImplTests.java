package beyond.momentours.couple.command.application.service;

import beyond.momentours.couple.command.application.dto.CoupleListDTO;
import beyond.momentours.couple.command.application.dto.CoupleProfileDTO;
import beyond.momentours.couple.command.application.mapper.CoupleConverter;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import beyond.momentours.couple.command.domain.aggregate.entity.CoupleMatchingStatus;
import beyond.momentours.couple.command.domain.repository.CoupleRepository;
import beyond.momentours.couple.command.domain.vo.request.CoupleProfileRequestVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class CommandCoupleServiceImplTests {
    @Mock
    private CoupleRepository coupleRepository;

    @Mock
    private CoupleConverter converter;

    @InjectMocks
    private CommandCoupleServiceImpl coupleService;

    @Test
    @DisplayName("커플 프로필 수정 테스트")
    void updateCoupleProfile() {
        // given
        CoupleProfileRequestVO requestVO = new CoupleProfileRequestVO();
        requestVO.setCoupleId(1L);
        requestVO.setCoupleName("새로운 커플 이름");
        requestVO.setCouplePhoto("새로운 커플 사진");
        requestVO.setCoupleStartDate(LocalDateTime.of(2025, 1, 1, 0, 0));

        CoupleList testCouple = new CoupleList(
                1L,
                "기존 이름",
                "기존 사진",
                LocalDateTime.of(2024, 12, 12, 0, 0),
                true,
                CoupleMatchingStatus.PROFILE_COMPLETED,
                1L,
                2L
        );

        when(coupleRepository.findCoupleListByCoupleIdAndCoupleStatusIsTrue(requestVO.getCoupleId()))
                .thenReturn(Optional.of(testCouple));

        CoupleProfileDTO expectedDTO = new CoupleProfileDTO();
        when(converter.fromEntityToProfileDTO(any(CoupleList.class)))
                .thenReturn(expectedDTO);

        // when
        CoupleProfileDTO result = coupleService.updateProfile(requestVO);

        // then
        verify(coupleRepository, times(1))
                .findCoupleListByCoupleIdAndCoupleStatusIsTrue(requestVO.getCoupleId());

        assertEquals(testCouple.getCoupleName(), requestVO.getCoupleName());
        assertEquals(testCouple.getCouplePhoto(), requestVO.getCouplePhoto());
        assertEquals(testCouple.getCoupleStartDate(), requestVO.getCoupleStartDate());

        verify(converter, times(1)).fromEntityToProfileDTO(testCouple);
        assertEquals(expectedDTO, result);
    }

    @Test
    @DisplayName("커플 삭제 테스트")
    void deleteCouple() {
        // given
        Long coupleId = 1L;

        CoupleList testCouple = new CoupleList(
                1L,
                "기존 이름",
                "기존 사진",
                LocalDateTime.of(2024, 12, 12, 0, 0),
                true,
                CoupleMatchingStatus.PROFILE_COMPLETED,
                1L,
                2L
        );

        when(coupleRepository.findCoupleListByCoupleIdAndCoupleStatusIsTrue(coupleId))
                .thenReturn(Optional.of(testCouple));

        CoupleListDTO expectedDTO = new CoupleListDTO();
        expectedDTO.setCoupleId(1L);
        expectedDTO.setCoupleName("기존 이름");
        expectedDTO.setCouplePhoto("기존 사진");
        expectedDTO.setCoupleStartDate(LocalDateTime.of(2024, 12, 12, 0, 0));
        expectedDTO.setCoupleStatus(false);
        expectedDTO.setCoupleMatchingStatus(CoupleMatchingStatus.PROFILE_COMPLETED);
        expectedDTO.setMemberId1(1L);
        expectedDTO.setMemberId2(2L);

        when(converter.fromEntityToCoupleDTO(any(CoupleList.class)))
                .thenReturn(expectedDTO);

        // when
        CoupleListDTO deletedCouple = coupleService.deleteCouple(coupleId);

        // then
        verify(coupleRepository, times(1))
                .findCoupleListByCoupleIdAndCoupleStatusIsTrue(coupleId);

        assertEquals(testCouple.getCoupleStatus(), false);
        assertEquals(deletedCouple.getCoupleStatus(), false);
        assertEquals(expectedDTO, deletedCouple);
    }
}