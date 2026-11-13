package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import static one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.SessionsEntityDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import jakarta.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.SessionsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.dsl.CountDSLCompleter;
import org.mybatis.dynamic.sql.dsl.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.dsl.SelectDSLCompleter;
import org.mybatis.dynamic.sql.dsl.UpdateDSL;
import org.mybatis.dynamic.sql.dsl.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface SessionsMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<SessionsEntity>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.123342014+09:00", comments="Source Table: sessions")
    BasicColumn[] selectList = BasicColumn.columnList(id, userId, createdAt, expiresAt);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122375134+09:00", comments="Source Table: sessions")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="SessionsEntityResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="expires_at", property="expiresAt", jdbcType=JdbcType.TIMESTAMP)
    })
    List<SessionsEntity> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122515318+09:00", comments="Source Table: sessions")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("SessionsEntityResult")
    Optional<SessionsEntity> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122648067+09:00", comments="Source Table: sessions")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, sessionsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122750437+09:00", comments="Source Table: sessions")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, sessionsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122810795+09:00", comments="Source Table: sessions")
    default int deleteByPrimaryKey(String id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122858058+09:00", comments="Source Table: sessions")
    default int insert(SessionsEntity row) {
        return MyBatis3Utils.insert(this::insert, row, sessionsEntity, c ->
            c.withMappedColumn(id)
            .withMappedColumn(userId)
            .withMappedColumn(createdAt)
            .withMappedColumn(expiresAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122987651+09:00", comments="Source Table: sessions")
    default int insertMultiple(Collection<SessionsEntity> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, sessionsEntity, c ->
            c.withMappedColumn(id)
            .withMappedColumn(userId)
            .withMappedColumn(createdAt)
            .withMappedColumn(expiresAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.123122254+09:00", comments="Source Table: sessions")
    default int insertSelective(SessionsEntity row) {
        return MyBatis3Utils.insert(this::insert, row, sessionsEntity, c ->
            c.withMappedColumnWhenPresent(id, row::getId)
            .withMappedColumnWhenPresent(userId, row::getUserId)
            .withMappedColumnWhenPresent(createdAt, row::getCreatedAt)
            .withMappedColumnWhenPresent(expiresAt, row::getExpiresAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.123410688+09:00", comments="Source Table: sessions")
    default Optional<SessionsEntity> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, sessionsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.123487117+09:00", comments="Source Table: sessions")
    default List<SessionsEntity> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, sessionsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.123608384+09:00", comments="Source Table: sessions")
    default List<SessionsEntity> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, sessionsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.123690044+09:00", comments="Source Table: sessions")
    default Optional<SessionsEntity> selectByPrimaryKey(String id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.123824316+09:00", comments="Source Table: sessions")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, sessionsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.12387233+09:00", comments="Source Table: sessions")
    static UpdateDSL updateAllColumns(SessionsEntity row, UpdateDSL dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(userId).equalTo(row::getUserId)
                .set(createdAt).equalTo(row::getCreatedAt)
                .set(expiresAt).equalTo(row::getExpiresAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.123965853+09:00", comments="Source Table: sessions")
    static UpdateDSL updateSelectiveColumns(SessionsEntity row, UpdateDSL dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(userId).equalToWhenPresent(row::getUserId)
                .set(createdAt).equalToWhenPresent(row::getCreatedAt)
                .set(expiresAt).equalToWhenPresent(row::getExpiresAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.124081048+09:00", comments="Source Table: sessions")
    default int updateByPrimaryKey(SessionsEntity row) {
        return update(c ->
            c.set(userId).equalTo(row::getUserId)
            .set(createdAt).equalTo(row::getCreatedAt)
            .set(expiresAt).equalTo(row::getExpiresAt)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.124177136+09:00", comments="Source Table: sessions")
    default int updateByPrimaryKeySelective(SessionsEntity row) {
        return update(c ->
            c.set(userId).equalToWhenPresent(row::getUserId)
            .set(createdAt).equalToWhenPresent(row::getCreatedAt)
            .set(expiresAt).equalToWhenPresent(row::getExpiresAt)
            .where(id, isEqualTo(row::getId))
        );
    }
}