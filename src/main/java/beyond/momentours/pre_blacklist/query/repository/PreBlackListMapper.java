package beyond.momentours.pre_blacklist.query.repository;

import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlackListAll;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PreBlackListMapper {

    List<ResponsePreBlackListAll> findByPreBlacklistAll();
}
