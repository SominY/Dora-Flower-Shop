package com.doraflower.service.item;

import com.doraflower.dto.ItemFormDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    Long saveItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList)
        throws Exception;

    ItemFormDTO getItemDtl(Long itemId);

    Long updateItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception;
}
