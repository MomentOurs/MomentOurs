//package beyond.momentours.moment.command.application.service;
//
//import beyond.momentours.location.command.application.dto.LocationDTO;
//import beyond.momentours.location.command.application.service.LocationCommandService;
//import beyond.momentours.location.command.domain.aggregate.LocationStatus;
//import beyond.momentours.moment.command.application.dto.MomentDTO;
//import beyond.momentours.moment.common.converter.MomentConverter;
//import beyond.momentours.moment.command.domain.aggregate.entity.Moment;
//import beyond.momentours.moment.command.domain.aggregate.repository.MomentRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@Transactional
//class MomentServiceImplTest {
//
//    @Mock
//    private MomentRepository momentRepository;
//
//    @Mock
//    private MomentConverter momentConverter;
//
//    @Mock
//    private LocationCommandService locationCommandService;
//
//    @InjectMocks
//    private MomentServiceImpl momentService;
//
//    private MomentDTO momentDTO;
//    private LocationDTO mockLocationDTO;
//    private Moment mockMoment;
//    private ResponseMomentDTO mockResponseMomentDTO;
//    private Long memberId;
//
//    @BeforeEach
//    void setUp() {
//        // 회원 ID를 직접 지정
//        memberId = 100L;
//
//        // Mock 데이터 설정
//        momentDTO = new MomentDTO(
//                "테스트 추억",
//                "맛집",
//                "아 너무 맛있다.",
//                true,
//                true,
//                memberId,
//                new BigDecimal("48.8584"),
//                new BigDecimal("2.2945"),
//                "테스트 맛집"
//        );
//
//        mockLocationDTO = new LocationDTO(
//                2L,
//                new BigDecimal("48.8584"),
//                new BigDecimal("2.2945"),
//                "테스트 맛집",
//                LocationStatus.UNCHANGED,
//                LocalDateTime.now(),
//                LocalDateTime.now()
//        );
//
//        mockMoment = new Moment(
//                10L,
//                momentDTO.getMomentTitle(),
//                momentDTO.getMomentCategory(),
//                momentDTO.getMomentContent(),
//                momentDTO.isMomentDisclosure(),
//                momentDTO.isMomentCommentStatus(),
//                0,
//                0,
//                true,
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                memberId,
//                mockLocationDTO.getLocationId()
//        );
//
//        mockResponseMomentDTO = new ResponseMomentDTO(
//                mockMoment.getMomentId(),
//                mockMoment.getMomentTitle(),
//                mockMoment.getMomentCategory(),
//                mockMoment.getMomentContent(),
//                mockMoment.isMomentDisclosure(),
//                mockMoment.isMomentCommentStatus(),
//                mockMoment.getMomentLike(),
//                mockMoment.getMomentView(),
//                mockMoment.isMomentStatus(),
//                mockMoment.getCreatedAt(),
//                mockMoment.getUpdatedAt(),
//                mockMoment.getLocationId(),
//                mockMoment.getMemberId()
//        );
//    }
//
//    @Test
//    @DisplayName("추억 생성 테스트")
//    void createMomentTest() {
//        // Given (Mocking 설정)
//        when(locationCommandService.findOrCreateLocation(
//                anyString(), any(BigDecimal.class), any(BigDecimal.class)))
//                .thenReturn(mockLocationDTO);
//
//        when(momentConverter.fromDTOToEntity(any(MomentDTO.class), anyLong(), anyLong()))
//                .thenReturn(mockMoment);
//
//        when(momentRepository.save(any(Moment.class)))
//                .thenReturn(mockMoment);
//
//        when(momentConverter.fromEntityToDTO(any(Moment.class)))
//                .thenReturn(mockResponseMomentDTO);
//
//        // When
//        ResponseMomentDTO result = momentService.createMoment(momentDTO, memberId);
//
//        // Then (검증)
//        verify(locationCommandService, times(1))
//                .findOrCreateLocation(anyString(), any(BigDecimal.class), any(BigDecimal.class));
//
//        verify(momentConverter, times(1))
//                .fromDTOToEntity(any(MomentDTO.class), anyLong(), anyLong());
//
//        verify(momentRepository, times(1))
//                .save(any(Moment.class));
//
//        verify(momentConverter, times(1))
//                .fromEntityToDTO(any(Moment.class));
//
//        // 결과값 검증
//        assertEquals(mockResponseMomentDTO, result);
//    }
//}
