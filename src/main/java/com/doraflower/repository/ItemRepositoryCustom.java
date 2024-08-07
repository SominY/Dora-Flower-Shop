package com.doraflower.repository;

import com.doraflower.dto.ItemSearchDTO;
import com.doraflower.dto.MainItemDTO;
import com.doraflower.entity.Item;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);

    Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
}
