package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import static one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.ItemsEntityDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import jakarta.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.ItemsEntity;
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
public interface ItemsMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<ItemsEntity>, CommonUpdateMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.871310349+09:00", comments="Source Table: items")
    BasicColumn[] selectList = BasicColumn.columnList(id, userSub, name, description, createdAt, updatedAt);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.87032073+09:00", comments="Source Table: items")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ItemsEntityResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_sub", property="userSub", jdbcType=JdbcType.CHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated_at", property="updatedAt", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ItemsEntity> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.870470542+09:00", comments="Source Table: items")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ItemsEntityResult")
    Optional<ItemsEntity> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.87058756+09:00", comments="Source Table: items")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, itemsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.870646255+09:00", comments="Source Table: items")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, itemsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.870696312+09:00", comments="Source Table: items")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.870828951+09:00", comments="Source Table: items")
    default int insert(ItemsEntity row) {
        return MyBatis3Utils.insert(this::insert, row, itemsEntity, c ->
            c.withMappedColumn(id)
            .withMappedColumn(userSub)
            .withMappedColumn(name)
            .withMappedColumn(description)
            .withMappedColumn(createdAt)
            .withMappedColumn(updatedAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.870959405+09:00", comments="Source Table: items")
    default int insertMultiple(Collection<ItemsEntity> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, itemsEntity, c ->
            c.withMappedColumn(id)
            .withMappedColumn(userSub)
            .withMappedColumn(name)
            .withMappedColumn(description)
            .withMappedColumn(createdAt)
            .withMappedColumn(updatedAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.871098446+09:00", comments="Source Table: items")
    default int insertSelective(ItemsEntity row) {
        return MyBatis3Utils.insert(this::insert, row, itemsEntity, c ->
            c.withMappedColumnWhenPresent(id, row::getId)
            .withMappedColumnWhenPresent(userSub, row::getUserSub)
            .withMappedColumnWhenPresent(name, row::getName)
            .withMappedColumnWhenPresent(description, row::getDescription)
            .withMappedColumnWhenPresent(createdAt, row::getCreatedAt)
            .withMappedColumnWhenPresent(updatedAt, row::getUpdatedAt)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.871368062+09:00", comments="Source Table: items")
    default Optional<ItemsEntity> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, itemsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.871531811+09:00", comments="Source Table: items")
    default List<ItemsEntity> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, itemsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.871649691+09:00", comments="Source Table: items")
    default List<ItemsEntity> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, itemsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.871770085+09:00", comments="Source Table: items")
    default Optional<ItemsEntity> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.871914016+09:00", comments="Source Table: items")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, itemsEntity, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.871978993+09:00", comments="Source Table: items")
    static UpdateDSL updateAllColumns(ItemsEntity row, UpdateDSL dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(userSub).equalTo(row::getUserSub)
                .set(name).equalTo(row::getName)
                .set(description).equalTo(row::getDescription)
                .set(createdAt).equalTo(row::getCreatedAt)
                .set(updatedAt).equalTo(row::getUpdatedAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.872194934+09:00", comments="Source Table: items")
    static UpdateDSL updateSelectiveColumns(ItemsEntity row, UpdateDSL dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(userSub).equalToWhenPresent(row::getUserSub)
                .set(name).equalToWhenPresent(row::getName)
                .set(description).equalToWhenPresent(row::getDescription)
                .set(createdAt).equalToWhenPresent(row::getCreatedAt)
                .set(updatedAt).equalToWhenPresent(row::getUpdatedAt);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.872425293+09:00", comments="Source Table: items")
    default int updateByPrimaryKey(ItemsEntity row) {
        return update(c ->
            c.set(userSub).equalTo(row::getUserSub)
            .set(name).equalTo(row::getName)
            .set(description).equalTo(row::getDescription)
            .set(createdAt).equalTo(row::getCreatedAt)
            .set(updatedAt).equalTo(row::getUpdatedAt)
            .where(id, isEqualTo(row::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.872675621+09:00", comments="Source Table: items")
    default int updateByPrimaryKeySelective(ItemsEntity row) {
        return update(c ->
            c.set(userSub).equalToWhenPresent(row::getUserSub)
            .set(name).equalToWhenPresent(row::getName)
            .set(description).equalToWhenPresent(row::getDescription)
            .set(createdAt).equalToWhenPresent(row::getCreatedAt)
            .set(updatedAt).equalToWhenPresent(row::getUpdatedAt)
            .where(id, isEqualTo(row::getId))
        );
    }
}