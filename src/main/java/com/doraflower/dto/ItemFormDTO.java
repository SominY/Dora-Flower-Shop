package com.doraflower.dto;

import com.doraflower.constant.ItemSellStatus;
import com.doraflower.entity.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemFormDTO {

    private Long id;

    @NotBlank(message = "☑️ Item Name is required. ")
    private String itemNm;

    @NotNull(message = "☑️ Price is required. ")
    @Positive(message = "Price must be a positive number.")
    private Integer price;

    @NotBlank(message = "☑️ Item Detail is required. ")
    private String itemDetail;

    @NotNull(message = "☑️ Stock Number is required. ")
    @Min(value = 0, message = "☑️ Stock Number must be zero or positive.")
    private Integer stockNum;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDTO> itemImgDTOList = new ArrayList<>();

    // 수정 시에 아이디를 담아둘 용도
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // ItemFormDTO의 필드를 Item 엔티티로 매핑
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    // Item 엔티티를 ItemFormDTO로 매핑하는 정적 메서드
    public static ItemFormDTO of(Item item) {
        return modelMapper.map(item, ItemFormDTO.class);
    }
}
