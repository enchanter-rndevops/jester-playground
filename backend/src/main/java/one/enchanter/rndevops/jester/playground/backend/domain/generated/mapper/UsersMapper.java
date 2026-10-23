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
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.853966531+09:00", comments="Source Table: users")
    BasicColumn[] selectList = BasicColumn.columnList(sub, email, name, createdAt);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.844832285+09:00", comments="Source Table: users")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UsersEntityResult", value = {
        @Result(column="sub", property="sub", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.TIMESTAMP)
    })
    List<UsersEntity> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.848382777+09:00", comments="Source Table: users")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UsersEntityResult")
    Optional<UsersEntity> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.848943891+09:00", comments="Source Table: users")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.849536446+09:00", comments="Source Table: users")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.850067471+09:00", comments="Source Table: users")
    default int deleteByPrimaryKey(String sub_) {
        return delete(c -> 
            c.where(sub, isEqualTo(sub_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.850594037+09:00", comments="Source Table: users")
    default int insert(UsersEntity row) {
        return MyBatis3Utils.insert(this::insert, row, usersEntity, c ->
            c.withMappedColumn(sub)
            .withMappedColumn(email)
            .withMappedColumn(name)
            .withMappedColumn(createdAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.852353577+09:00", comments="Source Table: users")
    default int insertMultiple(Collection<UsersEntity> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, usersEntity, c ->
            c.withMappedColumn(sub)
            .withMappedColumn(email)
            .withMappedColumn(name)
            .withMappedColumn(createdAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.853185409+09:00", comments="Source Table: users")
    default int insertSelective(UsersEntity row) {
        return MyBatis3Utils.insert(this::insert, row, usersEntity, c ->
            c.withMappedColumnWhenPresent(sub, row::getSub)
            .withMappedColumnWhenPresent(email, row::getEmail)
            .withMappedColumnWhenPresent(name, row::getName)
            .withMappedColumnWhenPresent(createdAt, row::getCreatedAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.854521273+09:00", comments="Source Table: users")
    default Optional<UsersEntity> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.855066385+09:00", comments="Source Table: users")
    default List<UsersEntity> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.855501223+09:00", comments="Source Table: users")
    default List<UsersEntity> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.855973523+09:00", comments="Source Table: users")
    default Optional<UsersEntity> selectByPrimaryKey(String sub_) {
        return selectOne(c ->
            c.where(sub, isEqualTo(sub_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.856532252+09:00", comments="Source Table: users")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, usersEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.857542031+09:00", comments="Source Table: users")
    static UpdateDSL updateAllColumns(UsersEntity row, UpdateDSL dsl) {
        return dsl.set(sub).equalTo(row::getSub)
                .set(email).equalTo(row::getEmail)
                .set(name).equalTo(row::getName)
                .set(createdAt).equalTo(row::getCreatedAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.859945205+09:00", comments="Source Table: users")
    static UpdateDSL updateSelectiveColumns(UsersEntity row, UpdateDSL dsl) {
        return dsl.set(sub).equalToWhenPresent(row::getSub)
                .set(email).equalToWhenPresent(row::getEmail)
                .set(name).equalToWhenPresent(row::getName)
                .set(createdAt).equalToWhenPresent(row::getCreatedAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.86057886+09:00", comments="Source Table: users")
    default int updateByPrimaryKey(UsersEntity row) {
        return update(c ->
            c.set(email).equalTo(row::getEmail)
            .set(name).equalTo(row::getName)
            .set(createdAt).equalTo(row::getCreatedAt)
            .where(sub, isEqualTo(row::getSub))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.861121618+09:00", comments="Source Table: users")
    default int updateByPrimaryKeySelective(UsersEntity row) {
        return update(c ->
            c.set(email).equalToWhenPresent(row::getEmail)
            .set(name).equalToWhenPresent(row::getName)
            .set(createdAt).equalToWhenPresent(row::getCreatedAt)
            .where(sub, isEqualTo(row::getSub))
        );
    }
}