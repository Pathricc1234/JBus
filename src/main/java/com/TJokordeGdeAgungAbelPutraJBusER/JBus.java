package com.TJokordeGdeAgungAbelPutraJBusER;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonDBEngine;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.SpringApplication;

public class JBus {
    public static void main(String[] args) {
        JsonDBEngine.Run(JBus.class);
        SpringApplication.run(JBus.class,args);
        Runtime.getRuntime().addShutdownHook(new Thread(()-> JsonDBEngine.join()));
    }
}
