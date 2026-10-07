import boto3
import os
import sys

# ================================
# 設定
# ================================
# ENV は CodeBuild の環境変数から取得（dev / stg / prod）
ENV = os.environ.get("ENV")
if not ENV:
    print("ERROR: ENV が指定されていません（dev / stg / prod）")
    sys.exit(1)

# prefix はあなたの構造に完全一致
PREFIX = f"/jester/backend/{ENV}/"

# properties ファイル名
PROPERTIES_FILE = f"parameterstore-{ENV}.properties"


# ================================
# properties ファイル読み込み
# ================================
def load_properties(path):
    props = {}
    try:
        with open(path) as f:
            for line in f:
                line = line.strip()
                if not line or line.startswith("#"):
                    continue
                if "=" not in line:
                    print(f"WARNING: 無効な行をスキップ: {line}")
                    continue
                key, value = line.split("=", 1)
                props[key.strip()] = value.strip()
        return props
    except FileNotFoundError:
        print(f"ERROR: properties ファイルが存在しません: {path}")
        sys.exit(1)


# ================================
# SecureString 判定
# ================================
def is_secure_key(key: str) -> bool:
    """
    spring.datasource.password など、
    パスワード・秘密鍵・トークン系は SecureString にする。
    """
    secure_keywords = ["password", "secret", "token", "key"]
    return any(k in key.lower() for k in secure_keywords)


# ================================
# Parameter Store へ投入
# ================================
def put_parameter(name: str, value: str, secure: bool):
    ssm = boto3.client("ssm")

    param_type = "SecureString" if secure else "String"

    try:
        ssm.put_parameter(
            Name=name,
            Value=value,
            Type=param_type,
            Overwrite=True
        )
        print(f"[OK] {name} ({param_type}) を更新しました")
    except Exception as e:
        print(f"[ERROR] {name} の更新に失敗しました: {e}")
        sys.exit(1)


# ================================
# メイン処理
# ================================
def main():
    print("====================================")
    print(f"Parameter Store 同期開始 ENV={ENV}")
    print("prefix:", PREFIX)
    print("properties:", PROPERTIES_FILE)
    print("====================================")

    props = load_properties(PROPERTIES_FILE)

    if not props:
        print("ERROR: properties が空です")
        sys.exit(1)

    for key, value in props.items():
        full_name = PREFIX + key  # spring.datasource.url のような .区切りのまま
        secure = is_secure_key(key)
        put_parameter(full_name, value, secure)

    print("====================================")
    print("Parameter Store 同期完了")
    print("====================================")


if __name__ == "__main__":
    main()
