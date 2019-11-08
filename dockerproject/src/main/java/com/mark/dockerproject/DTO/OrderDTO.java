package com.mark.dockerproject.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDTO implements Serializable {

    private String token;
    private String price;
    private List<ItemDto> itemDtoList;

}
