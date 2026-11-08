# Jester Playground BACKEND application

簡単なJSONを返すだけのSpringBootアプリ。

## 認証

Backendで行う認証処理。認証はCognitoが行い、BACKENDアプリではCognitoで生成されたIDTokenを検証する。

issはこんな感じ。cognitoのユーザープールの URL.
https://cognito-idp.<region>.amazonaws.com/<user_pool_id>

audはこんな感じ。Cognito のクライアント ID
71ab3c4d5e6f7g8h9i0j1k2l3m

それぞれの値をパラメータストアに入れておいて、一致するかどうかを検証するだけのかんたんな作業です。
どちらも秘密情報ではありませんので、シークレットマネージャーではなく、パラメータストアに設定する。

### セッション管理の流れ。

#### セッションの開始

セッション開始API. frontendはCognitoによる認証後にこれを呼ぶ。
/api/v1/session/login

パラメータにあるIDTokenを取得。
IDTokenの検証を行う。JWTの検証を行う。iss=ユーザープールの URL, aud=クライアント ID であることを検証する

IDTokenの検証が失敗した場合、エラー。401
subでユーザーテーブルを検索する。存在しない場合、401.

HTTPセッションを無効にする。

HTTPセッションを生成する。getSession(true) で生成。
独自にIDを生成してもいいけど、別なIDを生成する理由も特にないので、セッションIDを使用する。

DBのセッションテーブルにHTTPセッションIDとsubを保存する（必要ならその他の情報を保存してもよい）。
SpringのSecurityContext, Principalに ユーザーID, sub, ロール(現在は空のリスト)を保存する。

レコードに無効になったセッションが残ってしまうので、以下のような方法でレコードを削除する。
大した量ではないので、ある程度無視してもいい気がします。その場合は、有効期限カラムを入れたほうがいいでしょう。

- 定期的に削除(MySQLイベントスケジューラー)
- Spring Session JDBCで管理
- TTL機能があるDBを使う。DynamoDBなど。

Cognito以外の認証を使うことを考慮し、Provider="Cognito", ProvidedUseId="sub"として保持する。

#### セッションの処理

セッションがあるかどうか Interceptor で検証する。

HTTPセッションIDを取得する。getSession(false)で新しいセッションを開始せずに現在のセッションIDを取得する。取得できない場合、エラー401

セッションIDでセッションテーブルを検索。存在しない場合、エラー。401

テーブルのレコードからsubを取得する。

subでユーザーテーブルを検索する。存在しない場合、401.

ユーザーテーブルからユーザー情報を取得し、ContextHolderに入れる。

Cognito以外の認証を使うことを考慮し、Provider="Cognito", ProvidedUseId="sub"として保持する。

## ログ

@slf4jを使用する。
例外が発生した場合、エラーメッセージを出力する。先頭に エラーID <prefix><UUID先頭8桁>を付与する。
エラーの原因を調査する際に、このエラーIDを使用してログを検索することができる。

エラーログは通常、GlobalExceptionHandlerで出力されるので、Controller, Serviceで出力する必要はない。
エラーレベルERROR(文字列"[ERROR]")をCloudWatchLogsに出力すると、通知される(未設定)。

ローカルではDocker上に出力される。何も設定していない場合、ログはいまでも残り続けるので、保存期間などの設定を行う(未設定)。

compose.yaml

```
services:
  backend:
    image: myapp
    logging:
      driver: json-file
      options:
        max-size: "50m"
        max-file: "5"
```

ECS上では直接CloudWatchLogsに出力する(未設定)。

```
{
  "containerDefinitions": [
    {
      "name": "backend",
      "image": "xxxxxxxx.dkr.ecr.ap-northeast-1.amazonaws.com/backend:latest",
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "/ecs/jester-backend",
          "awslogs-region": "ap-northeast-1",
          "awslogs-stream-prefix": "backend"
        }
      }
    }
  ]
}
```

## Controller

## Service

## Mapper, Repository

##　リクエスト

リクエストは一部の特別なものを除き、すべてPOSTとする。

### ヘッダ

特別な付与が必要なヘッダなし。
使用するメソッドは POST のみ。リクエストボディはJSONとする。
Controllerは正常(200)のみ返す。それ以外のレスポンスは例外をthrowし、グローがるエラーハンドラで返す。

## レスポンス

レスポンスボディの形式はJSONとする。

### ヘッダ

APIがつける特別なヘッダなし。

