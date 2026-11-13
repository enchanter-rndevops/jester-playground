package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class UsersEntityDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.097981297+09:00", comments="Source Table: users")
    public static final UsersEntity usersEntity = new UsersEntity();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.098689841+09:00", comments="Source field: users.id")
    public static final SqlColumn<Long> id = usersEntity.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.099352937+09:00", comments="Source field: users.uid")
    public static final SqlColumn<String> uid = usersEntity.uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.099526196+09:00", comments="Source field: users.name")
    public static final SqlColumn<String> name = usersEntity.name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.099595481+09:00", comments="Source field: users.email")
    public static final SqlColumn<String> email = usersEntity.email;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.099713602+09:00", comments="Source field: users.created_at")
    public static final SqlColumn<LocalDateTime> createdAt = usersEntity.createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.098456966+09:00", comments="Source Table: users")
    public static final class UsersEntity extends AliasableSqlTable<UsersEntity> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT).withJavaProperty("id");

        public final SqlColumn<String> uid = column("uid", JDBCType.VARCHAR).withJavaProperty("uid");

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR).withJavaProperty("name");

        public final SqlColumn<String> email = column("email", JDBCType.VARCHAR).withJavaProperty("email");

        public final SqlColumn<LocalDateTime> createdAt = column("created_at", JDBCType.TIMESTAMP).withJavaProperty("createdAt");

        public UsersEntity() {
            super("users", UsersEntity::new);
        }
    }
}