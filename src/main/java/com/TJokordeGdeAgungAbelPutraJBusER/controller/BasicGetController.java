package com.TJokordeGdeAgungAbelPutraJBusER.controller;

import com.TJokordeGdeAgungAbelPutraJBusER.Algorithm;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonTable;
import com.TJokordeGdeAgungAbelPutraJBusER.Serializable;
import org.springframework.web.bind.annotation.*;
import java.util.*;

public interface BasicGetController <T extends Serializable> {

    JsonTable<T> getJsonTable();

    @GetMapping("/{id}")
    public default T getById(@PathVariable int id){
        return Algorithm.<T>find(getJsonTable(),a->a.id == id);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public default List<T> getPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize
    ){
        return Algorithm.<T>paginate(getJsonTable(),page,pageSize,a->true);
    }
}