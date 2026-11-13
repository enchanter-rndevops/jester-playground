package one.enchanter.rndevops.jester.playground.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.ItemsUserEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.repository.ItemsRepository;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.ItemsEntity;

@Service
public class ItemService {

    @Autowired
    private ItemsRepository itemsRepository;

    /**
     * 
     * @param sub
     * @param name
     * @param description
     * @return
     */
    public ItemsEntity registerItem(Long userId, String name, String description) {
        return itemsRepository.registerItem(userId, name, description);
    }

    /**
     * 
     * @param id
     * @return
     */
    public boolean deleteItem(long id) {
        return itemsRepository.deleteItem(id);
    }

    /**
     * 
     * @param id
     * @param name
     * @param description
     * @return
     */
    public boolean updateItem(long id, String name, String description, long userId) {
        return itemsRepository.updateItem(id, name, description, userId);
    }

    /**
     * 
     * @param id
     * @return
     */
    public ItemsUserEntity getItem(long id) {
        return itemsRepository.getItem(id);
    }

    /**
     * 
     * @return
     */
    public List<ItemsUserEntity> getItemList() {
        return itemsRepository.getItems();
    }

}
