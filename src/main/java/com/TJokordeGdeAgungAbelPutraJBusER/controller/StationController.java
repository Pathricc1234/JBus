package com.TJokordeGdeAgungAbelPutraJBusER.controller;

import com.TJokordeGdeAgungAbelPutraJBusER.Account;
import com.TJokordeGdeAgungAbelPutraJBusER.City;
import com.TJokordeGdeAgungAbelPutraJBusER.Station;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonAutowired;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonTable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController implements BasicGetController<Station> {
    public JsonTable<Station> stationTable;
    public StationController(){
        try {
            this.stationTable = new JsonTable<>(Station.class,"src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\station.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @JsonAutowired(value = Account.class, filepath = "src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\station.json")
    private Account account;
    @Override
    public JsonTable<Station> getJsonTable() {
        return stationTable;
    }

    //Add new Station
    @PostMapping("/create")
    public BaseResponse<Station> createStation(
            @RequestParam String stationName,
            @RequestParam String city,
            @RequestParam String address
    ) {
        try {
            // Validate parameters
            if (stationName.isBlank() || city.isBlank() || address.isBlank()) {
                return new BaseResponse<>(false, "Parameter values cannot be blank or null", null);
            }

            // Validate city as a valid enum value
            City cityEnum = City.valueOf(city.toUpperCase());

            // Create a new station using the provided details
            Station newStation = new Station(stationName, cityEnum, address);

            // Add the new station to the stationTable
            stationTable.add(newStation);

            //Success response message
            return new BaseResponse<>(true, "Station added successfully", newStation);
        } catch (IllegalArgumentException e) {
            // Handle invalid enum value
            return new BaseResponse<>(false, "Invalid city value", null);
        } catch (Exception e) {
            // Handle other unexpected errors
            return new BaseResponse<>(false, "An error occurred while adding the station", null);
        }
    }

    @GetMapping("/getAll")
    public List<Station> getAllStation(){
        return getJsonTable();
    }

}
