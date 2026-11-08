# パラメータストアとシークレット

Secrets Manager の値はAWS上のみで管理し、ローカルやGitには保存しない。
Parameter Store の値はローカルの設定ファイルを正とし、スクリプトでAWSへ投入する。

## プロパティファイル

アプリケーションは常に application-aws.properties を使用する。

application-aws.properties

```
spring.config.import=aws-parameterstore:/jester/backend/${ENV}/,aws-secretsmanager:/jester/backend/${ENV}/
```

SPRING_PROFILES_ACTIVE は環境によらず固定とする。

```
SPRING_PROFILES_ACTIVE=aws
```

実行環境の切り替えは ENV 環境変数で行う。

例:

```
ENV=dev
ENV=stg
ENV=prd
```

起動時に Spring Cloud AWS が以下を自動的に読み込む。

AWS Systems Manager Parameter Store
AWS Secrets Manager

## SecretsManager

シークレットは Secrets Manager 上のみで管理する。

ローカルファイルに保存しない
Gitにコミットしない
spring.config.import によって自動的に読み込まれる

シークレット名は以下とする。

```
/jester/backend/{ENV}/
```

例:

```
/jester/backend/dev/
/jester/backend/stg/
/jester/backend/prd/
```

## ParameterStore

アプリケーション設定値は Parameter Store に保存する。

Parameter Store の値は Git 管理される設定ファイルを正とするため、AWS コンソールから直接変更してはならない。

変更が必要な場合は設定ファイルを修正し、再投入すること。

## パラメータファイル

環境ごとの設定値は以下のファイルで管理する。

```
backend/parameters/parameterstore-<env>.properties
```

例:

```
backend/parameters/parameterstore-dev.properties
backend/parameters/parameterstore-stg.properties
backend/parameters/parameterstore-prd.properties
```

記述方法は通常の Spring Properties ファイルと同じ。

```

app.name=jester
log.level=INFO
```

## パラメータの投入

投入スクリプトは、プロパティファイルのキーに環境プレフィックスを付与して Parameter Store へ登録する。

```python
PREFIX = f"/jester/backend/{ENV}/"
```

例えば以下の設定:

```
app.name=jester
```

は、Parameter Store に次の名前で保存される。

```
/jester/backend/dev/app.name
```

必要ならprefixを指定する。スクリプトにハードコーディングされている。

```
PREFIX = f"/jester/backend/{ENV}/"
```

環境変数ENVを設定する。スクリプトを実行する。
環境別のパラメータがパラメータストアに保存される。

## スクリプトの実行

環境変数 BACKEND_ENV を指定してスクリプトを実行する。

```
ENV=dev python3 storeparameter.py
```

実行すると、対応する設定ファイルの内容が AWS Systems Manager Parameter Store に登録される。
