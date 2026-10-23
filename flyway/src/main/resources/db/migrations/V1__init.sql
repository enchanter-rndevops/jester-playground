
CREATE TABLE users (
    sub VARCHAR(36) NOT NULL COMMENT 'Cognitoが生成するsub',
    email VARCHAR(255) NOT NULL COMMENT 'メールアドレス',
    name VARCHAR(255) NOT NULL COMMENT '名前',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    PRIMARY KEY (sub)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ユーザー情報';

create table sessions (
    id VARCHAR(128) NOT NULL COMMENT 'セッションID',
    user_sub CHAR(36) NOT NULL COMMENT 'ユーザーのsub',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    expires_at TIMESTAMP NOT NULL COMMENT '有効期限',
    PRIMARY KEY (id),
    FOREIGN KEY (user_sub) REFERENCES users(sub)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='セッション管理';

create table items (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'アイテムID',
    user_sub CHAR(36) NOT NULL COMMENT 'ユーザーのsub',
    name VARCHAR(255) NOT NULL COMMENT 'アイテム名',
    description VARCHAR(1000) COMMENT 'アイテムの説明',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='アイテム情報';
