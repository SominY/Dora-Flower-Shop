package com.doraflower.dto;

import com.doraflower.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDTO {

    private Long id;

    private String itemNm;

    private int price;

    private int stockNum;

    private String itemDetail;

    private ItemSellStatus itemSellStatus;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
