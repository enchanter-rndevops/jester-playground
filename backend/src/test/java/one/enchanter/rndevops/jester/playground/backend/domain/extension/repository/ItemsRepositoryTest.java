package one.enchanter.rndevops.jester.playground.backend.domain.extension.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.ItemsUserEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.ItemsEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.ItemsMapper;

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

        // Act (実行)
        ItemsUserEntity result = itemsRepository.getItem(targetId);

        // Assert (検証)
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(targetId);
        // 検証コードを追加すること。
    }

    /*
     * testGetItems
     */
    @Test
    @DisplayName("正常系：IDを指定してアイテムのリストが正しく取得できること")
    void testGetItems_Success() {

        itemsMapper.insert(new ItemsEntity(1L, "sub1", "name1", "description1",
                LocalDateTime.of(2000, 1, 1, 1, 1),
                LocalDateTime.of(2000, 1, 1, 1, 2)));
        itemsMapper.insert(new ItemsEntity(2L, "sub2", "name2", "description2",
                LocalDateTime.of(2001, 1, 1, 1, 1),
                LocalDateTime.of(2001, 1, 1, 1, 2)));

        // Act (実行)
        List<ItemsUserEntity> result = itemsRepository.getItems();

        // Assert (検証)
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(result.get(1).getId()).isEqualTo(1);
        // 検証コードを追加すること。

    }

    /*
     * testDeleteItem
     */
    @Test
    @DisplayName("正常系：IDを指定してアイテムの削除が正しく行われること")
    void testDeleteItem_Success() {

        itemsMapper.insert(new ItemsEntity(1L, "sub1", "name1", "description1",
                LocalDateTime.of(2000, 1, 1, 1, 1),
                LocalDateTime.of(2000, 1, 1, 1, 2)));

        // Act (実行)
        boolean result = itemsRepository.deleteItem(1L);

        Assertions.assertThat(result).isTrue();
        // 検証コードを追加すること。

    }

    /*
     * testUpdateItem
     */
    @Test
    @DisplayName("正常系：IDを指定してアイテムの更新が正しく行われること")
    void testUpdateItem_Success() {

        itemsMapper.insert(new ItemsEntity(1L, "sub1", "name1", "description1",
                LocalDateTime.of(2000, 1, 1, 1, 1),
                LocalDateTime.of(2000, 1, 1, 1, 2)));

        // Act (実行)
        boolean result = itemsRepository.updateItem(1L, "name0", "description0");

        Assertions.assertThat(result).isTrue();

        Optional<ItemsEntity> except = itemsMapper.selectByPrimaryKey(1L);

        log.debug(except.toString());

        Assertions.assertThat(except.isPresent()).isTrue();

        // 検証コードを追加すること。

    }

    /**
     * testRegisterItem
     * 
     */
    @Test
    @DisplayName("正常系：IDを指定してアイテムの登録が正しく行われること")
    void testRegisterItem_Success() {

        ItemsEntity result = itemsRepository.registerItem("sub1", "name1", "description1");

        log.debug(result.toString());

        Optional<ItemsEntity> actual = itemsMapper.selectByPrimaryKey(result.getId());

        log.debug(actual.toString());

        Assertions.assertThat(actual.isPresent()).isTrue();

        // 検証コードを追加すること。

    }

}
