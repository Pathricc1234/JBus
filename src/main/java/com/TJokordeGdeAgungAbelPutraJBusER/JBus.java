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

    public static Bus createBus() {
        Price price = new Price(750000, 5);
        Bus bus = new Bus("Netlab Bus", Facility.LUNCH, price, 25, BusType.REGULER, City.BANDUNG, new Station("Depok Terminal", City.DEPOK, "Jl. Margonda Raya"), new Station("Halte UI", City.JAKARTA, "Universitas Indonesia"));
        Timestamp timestamp = Timestamp.valueOf("2023-07-27 19:00:00");
        bus.addSchedule(timestamp);
        return bus;
    }

}
