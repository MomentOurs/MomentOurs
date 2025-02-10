package beyond.momentours.member.query.repository;

import beyond.momentours.member.query.vo.response.ResponseLoginHistoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<ResponseLoginHistoryVO> findLoginHistoryAll();
}
