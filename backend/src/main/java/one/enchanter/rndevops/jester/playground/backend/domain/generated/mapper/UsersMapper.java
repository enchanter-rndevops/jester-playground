package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import static one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.UsersEntityDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import jakarta.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.UsersEntity;
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
public interface UsersMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<UsersEntity>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.108718109+09:00", comments="Source Table: users")
    BasicColumn[] selectList = BasicColumn.columnList(id, uid, name, email, createdAt);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.10040835+09:00", comments="Source Table: users")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UsersEntityResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="uid", property="uid", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.TIMESTAMP)
    })
    List<UsersEntity> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.103625036+09:00", comments="Source Table: users")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UsersEntityResult")
    Optional<UsersEntity> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.104166324+09:00", comments="Source Table: users")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.104758181+09:00", comments="Source Table: users")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.105399834+09:00", comments="Source Table: users")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.105781711+09:00", comments="Source Table: users")
    default int insert(UsersEntity row) {
        return MyBatis3Utils.insert(this::insert, row, usersEntity, c ->
            c.withMappedColumn(id)
            .withMappedColumn(uid)
            .withMappedColumn(name)
            .withMappedColumn(email)
            .withMappedColumn(createdAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.107262354+09:00", comments="Source Table: users")
    default int insertMultiple(Collection<UsersEntity> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, usersEntity, c ->
            c.withMappedColumn(id)
            .withMappedColumn(uid)
            .withMappedColumn(name)
            .withMappedColumn(email)
            .withMappedColumn(createdAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.107948395+09:00", comments="Source Table: users")
    default int insertSelective(UsersEntity row) {
        return MyBatis3Utils.insert(this::insert, row, usersEntity, c ->
            c.withMappedColumnWhenPresent(id, row::getId)
            .withMappedColumnWhenPresent(uid, row::getUid)
            .withMappedColumnWhenPresent(name, row::getName)
            .withMappedColumnWhenPresent(email, row::getEmail)
            .withMappedColumnWhenPresent(createdAt, row::getCreatedAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.109289155+09:00", comments="Source Table: users")
    default Optional<UsersEntity> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.109794372+09:00", comments="Source Table: users")
    default List<UsersEntity> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.11008462+09:00", comments="Source Table: users")
    default List<UsersEntity> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.110573846+09:00", comments="Source Table: users")
    default Optional<UsersEntity> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.110979779+09:00", comments="Source Table: users")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.111482642+09:00", comments="Source Table: users")
    static UpdateDSL updateAllColumns(UsersEntity row, UpdateDSL dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(uid).equalTo(row::getUid)
                .set(name).equalTo(row::getName)
                .set(email).equalTo(row::getEmail)
                .set(createdAt).equalTo(row::getCreatedAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.11351559+09:00", comments="Source Table: users")
    static UpdateDSL updateSelectiveColumns(UsersEntity row, UpdateDSL dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(uid).equalToWhenPresent(row::getUid)
                .set(name).equalToWhenPresent(row::getName)
                .set(email).equalToWhenPresent(row::getEmail)
                .set(createdAt).equalToWhenPresent(row::getCreatedAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.114062303+09:00", comments="Source Table: users")
    default int updateByPrimaryKey(UsersEntity row) {
        return update(c ->
            c.set(uid).equalTo(row::getUid)
            .set(name).equalTo(row::getName)
            .set(email).equalTo(row::getEmail)
            .set(createdAt).equalTo(row::getCreatedAt)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.114578341+09:00", comments="Source Table: users")
    default int updateByPrimaryKeySelective(UsersEntity row) {
        return update(c ->
            c.set(uid).equalToWhenPresent(row::getUid)
            .set(name).equalToWhenPresent(row::getName)
            .set(email).equalToWhenPresent(row::getEmail)
            .set(createdAt).equalToWhenPresent(row::getCreatedAt)
            .where(id, isEqualTo(row::getId))
        );
    }
}