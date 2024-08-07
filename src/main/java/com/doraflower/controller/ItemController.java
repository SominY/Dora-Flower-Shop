package com.doraflower.controller;

import com.doraflower.dto.ItemFormDTO;
import com.doraflower.dto.ItemSearchDTO;
import com.doraflower.entity.Item;
import com.doraflower.service.item.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Data
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDTO", new ItemFormDTO());
        return "item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String itemNew(@Valid ItemFormDTO itemFormDTO, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFiles") List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null){
            model.addAttribute("errorMessage", "The first product image is a required field.");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDTO, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "An error occurred during product registration.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    @GetMapping( "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {

        try {
            ItemFormDTO itemFormDTO = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDTO", itemFormDTO);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "The product does not exist.");
            model.addAttribute("itemFormDTO", new ItemFormDTO());
            return "item/itemForm";
        }

        return "item/itemModify";
    }

    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDTO itemFormDTO,
                             BindingResult bindingResult,
                             @RequestParam("itemImgFiles") List<MultipartFile> itemImgFileList,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "item/itemModify";
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null) {
            model.addAttribute("errorMessage", "The first product image is a required field.");
            return "item/itemModify";
        }

        try {
            itemService.updateItem(itemFormDTO, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occured during product registration.");
            return "item/itemForm";
        }

        return "redirect:/";

    }

    @GetMapping(value= {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDTO itemSearchDTO,
                             @PathVariable("page")Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDTO, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDTO", itemSearchDTO);
        model.addAttribute("maxPage", 10);

        return "item/itemMng";
    }

}
