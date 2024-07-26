package com.doraflower.repository;

import com.doraflower.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemNm(String itemNm); // 상품명으로 데이터 조회

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail); // 상품명 or 상품디테일로 데이터 조회

    List<Item> findByPriceLessThan(Integer price); // 특정 가격 이하 상품 조회

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price); // 가격 내림차순 조회

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
}
