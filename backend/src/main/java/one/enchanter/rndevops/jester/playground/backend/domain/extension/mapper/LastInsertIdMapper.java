package one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * insert後に、自動採番idを取得する。
 * テーブルやカラムごとではなく、全体で最後に採番された値を返すため、必ずトランザクション内で使用し、意図したinsert文のidを返すようにすること。
 * 
 * LastInsertIdMapper
 */
@Mapper
public interface LastInsertIdMapper {
    @Select("SELECT LAST_INSERT_ID()")
    Long selectLastInsertId();
}
