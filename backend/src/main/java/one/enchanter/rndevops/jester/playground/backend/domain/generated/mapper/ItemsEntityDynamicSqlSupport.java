package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class ItemsEntityDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.869642919+09:00", comments="Source Table: items")
    public static final ItemsEntity itemsEntity = new ItemsEntity();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.869831657+09:00", comments="Source field: items.id")
    public static final SqlColumn<Long> id = itemsEntity.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.869894019+09:00", comments="Source field: items.user_sub")
    public static final SqlColumn<String> userSub = itemsEntity.userSub;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.870000717+09:00", comments="Source field: items.name")
    public static final SqlColumn<String> name = itemsEntity.name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.870137663+09:00", comments="Source field: items.description")
    public static final SqlColumn<String> description = itemsEntity.description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.870213351+09:00", comments="Source field: items.created_at")
    public static final SqlColumn<LocalDateTime> createdAt = itemsEntity.createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.87026394+09:00", comments="Source field: items.updated_at")
    public static final SqlColumn<LocalDateTime> updatedAt = itemsEntity.updatedAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.869728646+09:00", comments="Source Table: items")
    public static final class ItemsEntity extends AliasableSqlTable<ItemsEntity> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT).withJavaProperty("id");

        public final SqlColumn<String> userSub = column("user_sub", JDBCType.CHAR).withJavaProperty("userSub");

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR).withJavaProperty("name");

        public final SqlColumn<String> description = column("description", JDBCType.VARCHAR).withJavaProperty("description");

        public final SqlColumn<LocalDateTime> createdAt = column("created_at", JDBCType.TIMESTAMP).withJavaProperty("createdAt");

        public final SqlColumn<LocalDateTime> updatedAt = column("updated_at", JDBCType.TIMESTAMP).withJavaProperty("updatedAt");

        public ItemsEntity() {
            super("items", ItemsEntity::new);
        }
    }
}