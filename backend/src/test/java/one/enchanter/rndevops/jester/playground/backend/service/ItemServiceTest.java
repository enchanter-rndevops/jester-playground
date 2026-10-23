package one.enchanter.rndevops.jester.playground.backend.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;

import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.ItemsUserEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.repository.ItemsRepository;

/**
 * ItemServiceは、REpositiryの結果を返すだけでロジックが存在しない。
 * このような薄いService層ではテストを行わない。
 * 
 */
@ActiveProfiles("test")
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

        // Act (実行)
        ItemsUserEntity result = itemService.getItem(targetId);

        // Assert (検証)
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(targetId);
        //

        // Repositoryが想定通りに1回呼ばれたかも検証できる
        verify(itemsRepository, times(1)).getItem(targetId);
    }

    @Test
    @DisplayName("異常系：存在しないIDの場合に例外がスローされること")
    void testGetItem_NotFound() {
        // Arrange (準備)
        long targetId = 999L;
        when(itemsRepository.getItem(targetId)).thenReturn(null);

        // Act & Assert (実行と例外の検証例)
        // ※ Service側で null チェック等のビジネスロジックを追加した想定
        // assertThatThrownBy(() -> itemService.getItem(targetId))
        // .isInstanceOf(ResourceNotFoundException.class);
    }
}