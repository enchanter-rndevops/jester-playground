package one.enchanter.rndevops.jester.playground.backend.domain.extension.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.ItemsUserEntity;

@Mapper
public interface ItemsQueryMapper {

    @SelectProvider(type = ItemsQueryProvider.class)
    public ItemsUserEntity getItem(@Param("id") Long id);

    @SelectProvider(type = ItemsQueryProvider.class)
    public List<ItemsUserEntity> getItems();

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

        public String getItems() {
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
                    order by created_at asc
                    """;
        }
    }

}
