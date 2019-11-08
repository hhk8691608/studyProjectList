package com.mark.dockerproject.service.business;

import com.mark.dockerproject.dao.ItemDao;
import com.mark.dockerproject.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;


    public Item getItemById(int id){
        Item item = itemDao.findItemById(id);
        return item;
    }


}
