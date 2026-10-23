package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SessionsEntityDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.864871768+09:00", comments="Source Table: sessions")
    public static final SessionsEntity sessionsEntity = new SessionsEntity();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.865092328+09:00", comments="Source field: sessions.id")
    public static final SqlColumn<String> id = sessionsEntity.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.865187714+09:00", comments="Source field: sessions.user_sub")
    public static final SqlColumn<String> userSub = sessionsEntity.userSub;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.865275345+09:00", comments="Source field: sessions.created_at")
    public static final SqlColumn<LocalDateTime> createdAt = sessionsEntity.createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.865360271+09:00", comments="Source field: sessions.expires_at")
    public static final SqlColumn<LocalDateTime> expiresAt = sessionsEntity.expiresAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.864972925+09:00", comments="Source Table: sessions")
    public static final class SessionsEntity extends AliasableSqlTable<SessionsEntity> {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR).withJavaProperty("id");

        public final SqlColumn<String> userSub = column("user_sub", JDBCType.CHAR).withJavaProperty("userSub");

        public final SqlColumn<LocalDateTime> createdAt = column("created_at", JDBCType.TIMESTAMP).withJavaProperty("createdAt");

        public final SqlColumn<LocalDateTime> expiresAt = column("expires_at", JDBCType.TIMESTAMP).withJavaProperty("expiresAt");

        public SessionsEntity() {
            super("sessions", SessionsEntity::new);
        }
    }
}