package com.mark.dockerproject.model;

import lombok.Data;

@Data
public class Order {

    private int id;
    private String price;
    private String orderNo;
    private String supplierId;

}
