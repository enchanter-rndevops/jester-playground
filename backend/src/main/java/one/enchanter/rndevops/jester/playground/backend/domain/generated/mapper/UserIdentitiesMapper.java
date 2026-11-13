package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import static one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.UserIdentitiesEntityDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import jakarta.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.UserIdentitiesEntity;
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
public interface UserIdentitiesMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<UserIdentitiesEntity>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.119332582+09:00", comments="Source Table: user_identities")
    BasicColumn[] selectList = BasicColumn.columnList(id, userId, provider, providedUserId, createdAt);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.118307098+09:00", comments="Source Table: user_identities")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserIdentitiesEntityResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="provider", property="provider", jdbcType=JdbcType.VARCHAR),
        @Result(column="provided_user_id", property="providedUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.TIMESTAMP)
    })
    List<UserIdentitiesEntity> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.118518962+09:00", comments="Source Table: user_identities")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserIdentitiesEntityResult")
    Optional<UserIdentitiesEntity> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.118634709+09:00", comments="Source Table: user_identities")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, userIdentitiesEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.118700126+09:00", comments="Source Table: user_identities")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, userIdentitiesEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.118766727+09:00", comments="Source Table: user_identities")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.118823317+09:00", comments="Source Table: user_identities")
    default int insert(UserIdentitiesEntity row) {
        return MyBatis3Utils.insert(this::insert, row, userIdentitiesEntity, c ->
            c.withMappedColumn(id)
            .withMappedColumn(userId)
            .withMappedColumn(provider)
            .withMappedColumn(providedUserId)
            .withMappedColumn(createdAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.118963972+09:00", comments="Source Table: user_identities")
    default int insertMultiple(Collection<UserIdentitiesEntity> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, userIdentitiesEntity, c ->
            c.withMappedColumn(id)
            .withMappedColumn(userId)
            .withMappedColumn(provider)
            .withMappedColumn(providedUserId)
            .withMappedColumn(createdAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.119146699+09:00", comments="Source Table: user_identities")
    default int insertSelective(UserIdentitiesEntity row) {
        return MyBatis3Utils.insert(this::insert, row, userIdentitiesEntity, c ->
            c.withMappedColumnWhenPresent(id, row::getId)
            .withMappedColumnWhenPresent(userId, row::getUserId)
            .withMappedColumnWhenPresent(provider, row::getProvider)
            .withMappedColumnWhenPresent(providedUserId, row::getProvidedUserId)
            .withMappedColumnWhenPresent(createdAt, row::getCreatedAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.119396066+09:00", comments="Source Table: user_identities")
    default Optional<UserIdentitiesEntity> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, userIdentitiesEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.119573994+09:00", comments="Source Table: user_identities")
    default List<UserIdentitiesEntity> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, userIdentitiesEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.119854733+09:00", comments="Source Table: user_identities")
    default List<UserIdentitiesEntity> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, userIdentitiesEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.119992863+09:00", comments="Source Table: user_identities")
    default Optional<UserIdentitiesEntity> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.12009406+09:00", comments="Source Table: user_identities")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, userIdentitiesEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.120159919+09:00", comments="Source Table: user_identities")
    static UpdateDSL updateAllColumns(UserIdentitiesEntity row, UpdateDSL dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(userId).equalTo(row::getUserId)
                .set(provider).equalTo(row::getProvider)
                .set(providedUserId).equalTo(row::getProvidedUserId)
                .set(createdAt).equalTo(row::getCreatedAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.120290705+09:00", comments="Source Table: user_identities")
    static UpdateDSL updateSelectiveColumns(UserIdentitiesEntity row, UpdateDSL dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(userId).equalToWhenPresent(row::getUserId)
                .set(provider).equalToWhenPresent(row::getProvider)
                .set(providedUserId).equalToWhenPresent(row::getProvidedUserId)
                .set(createdAt).equalToWhenPresent(row::getCreatedAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.120484684+09:00", comments="Source Table: user_identities")
    default int updateByPrimaryKey(UserIdentitiesEntity row) {
        return update(c ->
            c.set(userId).equalTo(row::getUserId)
            .set(provider).equalTo(row::getProvider)
            .set(providedUserId).equalTo(row::getProvidedUserId)
            .set(createdAt).equalTo(row::getCreatedAt)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.120608295+09:00", comments="Source Table: user_identities")
    default int updateByPrimaryKeySelective(UserIdentitiesEntity row) {
        return update(c ->
            c.set(userId).equalToWhenPresent(row::getUserId)
            .set(provider).equalToWhenPresent(row::getProvider)
            .set(providedUserId).equalToWhenPresent(row::getProvidedUserId)
            .set(createdAt).equalToWhenPresent(row::getCreatedAt)
            .where(id, isEqualTo(row::getId))
        );
    }
}