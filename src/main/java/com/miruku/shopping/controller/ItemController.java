package com.miruku.shopping.controller;

import com.miruku.shopping.dto.request.ItemCreationRequest;
import com.miruku.shopping.dto.response.ApiResponse;
import com.miruku.shopping.dto.response.ItemResponse;
import com.miruku.shopping.service.ItemService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ApiResponse<ItemResponse> createItem(@RequestBody ItemCreationRequest itemCreationRequest, HttpServletRequest httpServletRequest) throws ParseException, JOSEException {
        return ApiResponse.<ItemResponse>builder()
                .result(itemService.createItem(itemCreationRequest, httpServletRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ItemResponse>> getAllItems(){
        return ApiResponse.<List<ItemResponse>>builder()
                .result(itemService.getAllItems())
                .build();
    }

}
