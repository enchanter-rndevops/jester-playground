package one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper;

import jakarta.annotation.Generated;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SessionsEntityDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.121744275+09:00", comments="Source Table: sessions")
    public static final SessionsEntity sessionsEntity = new SessionsEntity();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122116278+09:00", comments="Source field: sessions.id")
    public static final SqlColumn<String> id = sessionsEntity.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122178921+09:00", comments="Source field: sessions.user_id")
    public static final SqlColumn<Long> userId = sessionsEntity.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122277453+09:00", comments="Source field: sessions.created_at")
    public static final SqlColumn<LocalDateTime> createdAt = sessionsEntity.createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.122326459+09:00", comments="Source field: sessions.expires_at")
    public static final SqlColumn<LocalDateTime> expiresAt = sessionsEntity.expiresAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.121983328+09:00", comments="Source Table: sessions")
    public static final class SessionsEntity extends AliasableSqlTable<SessionsEntity> {
        public final SqlColumn<String> id = column("id", JDBCType.VARCHAR).withJavaProperty("id");

        public final SqlColumn<Long> userId = column("user_id", JDBCType.BIGINT).withJavaProperty("userId");

        public final SqlColumn<LocalDateTime> createdAt = column("created_at", JDBCType.TIMESTAMP).withJavaProperty("createdAt");

        public final SqlColumn<LocalDateTime> expiresAt = column("expires_at", JDBCType.TIMESTAMP).withJavaProperty("expiresAt");

        public SessionsEntity() {
            super("sessions", SessionsEntity::new);
        }
    }
}