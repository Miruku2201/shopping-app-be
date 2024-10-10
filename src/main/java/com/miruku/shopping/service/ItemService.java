package com.miruku.shopping.service;

import com.miruku.shopping.dto.request.ItemCreationRequest;
import com.miruku.shopping.dto.response.ItemResponse;
import com.miruku.shopping.entity.Item;
import com.miruku.shopping.mapper.ItemMapper;
import com.miruku.shopping.repository.ItemRepository;
//import com.miruku.shopping.util.TokenValidatorUtil;
import com.miruku.shopping.util.TokenValidatorUtil;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;
//    private final TokenValidatorUtil tokenValidatorUtil = new TokenValidatorUtil();

    @Autowired
    private TokenValidatorUtil tokenValidatorUtil;
    public ItemResponse createItem(ItemCreationRequest itemCreationRequest,
                                   HttpServletRequest httpServletRequest) throws ParseException, JOSEException {
        String userIdCreated = tokenValidatorUtil.extractUserIdFromToken(httpServletRequest);
        Item item = itemMapper.toItem(itemCreationRequest);
        item.setUserCreatedId(userIdCreated);
        return itemMapper.toItemResponse(itemRepository.save(item));
    }

    public List<ItemResponse> getAllItems(){
        List<Item> itemList = itemRepository.findAll();
        return itemList.stream()
                .map(itemMapper::toItemResponse)
                .collect(Collectors.toList());
    }
}
