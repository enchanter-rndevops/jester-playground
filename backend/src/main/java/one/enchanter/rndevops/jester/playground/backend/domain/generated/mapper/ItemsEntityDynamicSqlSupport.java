package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class ItemsEntityDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.125864122+09:00", comments="Source Table: items")
    public static final ItemsEntity itemsEntity = new ItemsEntity();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.126135583+09:00", comments="Source field: items.id")
    public static final SqlColumn<Long> id = itemsEntity.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.126180591+09:00", comments="Source field: items.user_id")
    public static final SqlColumn<Long> userId = itemsEntity.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.126219928+09:00", comments="Source field: items.name")
    public static final SqlColumn<String> name = itemsEntity.name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.126258383+09:00", comments="Source field: items.description")
    public static final SqlColumn<String> description = itemsEntity.description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.126297219+09:00", comments="Source field: items.created_at")
    public static final SqlColumn<LocalDateTime> createdAt = itemsEntity.createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.126335924+09:00", comments="Source field: items.updated_at")
    public static final SqlColumn<LocalDateTime> updatedAt = itemsEntity.updatedAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.126073882+09:00", comments="Source Table: items")
    public static final class ItemsEntity extends AliasableSqlTable<ItemsEntity> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT).withJavaProperty("id");

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT).withJavaProperty("userId");

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR).withJavaProperty("name");

        public final SqlColumn<String> description = column("description", JDBCType.VARCHAR).withJavaProperty("description");

        public final SqlColumn<LocalDateTime> createdAt = column("created_at", JDBCType.TIMESTAMP).withJavaProperty("createdAt");

        public final SqlColumn<LocalDateTime> updatedAt = column("updated_at", JDBCType.TIMESTAMP).withJavaProperty("updatedAt");

        public ItemsEntity() {
            super("items", ItemsEntity::new);
        }
    }
}