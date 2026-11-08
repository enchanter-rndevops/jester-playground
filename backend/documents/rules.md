# Backend コーディングルール

プロジェクト全体とこのルールを読み込ませることで、AIがController, Service, Repositoryのテストコードを生成。人がそれを検証し、必要に応じて追加、修正などを行うことでテストコードを完成させることを目的としています。

AIに読み込ませる部分のみ別ファイルに分割すること。

- AIが生成するもの
  - テストケース
  - Assert文
  - Mock設定
  - テストデータ生成

- 人間が決めるもの
  - テスト対象
  - テスト観点
  - 境界値
  - 例外ケース
  - ビジネス要件
  - テストケースの妥当性
  - テスト漏れ
  - ビジネス要件との整合性
  - Assert内容

AIが生成するテストは、Controller, Service, Repositoryのみ。listener, filterなどは対象外。

## Controller

- 使用するメソッドはPOSTのみ。
- レスポンスは直接ApiResponse<T>を返す。
- 特別なレスポンスが必要な場合のみResponseEntityを使用すること。
- エラーは例外をThrowすること。必要なら、exception handler に追加する。

サンプルコード

```

/**
 * ItemGetRequestBody
 * パラメータと戻り値をrecordで定義する。
 + パラメータは RequestBody を付ける。
 * レスポンスボディは、ResponseData を付ける。
 *
 * リクエストボディのほうには、アノテーションによるバリデーションを付ける。
 */
record ItemGetRequestBody(String id) {
}

record ItemGetResponseData(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        String username) {
}

/**
 *
 * ItemController
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/item")
public class ItemController {

    // 必要なServiceをDI.
    //  required = false があるのは、TypeScriptの型定義時にServiceがなくてもエラーにならないようにするため。
    @Autowired
    private ItemService itemService;

    /**
     * コメントをここに書く。パラメータと戻り値ははこのファイルの先頭当たりに定義したレコードを使用する。
     *
     */
    @PostMapping("/get")
    public ApiResponse<ItemGetResponseData> get(@RequestBody ItemGetRequestBody body) {
        log.info("ItemController.get() called");

        // 標準のバリデータでできないバリエーションがある場合、ここでバリデーションを行うことができる。

        var item = itemService.getItem(Long.parseLong(body.id()));

        if (item == null) {
            throw new NotFoundException("アイテムが存在ません。");
            // この例外をthrowすると、GlobalExceptionHandlerで以下のレスポンスと同じものを返すようになっています。
            // return new ApiResponse<ItemGetResponseData>(Severity.ERROR, "アイテムが存在しません。");
            // * Controllerでは（特別な要件がない場合）、戻り値に ResponseEntityを使わず、ApiResponse を直接使用することができる。
            // * Controllerはokだけを返すことでコードが見やすくなる。
        } else {
            // 正常の場合、ApiResponse<T>をそのまま返す。
            var responseData = new ItemGetResponseData(item.getId(), item.getName(), item.getDescription(),
                    item.getCreatedAt(), item.getUserName());
            return new ApiResponse<ItemGetResponseData>(Severity.SUCCESS, "OK", responseData);
        }
    }

}
```

## Controllerのテスト

- テスト対象はバリデーションとレスポンスボディ。
- ServiceはMock化する。
- @WebMvcTest を使用
- DBアクセス禁止
- MockMvcを利用
- JSONPathですべての項目を検証。
- 複数パターンがある場合や、undefined(もしあれば), nullなどの検証も行うこと。
- Controllerテストでは業務ロジックを確認しない
- テストケースは以下を検討すること
  - 正常系
  - 0件
  - NULL
  - 境界値
  - その他思いつくものをここに追加

```

@WebMvcTest(controllers = ItemController.class)
@ActiveProfiles("test") // テスト用のプロファイルを指定
@Import({ SecurityConfig.class })
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    private UserSessionRepository userSessionRepository;

    @MockitoBean
    private SessionValidationInterceptor sessionValidationInterceptor;

    /**
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    @DisplayName("正常系：アイテム取得APIが正しく動作すること")
    void testItemGet() throws Exception {

        when(sessionValidationInterceptor.preHandle(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any())).thenReturn(true);

         when(itemService.getItem(0))
                .thenReturn(new ItemsUserEntity(0L, "itemname", "description",
                        LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 2, 0, 0),
                        "user_sub",
                        "username"));

        String jsonBody = "{\"id\": 0}";

        ResultActions rs = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/item/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody));

        rs.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.severity").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invalidFields").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("username"));
    }

    // バリデーションエラーや存在しないidのテストケースを追加する。

}
```

## Serviceの実装

- Serviceから直接Mapperを使用しない。
- 必要に応じて @Transactional でトランザクションを制御する。

```
@Service
public class ItemService {

    /**
     * itemをidで取得
     * @param id
     * @return  item
     */
    public ItemsUserEntity getItem(long id) {
        // ここから直接Mapperを使用しないこと。
        // 独自に実装したMapperも使用しない。面倒でもRepositoryを作成すること。
        return itemsRepository.getItem(id);
    }
}
```

## Serviceのテスト