### ステータス

基本的にはこの型。ただし、CloudFrontやCognitoが返すものはこの形式にならない。
レスポンスはすべてJSON.

| HTTP ステータス | 意味                                 | UI の挙動                                    |
| --------------- | ------------------------------------ | -------------------------------------------- |
| 200             | 成功                                 | 通常処理                                     |
| 400             | 業務エラーまたはバリデーションエラー | 画面内でエラー表示（遷移なし）               |
| 401             | 未認証                               | ログインダイアログ表示                       |
| 403             | 未認可                               | 400と同じ                                    |
| 500             | システムエラー                       | システムエラー画面またはエラーダイアログ表示 |

### ボディ

共通部分。

```
{
  # APIの処理結果の詳細。
  # success, info, warning, error のどれか。
  # 画面の表示のみに使用する(MUIのAlertにあるseverityプロパティのような感じ)。
  # MUIを使うわけではないので、severityの種類は増やしてもよい。tailwindcssのseverityも増やしましょう。
  "severity" : "SUCCESS",
  # "画面に表示することを意図したメッセージ。必ず存在する。
  "message" : "成功しました。",
  # APIのデータ。severityがerrorでもdataはnullで存在する。
  "data" : {

  }
  # コンポーネントのヘルパーテキストなどに表示することを意図したメッセージ。
  # ヘルパーテキスト用のメッセージが存在しない場合、undefined.
  # fieldnameはAPIパラメータの名前と同じ(先頭の$.を省略したJSON path形式)。
  "invalid" : {
    "field": "user.address.zipcode",
    "message" : "画面表示を意図したメッセージ",
    "rejectedValue" : "リジェクトされた値"
  }
}
```

### 処理成功時のレスポンス。

ステータスコード 200.
\$.data はAPIにより異なる。

Controllerは、ApiResponse<T>を返す。

```
@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    // メッセージの重要度。SUCCESS, WARNINGなど
    private final Severity severity;
    // 画面表示を意図したメッセージ
    private final String message;
    // APIが返すデータ。存在しない場合、null
    private final T data;
    // バリデーションエラー時のメッセージ
    private final List<InvalidField> invalidFields;

}
```

```
@Getter
@RequiredArgsConstructor
public class InvalidField {
    // フィールド名。リクエストボディのJSON pathから先頭の $. を除いたもの
    private final String field;
    // 画面表示を意図したメッセージ
    private final String message;
    // 入力された値
    private final Object rejectedValue;
}
```

### 業務エラー、バリデーションエラー

レスポンスコード400
パラメータの変更などによってやり直すことにより回復可能なエラー。
severityはエラー内容により変更可能。
バリデーションエラーの場合、invalidにエラーのフィールド名とメッセージを格納する。
\$.nvalid.field[] の値は、パラメータのJSON Paht($.は省略)。

```
{
  "severity" : "ERROR",
  "message" : "エラーメッセージ",
  "data" : null,
  "InvalidFields" : [{
    "field": "user.address.zipcode",
    "message" : "画面表示を意図したメッセージ",
    "rejectedValue" : "リジェクトされた値"
  }]
}
```

### 認証エラーのレスポンス。

認証エラーの場合。Cognitoが返すエラーはこの形式にならないことに注意。
レスポンスコード401

```
{
  "severity" : "ERROR",
  "message" : "認証できませんでした。",
  "data" : null,
  "invalidFields" : []
}
```

### 認可エラーのレスポンス。

レスポンスコード 403.
メッセージは認可によっててきとうに変更しましょう。

```
{
  "severity" : "ERROR",
  "message" : "アクセスできません。",
  "data" : null,
  "invalidFields" : []

}
```

### 予期しない復旧できないエラー。ステータスコード 500

レスポンスコード500

メッセージは適当なハッシュ値（セッションIDと日時とか適当なものから生成する）。
目印に、先頭にEとかつけておくのもいいかもしれない。
画面表示のときに、ハッシュ値にいい感じのメッセージをつけて表示すること。
同じ値をCloudWatchLogにエラーメッセージとともに出力することで検索が簡単にできるようにする。

messageとエラーハッシュを分けることを考えたけど、サーバーが返すメッセージなんてe.getMessage()の値くらいしか入れるものがないし、そんなものを表示しても意味がない。
というわけで、この形式でいいか。

