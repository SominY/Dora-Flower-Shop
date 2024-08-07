package com.doraflower.service.item;

import com.doraflower.dto.ItemFormDTO;
import com.doraflower.dto.ItemSearchDTO;
import com.doraflower.dto.MainItemDTO;
import com.doraflower.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    Long saveItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList)
        throws Exception;

    ItemFormDTO getItemDtl(Long itemId);

    Long updateItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception;

    // 상품 데이터 조회
    Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);

    // 메인 페이지 상품 데이터 조회
    Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);

}
