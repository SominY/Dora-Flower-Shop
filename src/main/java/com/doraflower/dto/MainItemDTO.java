package com.doraflower.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MainItemDTO {

    private Long id;

    private String itemNm;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    @QueryProjection
    public MainItemDTO(Long id, String itemNm, String itemDetail,
                       String imgUrl, Integer price) {

        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;

    }
}
