# AWS上のRDSにIAM認証で接続する

開発中はSQLツールでAWS上のRDSに接続したくなる方もいるのではないでしょうか。
ポートフォワードを使用し、IAM認証で接続することで、パスワードをローカルに保存することなく安全に接続できるようにします。

RDSのパスワードをローカルに保存しておくのはセキュリティ的なリスクになります。
以前はソースコード管理をローカル環境だけで行うことも珍しくありませんでした。
しかし現在はGitHubなどの外部サービスを利用することが一般的になり、IDEや各種ツールのプラグインも増えています。
IAM認証を使用し、パスワードをローカルに保存しないようにすることで、誤ってパスワードをコミットしてしまうリスクや、悪意のあるプラグインやマルウェアによる情報窃取を防ぐことができます。

AWS JDBC Driverを使用し、IAM認証をドライバー側で行うようにするつもりでしたが、うまくいかなかったので、トークンを生成し、パスワードに設定する方式にしています。

WSL上でAWS Cli v2, SSMポートフォーワードが使えるようになっていること。

RDSはMySQLを使用しています。
ここでは、DBeaverの設定を行っています。パスワードを生成したトークンにするだけなので、ほかのツールでも接続することができると思います。

## MySQLにIAMユーザー作成

踏み台へ接続(adminはポートフォワードで接続できません)。

```
$ aws ssm start-session --target <踏み台EC2インスタンスのID>
```

踏み台からadminでMySQLへ接続。

```
$ mysql -h <RDSエンドポイント> -u admin -p
```

MySQLユーザー作成

```
> CREATE USER '<IAMユーザー>'@'%' IDENTIFIED WITH AWSAuthenticationPlugin AS 'RDS';
> CREATE DATABASE <データベース名>
> GRANT ALL PRIVILEGES ON <データベース名>.* TO '<IAMユーザー>'@'%';
> FLUSH PRIVILEGES;
```

## 準備

トークン生成スクリプトを作成する。
このスクリプトで、トークンをクリップボードにコピーし、標準出力にも出力する。
--hostname や -- username は環境にあわせて変更すること。

~/.local/bin/rdstoken

```
TOKEN=$(aws rds generate-db-auth-token \
--hostname <RDSのエンドポイントURL> \
--port <RDSのエンドポイントポート> \
--region ap-northeast-1 \
--username <IAM認証を設定したRDSのユーザー> \
)
echo "$TOKEN" | clip.exe
echo "$TOKEN"
```

アジアパシフィック (東京)の証明書バンドル(PEM)をダウンロードする。
https://docs.aws.amazon.com/ja_jp/AmazonRDS/latest/UserGuide/UsingWithRDS.SSL.html

## DBeaver

Javaで作成されているので、環境を選ばないツール。
A5:SQL Mk-2よりもこちらのほうが操作しやすいように思う。

wingetを使ってインストールするのがおすすめですが、アップデートをアプリから行うと、wingetでインストールされたところと別なとろこに新規インストールされてしまう？

### 接続設定を作成する

接続を作成。MySQLを選択。

- 一般

ホスト: localhost
ポート: 13306(ポートフォワードのポート番号)
データベース: <DB名>

- SSL

CA Certificart: ダウンロードした証明書バンドル (PEM)
Require SSL ☑
Verify server Certificart : ☑

### 接続する

RDS用のポートフォワードを開始。
作成した接続からDBに接続する。
作成したスクリプト wsl.exe /home/sin/.local/bin/rdstoken を実行。 生成されたトークンをパスワードにペーストし、接続。

## A5:SQL Mk2

Windowsではわりとスタンダードなツール。wingetを使ってインストールするのがおすすめ。
昔からあるツールなので、UIや操作が今風でなく、ちょっとつかいにくさを感じる。

### 接続設定を作成する

データベースの追加と削除から追加。MySQL MariaDB(直接接続)をクリックし、接続を作成する。

- 基本タブ

ホスト名: localhost
ポート: 13306(SSMポートフォワードのポート番号)
ユーザー:iam認証を設定したRDSユーザー
パスワードは設定しない

- SSLタブ

Cert : ダウンロードした証明書バンドル (PEM)

☑ SSLを有効にする
☑ サーバー証明書を信用する

- その他タブ

接続時にパスワードの設定を自動て行うようにするため、スクリプトを実行する。
このスクリプトででトークンがクリップボードにコピーされ、接続時に使用される。

```
wsl.exe /home/sin/.local/bin/rdstoken
```

### 接続する

RDS用のポートフォワードを開始。
作成した接続からDBに接続する。
