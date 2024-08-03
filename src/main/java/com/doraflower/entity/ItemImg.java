package com.doraflower.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item_img")
@Data
public class ItemImg extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_id")
    private Long id;

    @Column(name = "image_name", nullable = false)
    private String imgName;  // 이미지 파일명

    @Column(name = "original_name")
    private String oriImgName;  // 원본 이미지 파일명

    @Column(name = "image_url")
    private String imgUrl;  // 이미지 조회 경로

    @Column(name = "represent_img")
    private String repImgYn;  // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {

        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
