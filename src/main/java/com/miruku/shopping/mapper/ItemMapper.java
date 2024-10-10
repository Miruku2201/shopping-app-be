package com.miruku.shopping.mapper;

import com.miruku.shopping.dto.request.ItemCreationRequest;
import com.miruku.shopping.dto.response.ItemResponse;
import com.miruku.shopping.entity.Item;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Item toItem(ItemCreationRequest request);

    ItemResponse toItemResponse(Item item);
}
