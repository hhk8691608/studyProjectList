package com.mark.dockerproject.DTO;

import com.mark.dockerproject.model.Item;
import lombok.Data;

import java.io.Serializable;

@Data
public class ItemDto implements Serializable {

    private int id;
    private String name;
    private String price;

    public ItemDto(){}

    public ItemDto(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
    }


}