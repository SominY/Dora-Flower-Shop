package com.doraflower.service.item;

import com.doraflower.dto.ItemFormDTO;
import com.doraflower.dto.ItemImgDTO;
import com.doraflower.entity.Item;
import com.doraflower.entity.ItemImg;
import com.doraflower.repository.ItemImgRepository;
import com.doraflower.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgRepository itemImgRepository;

    private final ItemImgService itemImgService;


    @Override
    public Long saveItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList)
            throws Exception {

        //상품 등록
        Item item = itemFormDTO.createItem();
        itemRepository.save(item);

        //이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0)
                itemImg.setRepImgYn("Y");
            else
                itemImg.setRepImgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();

    }

    @Override
    @Transactional(readOnly = true)
    public ItemFormDTO getItemDtl(Long itemId) {

        // 해당 상품 이미지 조회
        List<ItemImg> itemImgList =
                itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        // 조회한 ItemImg 엔티티를 ItemImgDTO 객체로 만들어 리스트 추가
        List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDTO itemImgDTO = ItemImgDTO.of(itemImg);
            itemImgDTOList.add(itemImgDTO);
        }

        // 상품 엔티티 조회
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDTO itemFormDTO = ItemFormDTO.of(item);
        itemFormDTO.setItemImgDTOList(itemImgDTOList);

        return itemFormDTO;
    }

    @Override
    public Long updateItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList)
            throws Exception {

        // 상품 수정
        Item item = itemRepository.findById(itemFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDTO);

        List<Long> itemImgIds = itemFormDTO.getItemImgIds();

        // 이미지 등록
        for (int i=0; i<itemImgIds.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();
    }
}