```
{
  "severity" : "ERROR",
  "message" : "E<ハッシュ値><エラーメッセージ>",
  "data" : null,
  "invalidFields" : []
}
```

## DB

DBにはMyBATISを使用します。
MyBatisのDynamicSQL方式を検討していましたが、メリットがないので、プロバイダベース方式を使用します。

- 直接SQLを書いて実装する場合がほとんど。わざわざDynamicSQLに置き換えるという手間をかける意味がない。
- 開発を行う場合は、検索部分は直接SQLを書くことが多い。アノテーションベースのSQL BuilderならDynamicSQLよりは置き換えが楽。
- DynamicSQLでは型チェックが働くというメリットもあるが、デメリットが上回る。
- そもそも型チェックが緩いSQLで厳密に型チェックをしたところであまり意味がないのでは？

### 自動生成

MyBATIS generatorを使い、基本的なCRUDクラスを自動生成します。
このクラスは、
jester-playground/backend/src/main/java/dev/enchander/rndevops/jester/playground/backend/domain/generated
に生成されます。

```
$ ./mvnw mybatis-generator:generate
```

### 運用

自動生成されたMapperは、Serviceクラスなどから直接使用することを禁止します。
jester-playground/backend/src/main/java/dev/enchander/rndevops/jester/playground/backend/domain/extension/repository
にRepositoryクラスを作成し、そのクラス経由でMapperを使用すること。

DynamicSQLを使わないので、この制約もあまり意味をもたなくなったけど、この制約はあるほうがいい。

RepositoryがDBにアクセスする方法は2つあります。
ひとつは自動生成されたmapperを使う方法。
もうひとつは、Mapperを自分で実装して使う方法です。
Mapperを自分で実装する場合は、Dynamic SQLを使うメリットがあまりないように思いますので、プロバイダベースのMapperを使うのがよいでしょう。

Mapper は DBアクセスのためのインターフェース。
Repository はドメインモデルのためのインターフェース。

となっていますが、プロバイダベースの場合はこの区別が曖昧になりがち。というか、もう同じものとしていいのではという気がします。
プロバイダベースのMapperを使う場合は、Repositoryを挟む必要はないので、Repositoryに直接Mapperを実装すること。

ただし、以下のような要件がある場合、Repositoryを挟むことで構造的メリットが得られます。
その場合でも、RepositoryからプロバイダベースのMapper（名前はRepositoryなので、わかりにくい）をAutowiredで注入して使うのがよいでしょう。

- 複数 mapper をまとめて 1 つの操作にしたい
- トランザクション境界を Repository に置きたい
- キャッシュやロギングを挟みたい
- 再生成の影響をRepositoryで吸収したい

実際にはトランザクション境界はService層に置くことが多いので、Repositoryを経由するメリットは少ないかもしれない。
ビジネスロジックが複雑でも、それをRepositoryに置くのは、Repositoryの責務を超えてしまう可能性があるため、慎重に行うこと。

プロバイダベースとなったので、ほぼそのまま直接SQLを書くことができます。
DynamicSQLを使った実装よりは格段に楽でしょう。
条件分岐などがある場合でも、DynamicSQLよりはSQL Builderを使った方が楽でしょう。

残っているめんどくさい手順は、SELECT からエンティティを生成する部分だけ。
seslect文を書いてしまえば、エンティティは機械的に生成するだけなので、うまく指示すればAIが生成してくれると思います。

エンティティ。
jester-playground/backend/src/main/java/dev/enchander/rndevops/jester/playground/backend/domain/extension/entity

RepositoryのDB操作メソッドの戻り値。ほとんどの場合、SELECT <この部分と同じ>

リポジトリ。jester-playground/backend/src/main/java/dev/enchander/rndevops/jester/playground/backend/domain/extension/repository
ここで実装するのは、以下のうちのいづれか。

- 自動生成Mapperのリポジトリ

  ```
  /**
  * generated/mapperを使うRepositoryのサンプル。
  */
  public class SampleGeneratedBaseRepository {

      @Autowired
      private UsersMapper usersMapper;

      public Optional<Users> findBySub(String sub) {

          /*
          * ここでgenerated.mapperを使用して DBアクセスを行う。
          * 複数 mapper をまとめて 1 つの操作にしたいとかあるかもしれないが、そんなことをするくらいなら、JOINでいい。
          *
          */

          return Optional.of(usersMapper.selectByPrimaryKey(sub));

      }
  }
  ```

