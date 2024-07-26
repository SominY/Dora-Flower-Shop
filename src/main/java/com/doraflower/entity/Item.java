package com.doraflower.entity;

import com.doraflower.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;  // 상품 코드

    @Column(name = "item_name", nullable = false, length = 50)
    private String itemNm;  // 상품명

    @Column(name= "item_price", nullable = false)
    private int price;  // 가격

    @Column(name= "stock_number", nullable = false)
    private int stockNum;  // 재고수량

    @Lob
    @Column(name = "item_detail", nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

}
