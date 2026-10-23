package one.enchanter.rndevops.jester.playground.backend.domain.extension.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.ItemsUserEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper.ItemsQueryMapper;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper.LastInsertIdMapper;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.ItemsEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.mapper.ItemsMapper;

/**
 * 
 * ItemsRepository
 */
@Component
@Slf4j
public class ItemsRepository {

    @Autowired
    private LastInsertIdMapper lastInsertIdMapper;

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsQueryMapper itemsQueryMapper;

    /**
     * 
     * @param id
     * @return
     */
    public ItemsUserEntity getItem(long id) {
        return itemsQueryMapper.getItem(id);
    }

    /**
     * itemを削除する
     * 
     * @param id
     * @return 削除結果。削除件数が1の場合、true.
     */
    public boolean deleteItem(long id) {
        int result = itemsMapper.deleteByPrimaryKey(id);
        return result != 0;
    }

    /**
     * 
     * @return
     */
    public List<ItemsUserEntity> getItems() {
        return itemsQueryMapper.getItems();
    }

    /**
     * itemを更新する。
     * 
     * @param id          更新するitem id.
     * @param name        更新する値。nullでnameを更新しない。
     * @param description 更新する値。nullでdescriptionを更新しない。
     * @return
     */
    public boolean updateItem(long id, String name, String description) {

        ItemsEntity row = new ItemsEntity();

        row.setId(id);
        row.setName(name);
        row.setUserSub(description);

        int result = itemsMapper.updateByPrimaryKeySelective(row);

        return result != 0;

    }

    /**
     * 
     * @param sub
     * @param name
     * @param description
     * @return
     */
    @Transactional
    public ItemsEntity registerItem(String sub, String name, String description) {

        ItemsEntity row = new ItemsEntity();

        row.setUserSub(sub);
        row.setName(name);
        row.setDescription(description);

        int result = itemsMapper.insert(row);

        if (result == 0) {
            return null;
        }

        Long newId = lastInsertIdMapper.selectLastInsertId();

        log.debug(row.toString());

        Optional<ItemsEntity> itemsEntity = itemsMapper.selectByPrimaryKey(newId);

        return itemsEntity.orElse(null);

    }

}
