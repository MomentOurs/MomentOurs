package beyond.momentours.pre_blacklist.query.repository;

import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlacklistAll;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PreBlacklistMapper {

    List<ResponsePreBlacklistAll> findByPreBlacklistAll();
}
