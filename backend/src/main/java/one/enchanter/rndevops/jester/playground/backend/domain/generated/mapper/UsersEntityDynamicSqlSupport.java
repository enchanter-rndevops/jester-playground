package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class UsersEntityDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.841952788+09:00", comments="Source Table: users")
    public static final UsersEntity usersEntity = new UsersEntity();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.842744946+09:00", comments="Source field: users.sub")
    public static final SqlColumn<String> sub = usersEntity.sub;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.843679248+09:00", comments="Source field: users.email")
    public static final SqlColumn<String> email = usersEntity.email;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.843896651+09:00", comments="Source field: users.name")
    public static final SqlColumn<String> name = usersEntity.name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.843988771+09:00", comments="Source field: users.created_at")
    public static final SqlColumn<LocalDateTime> createdAt = usersEntity.createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.842544886+09:00", comments="Source Table: users")
    public static final class UsersEntity extends AliasableSqlTable<UsersEntity> {
        public final SqlColumn<String> sub = column("sub", JDBCType.VARCHAR).withJavaProperty("sub");

        public final SqlColumn<String> email = column("email", JDBCType.VARCHAR).withJavaProperty("email");

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR).withJavaProperty("name");

        public final SqlColumn<LocalDateTime> createdAt = column("created_at", JDBCType.TIMESTAMP).withJavaProperty("createdAt");

        public UsersEntity() {
            super("users", UsersEntity::new);
        }
    }
}