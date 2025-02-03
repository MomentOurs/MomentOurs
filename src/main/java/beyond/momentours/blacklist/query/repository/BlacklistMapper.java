package beyond.momentours.blacklist.query.repository;

import beyond.momentours.blacklist.query.vo.response.ResponseBlacklistAll;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlacklistMapper {
    List<ResponseBlacklistAll> findByBlacklistAll();
}
