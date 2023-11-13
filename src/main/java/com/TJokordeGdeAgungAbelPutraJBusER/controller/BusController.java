    package com.TJokordeGdeAgungAbelPutraJBusER.controller;

    import com.TJokordeGdeAgungAbelPutraJBusER.*;
    import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonAutowired;
    import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonTable;
    import org.springframework.web.bind.annotation.*;

    import java.io.IOException;
    import java.sql.Timestamp;
    import java.util.List;

    @RestController
    @RequestMapping("/bus")
    public class BusController implements BasicGetController<Bus> {
        public JsonTable<Bus> busTable;
        public BusController(){
            try {
                this.busTable = new JsonTable<>(Bus.class,"src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\bus.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @JsonAutowired(value = Account.class, filepath = "src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\bus.json")
        private Bus bus;
        @Override
        public JsonTable<Bus> getJsonTable() {
            return busTable;
        }
        @PostMapping("/create")
        public BaseResponse<Bus> create(
                @RequestParam int accountId,
                @RequestParam String name,
                @RequestParam int capacity,
                @RequestParam List<Facility> facilities,
                @RequestParam BusType busType,
                @RequestParam int price,
                @RequestParam int stationDepartureId,
                @RequestParam int stationArrivalId
        ) throws IOException {
            AccountController accountController = new AccountController();
            StationController stationController = new StationController();
            List<Account> allAcc = accountController.getJsonTable();
            List<Station> allStations = stationController.getJsonTable();
            boolean isAccExist = allAcc.stream().anyMatch(account -> account.id == accountId);
            boolean isCompExist = allAcc.stream().anyMatch(account -> account.company != null);
            boolean isDeptIdValid = allStations.stream().anyMatch(station -> station.id == stationDepartureId);
            boolean isArrIdValid = allStations.stream().anyMatch(station -> station.id == stationArrivalId);
            Station departureStation = allStations.stream()
                    .filter(station -> station.id == stationDepartureId)
                    .findFirst()
                    .orElse(null);

            Station arrivalStation = allStations.stream()
                    .filter(station -> station.id == stationArrivalId)
                    .findFirst()
                    .orElse(null);
            Price busPrice = new Price(price);
            if(isAccExist && isCompExist){
                if(isDeptIdValid && isArrIdValid){
                    Bus newBus = new Bus(name,facilities,busPrice,capacity,busType,departureStation,arrivalStation);
                    busTable.add(newBus);
                    try {
                        busTable.writeJson();;
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    return new BaseResponse<>(true,"Bus berhasil dibuat",newBus);
                }
                else{
                    return new BaseResponse<>(false,"Tidak ditemukan stasiun awal dan tujuan dengan id tersebut",null);
                }
            }
            return new BaseResponse<>(false,"Tidak ditemukan akun maupun akun renter",null);
        }

        @PostMapping("/addSchedule")
        public BaseResponse<Bus> addSchedule(@RequestParam int busId, @RequestParam String time) {
            Bus foundBus = null;
            boolean isBusExist = false;
            for(Bus bus : busTable){
                if(bus.id == busId){
                    foundBus = bus;
                    isBusExist = true;
                }
            }
            if(isBusExist){
                try {
                    Timestamp newSchedule = Timestamp.valueOf(time);
                    foundBus.addSchedule(newSchedule);
                    busTable.add(foundBus);
                    try {
                        busTable.writeJson();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    return new BaseResponse<>(true,"Jadwal berhasil ditambah",foundBus);
                }
                catch (IllegalArgumentException e){
                    return new BaseResponse<>(false,e.getMessage(),null);
                }
            }
            return new BaseResponse<>(false,"Akun Bus Tidak Ditemukan",null);
        }
    }
