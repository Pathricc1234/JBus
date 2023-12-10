package com.TJokordeGdeAgungAbelPutraJBusER;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonDBEngine;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.SpringApplication;

/**
 * Class utama dari program menghandle backend
 */
public class JBus {
    public static void main(String[] args){
        JsonDBEngine.Run(JBus.class);
        SpringApplication.run(JBus.class,args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> JsonDBEngine.join()));
    }
}
