# 開発用イメージ。
FROM public.ecr.aws/amazonlinux/amazonlinux:2023
RUN dnf install -y java-25-amazon-corretto-headless
RUN dnf install -y tar
RUN dnf install -y gzip

# 1. 必要なツール（curlなど）のインストール（必要に応じて）
#RUN apk add --no-cache curl

# 2. AWS RDSのグローバルルート証明書（PEM）をダウンロード
# ※AWSが提供している最新のバンドル証明書などを取得します
RUN curl -o /tmp/rds-ca-bundle.pem https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem

# 3. Javaのデフォルトcacertsパスを特定し、ダウンロードした証明書をインポート
# パスワードのデフォルトは "changeit" です
RUN keytool -importcert -noprompt \
    -alias rds-global-ca \
    -file /tmp/rds-ca-bundle.pem \
    -keystore /usr/lib/jvm/java-25-amazon-corretto/lib/security/cacerts \
    -storepass changeit

# 4. 一時ファイルの削除
RUN rm /tmp/rds-ca-bundle.pem

WORKDIR /app
#ENTRYPOINTがなくても開発中は問題ないので、ENTRYPOINTは書かない。
#ENTRYPOINT ["/bin/sh", "-c"]