- テスト対象はServiceの処理のみ。RepositoryはMock化すること。
- Serviceに業務ロジックが存在する場合のみ作成する。
- 対象:
  - 条件分岐
  - 例外変換
  - 集計処理
  - DTO変換
  - 複数Repository連携
- 対象外:
  - Repository呼び出しのみ
  - 単なる委譲処理
  - setter/getter相当の処理

```
/**
 * ItemServiceは、Repositoryの結果を返すだけでロジックが存在しない。
 * このような薄いService層ではテストを行わないでよい。
 *
 */
@ActiveProfiles("test") // テスト用のpropertiesを読み込む。
public class ItemServiceTest {

    // 1. テスト対象のServiceをインスタンス化し、モックを注入する
    @InjectMocks
    private ItemService itemService;

    // 2. Serviceが依存しているRepositoryなどをモック化する
    @Mock
    private ItemsRepository itemsRepository;

    @Test
    @DisplayName("正常系：指定したIDのアイテムが正しく取得できること")
    void testGetItem_Success() {
        // Arrange (準備)
        long targetId = 1L;
        ItemsUserEntity mockEntity = new ItemsUserEntity(targetId, "itemname", "descripton",
                LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 2), "sub", "username");

        when(itemsRepository.getItem(targetId)).thenReturn(mockEntity);

        // 実行
        ItemsUserEntity result = itemService.getItem(targetId);

        // 検証
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(targetId);
        // ここに他のフィールドの検証も追加すること

        // Repositoryが想定通りに1回呼ばれたかも検証できる
        verify(itemsRepository, times(1)).getItem(targetId);
    }
}
```

## Repository, Mapper

Mapper実装のサンプル。
ここでSQLを組み立てる。
SQLに直接パラメータの値を埋め込むようなことは、まさかやりませんよね。

```
@Mapper
public interface ItemsQueryMapper {

    @SelectProvider(type = ItemsQueryProvider.class)
    public ItemsUserEntity getItem(@Param("id") Long id);

    class ItemsQueryProvider implements ProviderMethodResolver {

        public String getItem(@Param("id") Long id) {
            return """
                    select
                        items.id as id,
                        items.name as name,
                        items.description as description,
                        items.created_at as created_at,
                        items.updated_at as updated_at,
                        users.sub as sub,
                        users.name as user_name
                    from items
                    left outer join users on items.user_sub = users.sub
                    where
                        items.id = #{id}
                    """;
        }
    }
```

Repositoryのサンプル

```
@Component
@Slf4j
public class ItemsRepository {

    // insert時に自動採番IDを取得するのに使用する。
    // トランザクションが必要なことに注意。
    @Autowired
    private LastInsertIdMapper lastInsertIdMapper;

    // 使用するMapperをDI
    @Autowired
    private ItemsQueryMapper itemsQueryMapper;

    /**
     *
     * @param id
     * @return
     */
    public ItemsUserEntity getItem(long id) {
        // ここではMapperを呼ぶだけ。
        // 必要なら複数のMapperを呼び出してもいいような気がするけど、
        // そんなことをするくらいなら、joinをするとか、別な方法を考えたほうがいいと思います。
        return itemsQueryMapper.getItem(id);
    }
}
```

## Repository, Mapperのテスト

基本的にRepositoryのテストのみ行う。
Mapperは、Repositoryから呼ばれることで、テストを行っているとみなす。
MyBatisが自動生成するMapperはテスト対象外。

- 対象
  - SQL
  - JOIN
  - Mapper連携
  - 結果マッピング
- 環境
  - Testcontainers
  - MySQL
  - SpringBootTest
- データ作成
  - Mapperのinsertを利用可
  - 自動生成Mapperはテスト対象外
- 検証
  - Entityの全フィールドを確認
  - 正常系を必ず作成
  - 0件ケースも作成
- 対象外
  - MyBatis Generatorが生成したMapper
  - Entity

```
/**
 * Repositoryのテスト
 * テスト時にMysqlコンテナを自動的に作成し、マイグレーションを行う。あらかじめDBを用意しておく必要はない。
 * 当然ですが、DBの中身はないので、テスト前にテストデータを作成すること。
 *
 */
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
@Slf4j
public class ItemsRepositoryTest {

    @Container
    @ServiceConnection
    static MySQLContainer mysqlContainer = new MySQLContainer("mysql:8.4")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    //
    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsRepository itemsRepository;

    /*
     * testGetItem
     */

    @Test
    @DisplayName("正常系：IDを指定してアイテムが正しく取得できること")
    void testGetItem_Success() {
        // Arrange (準備：必要に応じてテストデータを事前挿入など)
        long targetId = 1L;

        // テストデータの投入。
        // jdbcTemplateを使用してinsertを書くこともできるが、insertを書くのはとてもしんどい。
        // Mydatisにより自動生成されたコードはテストの必要がないので、これを使用してinsertを行う。
        itemsMapper.insert(new ItemsEntity(1L, "sub", "name", "description",
                LocalDateTime.of(2000, 1, 1, 1, 1),
                LocalDateTime.of(2000, 1, 1, 1, 2)));

        // 実行
        ItemsUserEntity result = itemsRepository.getItem(targetId);

        // 検証
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(targetId);
        // 検証コードを追加すること。
    }
}

```
