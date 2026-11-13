
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '内部ユーザーID',
    uid VARCHAR(26) NOT NULL UNIQUE COMMENT '外部公開ユーザーID',
    name VARCHAR(255) COMMENT 'ユーザー名',
    email VARCHAR(255) UNIQUE COMMENT 'メールアドレス',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ユーザー基本情報';

-- 2. 外部認証（Cognitoやソーシャルログイン）との紐付けテーブル
CREATE TABLE user_identities (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT 'usersテーブルのid',
    provider VARCHAR(50) NOT NULL COMMENT '例: cognito, google',
    provided_user_id VARCHAR(255) NOT NULL COMMENT 'Cognitoのsubや各プロバイダの固有ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_provider_user (provider, provided_user_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='外部認証プロバイダ連携';

create table sessions (
    id VARCHAR(128) NOT NULL COMMENT 'セッションID',
    user_id BIGINT NOT NULL COMMENT 'usersテーブルのid',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    expires_at TIMESTAMP NOT NULL COMMENT '有効期限',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='セッション管理';

create table items (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'アイテムID',
    user_id BIGINT NOT NULL COMMENT 'usersテーブルのid',
    name VARCHAR(255) NOT NULL COMMENT 'アイテム名',
    description VARCHAR(1000) COMMENT 'アイテムの説明',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='アイテム情報';
