package com.doraflower.repository;

import com.doraflower.constant.ItemSellStatus;
import com.doraflower.entity.Item;
import com.doraflower.entity.Member;
import com.doraflower.entity.Order;
import com.doraflower.entity.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em;

    // 주문할 상품 생성
    public Item createItem() {

        Item item = Item.builder()
                .itemNm("test")
                .price(10000)
                .stockNum(100)
                .itemDetail("detail")
                .itemSellStatus(ItemSellStatus.SELL)
                .build();

        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {

        // Order 객체를 생성
        Order order = new Order();

        for (int i=0; i < 3; i++) {
            // 아이템 생성 및 저장
            Item item = this.createItem();
            itemRepository.save(item);

            // 주문 아이템 생성
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .order(order)
                    .orderPrice(1000)
                    .count(10)
                    .build();
            order.getOrderItems().add(orderItem);
        }

        // 주문 저장 및 플러시
        orderRepository.saveAndFlush(order);
        // 엔티티 매니저 초기화
        em.clear();

        // 저장된 주문 조회
        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);

        // savedOrder의 OrderItem 수가 3개인지 검증
        assertEquals(3, savedOrder.getOrderItems().size());
    }


    // 주문 데이터를 생성해서 저장
    public Order createOrder() {
        Order order = new Order();

        for(int i=0; i<3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .order(order)
                    .orderPrice(1000)
                    .count(10)
                    .build();

            order.getOrderItems().add(orderItem);
        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest() {

        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        em.flush();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("Order class: " +
                orderItem.getOrder().getClass());
        System.out.println("====================");
        orderItem.getOrder().getOrderDate();
        System.out.println("====================");
    }

}