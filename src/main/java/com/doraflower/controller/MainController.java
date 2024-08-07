package com.doraflower.controller;

import com.doraflower.dto.ItemSearchDTO;
import com.doraflower.dto.MainItemDTO;
import com.doraflower.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/")
    public String main(ItemSearchDTO itemSearchDTO,
                       Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<MainItemDTO> items = itemService.getMainItemPage(itemSearchDTO, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDTO", itemSearchDTO);
        model.addAttribute("maxPage", 9);

        return "main";
    }

}
