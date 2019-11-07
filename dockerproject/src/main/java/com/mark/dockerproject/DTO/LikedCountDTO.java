package com.mark.dockerproject.DTO;

import lombok.Data;

@Data
public class LikedCountDTO {

    private int id;

    private String key;

    private Integer value;

    private int count;

    public LikedCountDTO(){}

    public LikedCountDTO(String key,Integer value){
        this.key = key;
        this.value = value;
        this.count = value;
        this.id = Integer.parseInt(key);
    }


}
