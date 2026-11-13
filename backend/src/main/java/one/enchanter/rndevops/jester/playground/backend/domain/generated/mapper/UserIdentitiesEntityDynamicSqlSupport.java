package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class UserIdentitiesEntityDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.117732185+09:00", comments="Source Table: user_identities")
    public static final UserIdentitiesEntity userIdentitiesEntity = new UserIdentitiesEntity();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.117875966+09:00", comments="Source field: user_identities.id")
    public static final SqlColumn<Long> id = userIdentitiesEntity.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.117932105+09:00", comments="Source field: user_identities.user_id")
    public static final SqlColumn<Long> userId = userIdentitiesEntity.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.11803145+09:00", comments="Source field: user_identities.provider")
    public static final SqlColumn<String> provider = userIdentitiesEntity.provider;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.118138809+09:00", comments="Source field: user_identities.provided_user_id")
    public static final SqlColumn<String> providedUserId = userIdentitiesEntity.providedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.118245448+09:00", comments="Source field: user_identities.created_at")
    public static final SqlColumn<LocalDateTime> createdAt = userIdentitiesEntity.createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.11780137+09:00", comments="Source Table: user_identities")
    public static final class UserIdentitiesEntity extends AliasableSqlTable<UserIdentitiesEntity> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT).withJavaProperty("id");

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT).withJavaProperty("userId");

        public final SqlColumn<String> provider = column("provider", JDBCType.VARCHAR).withJavaProperty("provider");

        public final SqlColumn<String> providedUserId = column("provided_user_id", JDBCType.VARCHAR).withJavaProperty("providedUserId");

        public final SqlColumn<LocalDateTime> createdAt = column("created_at", JDBCType.TIMESTAMP).withJavaProperty("createdAt");

        public UserIdentitiesEntity() {
            super("user_identities", UserIdentitiesEntity::new);
        }
    }
}