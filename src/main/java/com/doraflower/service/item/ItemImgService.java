package com.doraflower.service.item;

import com.doraflower.entity.ItemImg;
import org.springframework.web.multipart.MultipartFile;

public interface ItemImgService {

    void saveItemImg(ItemImg itemImg, MultipartFile multipartFile)
            throws Exception;

    void updateItemImg(Long itemImgId, MultipartFile multipartFile) throws Exception;
}
