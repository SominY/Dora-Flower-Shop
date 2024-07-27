package com.doraflower.repository;

import com.doraflower.config.QueryDslConfig;
import com.doraflower.entity.Item;
import com.doraflower.entity.QItem;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@Log4j2
@Import(QueryDslConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        for (int i = 1; i <= 10; i++) {
            Item item = Item.builder()
                    .itemNm("flower ..." + (i % 10))
                    .price(10000)
                    .stockNum(i % 10)
                    .itemDetail("flower ..."+ (i % 10))
                    .build();
            Item result = itemRepository.save(item);

            log.info("BNO: " + result.getId());
        }

    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNm() {
        String sample = "flower ...1";
        List<Item> results = itemRepository.findByItemNm(sample);
        results.forEach(result -> log.info(result));
    }

    @Test
    @DisplayName("상품명 or 상품상세설명 테스트")
    public void findByItemNmOrItemDetail() {

        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("flower ...1", "flower ...2");
        itemList.forEach(item -> log.info(item));
    }

    @Test
    @DisplayName("특정 가격 이하 상품 조회")
    public void findByPriceLessThan() {

        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        itemList.forEach(item -> log.info(item));
    }

    @Test
    @DisplayName("가격 내림차순 조회")
    public void findByPriceLessThanOrderByPriceDesc() {

        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        itemList.forEach(item -> log.info(item));
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회")
    public void findByItemDetail() {
        List<Item> itemList = itemRepository.findByItemDetail("flower ...1");
        itemList.forEach(item -> log.info(item));
    }

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest() {

        // QItem 객체 생성
        QItem item = QItem.item;

        // 쿼리 작성
        JPQLQuery<Item> query = jpaQueryFactory.selectFrom(item)
                .where(item.itemNm.contains("flower"))
                .orderBy(item.id.desc());

        // 쿼리 실행
        List<Item> results = query.fetch();
        results.forEach(result -> log.info(result));

    }
}