- プロバイダベースのMapper実装

  ```
  /*
  * Repositiryに直接Mapperを実装してしまう場合のサンプル。
  */
  @Mapper
  public interface SampleProvidedBasedRepository {

      @SelectProvider(type = SampleProvider.class)
      public Optional<Users> validSession(String userId);

      class SampleProvider implements ProviderMethodResolver {

          public String validSession(String userId) {
              // SQLビルダーを使ってSQLを組み立てる。
              // return new SQL() {
              // {
              // SELECT("todo_id", "todo_title", "finished", "created_at");
              // FROM("todo");
              // WHERE("todo_id = #{userId}");
              // }
              // }.toString();
              // もしくは、SQLを文字列で返す。
              return "SELECT ...  WHERE id = #{userId}";
          }
      }
  }
  ```

- プロバイダベースのベースのMapper実装を使用したリポジトリ。
  リポジトリが２段階以上にならないように注意すること。
  つまり、ここで Autowired するリポジトリは、他のRepositoryをAutowiredしていないこと。
  ```
  /*
  * 他のMapperを使用し、 Repository内で処理をはさむ場合のサンプル。
  */
  @Mapper
  public class SampleRepository {

      @Autowired
      private SampleProvidedBasedRepository sampleProvidedBasedRepository;

      public Optional<Users> validSession(String userId) {
          /*
          * Repository内で直接Mapperを実装しない場合のサンプル。
          * 以下のような要件がある場合に使用する。
          * - 複数 mapper をまとめて 1 つの操作にしたい
          * - トランザクション境界を Repository に置きたい
          * - 複雑なビジネスロジックを Service に置きたくない
          * - 返却型を変換したい（DTO → Domain）
          * - キャッシュやロギングを挟みたい
          * - 再生成の影響をRepositoryで吸収したい
          */
          return sampleProvidedBasedRepository.validSession(userId);
      }
  }
  ```

## API 型定義 for Typescript

ControllerクラスにあるGetMapping, @PostMappingのパラメータ、戻り値の型からReactで使用可能なAPIの型定義ファイルを自動生成する。
JavaのControllerクラスと同じものをTypescriptで書くのはめんどくさいし、anyを使うわけにもいかないので、このようなものを作成してみました。
まだ改良するかもしれません（Map<K,V>対応など）。

Mavenのプラグインとして実行するようになっています。
ソースはtestにあります。

このソースの先頭に簡単な説明があります。

src/test/java/one/enchanter/rndevops/jester/playground/typegenerator/TsGeneratorMain.java

こんな感じで生成されます。これをfrontendのてきとうなディレクトリにコピーして使用してください。

```
// API type generator for TypeScript.
// Generated at 2026-11-05T14:57:14.126746183

export interface ApiResponse<T> {
  severity: Severity;
  message: string;
  data: T | null;
  invalidFields: InvalidField[];
}

export interface InvalidField {
  field: string;
  message: string;
  rejectedValue: any;
}

export type Severity = "SUCCESS" | "ERROR" | "WARN";

// --------
// one.enchanter.rndevops.jester.playground.backend.controller.HealthController
// GET:/api/v1/test
export interface TestRequest1 {
  name: string;
  testRequest2: TestRequest2;
}

export interface TestRequest2 {
  name: string;
}

export interface TestResponseData1 {
  name: string;
  map: any;
  list: string[];
  testResponseData2: TestResponseData2;
}

export interface TestResponseData2 {
  name: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.HealthController
// GET:/api/v1/health

// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/get
export interface ItemGetRequestBody {
  id: string;
}

export interface ItemGetResponseData {
  id: number;
  name: string;
  description: string;
  createdAt: string;
  username: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/list
export interface ItemListResponseData {
  items: ItemGetResponseData[];
}

export interface ItemGetResponseData {
  id: number;
  name: string;
  description: string;
  createdAt: string;
  username: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/register
export interface ItemRegisterRequestBody {
  name: string;
  description: string;
}

export interface ItemRegisterResponseData {
  id: number;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.ItemController
// POST:/api/v1/item/delete
export interface ItemDeleteRequestBody {
  id: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.UnauthSessionController
// POST:/api/v1/session/login
export interface SessionRequestBody {
  idToken: string;
}


// --------
// one.enchanter.rndevops.jester.playground.backend.controller.WhoamiController
// POST:/api/v1/whoami
export interface WhoamiResponseData {
  uid: string;
  email: string;
  name: string;
}
```
