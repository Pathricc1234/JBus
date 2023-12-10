        package com.TJokordeGdeAgungAbelPutraJBusER.controller;

        import com.TJokordeGdeAgungAbelPutraJBusER.*;
        import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonAutowired;
        import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonTable;
        import org.springframework.web.bind.annotation.*;

        import java.io.IOException;
        import java.sql.Timestamp;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;
        import java.util.Map;

        /**
         * BusController untuk melakukan backend yang berhubungan dengan class {@link Bus} bus
         * @author Tjokorde Gde Agung Abel Putra
         * @version 1.0
         */
        @RestController
        @RequestMapping("/bus")
        public class BusController implements BasicGetController<Bus> {
            public JsonTable<Bus> busTable;

            /**
             * Mendapatkan semua data pada bus Json dan meyimpan di busTable
             */
            public BusController() {
                try {
                    this.busTable = new JsonTable<>(Bus.class, "src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\bus.json");
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

            /**
             * Membuat bus
             * @param accountId ID dari akun pemilik bus
             * @param name nama dari bus
             * @param capacity kapasitas bus
             * @param facility fasilitas bus
             * @param busType tipe bus
             * @param price harga tiket bus per satu kursi
             * @param stationDepartureId id dari stasiun keberangkatan
             * @param stationArrivalId id dari stasiun kedatangan
             * @return Bus class yang disimpan di JSON
             * @throws IOException jika terjadi kegagalan
             */
            @PostMapping("/create")
            public BaseResponse<Bus> create(
                    @RequestParam int accountId,
                    @RequestParam String name,
                    @RequestParam int capacity,
                    @RequestParam List<Facility> facility,
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
                if (isAccExist && isCompExist) {
                    if (isDeptIdValid && isArrIdValid) {
                        Bus newBus = new Bus(accountId, name, facility, busPrice, capacity, busType, departureStation, arrivalStation);
                        busTable.add(newBus);
                        try {
                            busTable.writeJson();
                            ;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return new BaseResponse<>(true, "Bus berhasil dibuat", newBus);
                    } else {
                        return new BaseResponse<>(false, "Tidak ditemukan stasiun awal dan tujuan dengan id tersebut", null);
                    }
                }
                return new BaseResponse<>(false, "Tidak ditemukan akun maupun akun renter", null);
            }

            /**
             * Menambah jadwal pada bus
             * @param busId ID dari bus
             * @param time waktu keberangkatan
             * @return jadwal baru dengan informasi kursi
             */
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
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        java.util.Date parsedDate = dateFormat.parse(time);
                        Timestamp newSchedule = new Timestamp(parsedDate.getTime());
                        foundBus.addSchedule(newSchedule);
                        try {
                            busTable.writeJson();
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                        return new BaseResponse<>(true,"Jadwal berhasil ditambah",foundBus);
                    }
                    catch (IllegalArgumentException | ParseException e){
                        return new BaseResponse<>(false,e.getMessage(),null);
                    }
                }
                return new BaseResponse<>(false,"Akun Bus Tidak Ditemukan",null);
            }

            /**
             * Mereservasi kursi pada bus
             * @param busId ID dari bus
             * @param time waktu keberangkatan
             * @param busSeats tempat duduk yang dipilih
             * @return Bus class dengan seatAvailability pada schedule yang berubah
             */
            @PostMapping("/setSeatMapping")
            BaseResponse<Bus> setSeatMapping(@RequestParam int busId, @RequestParam Timestamp time ,@RequestParam List<String> busSeats){
                Map<String, Boolean> modifySeat = null;
                Bus foundBus = busTable.stream()
                        .filter(bus -> bus.id == busId)
                        .findFirst()
                        .orElse(null);
                assert foundBus != null;
                for(Schedule schedule : foundBus.schedules){
                    if(schedule.departureSchedule.equals(time)){
                        modifySeat = schedule.seatAvailability;
                        break;
                    }
                }
                assert modifySeat != null;
                for (Map.Entry<String,Boolean> seats : modifySeat.entrySet()) {
                    for(String seatCode : busSeats){
                        if(seats.getKey().equals(seatCode)){
                            seats.setValue(false);
                            break;
                        }
                    }
                }
                try {
                    busTable.writeJson();
                    return new BaseResponse<>(true,"Kursi Berhasil Di Set",foundBus);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            /**
             * Mendapatkan info bus berdasarkan akun pemiliki
             * @param accountId ID dari akun pemiliki bus
             * @return informasi dari semua Bus yang dimiliki akun
             */
            @GetMapping("/getMyBus")
            public List<Bus> getMyBus(@RequestParam int accountId) {
                return Algorithm.<Bus>collect(getJsonTable(), b -> b.accountId == accountId);
            }

            /**
             * Mendapatkan info semua bus
             * @return semua bus pada JSON
             */
            @GetMapping("/getAllBus")
            public List<Bus> getAllBus() {
                return getJsonTable();
            }

            /**
             * Mendapatkan informasi pada jadwal tertentu
             * @param busId ID dari bus
             * @param time waktu dari jadwal yang ingin dicek
             * @return Map dari jadwal dan bus dipilih
             */
            @GetMapping("/busSeatInfo")
            BaseResponse<Map<String,Boolean>> busSeatInfo(@RequestParam int busId, @RequestParam Timestamp time){
                Bus foundBus = busTable.stream()
                        .filter(bus -> bus.id == busId)
                        .findFirst()
                        .orElse(null);
                if(foundBus != null){
                    for(Schedule schedule : foundBus.schedules){
                        if(schedule.departureSchedule.equals(time)){
                            return new BaseResponse<>(true,"Schedule",schedule.seatAvailability);
                        }
                    }
                    return new BaseResponse<>(false,"No Schedule",null);
                }
                return new BaseResponse<>(false,"No Bus",null);
            }

            /**
             * Mendapatkan semua schedule pada Bus
             * @param busId ID dari bus yang dipilih
             * @return semua schedule pada bus yang dipilih
             */
            @GetMapping("/getAllSchedule")
            List<Schedule> getAllSchedule(@RequestParam int busId){
                Bus foundBus = busTable.stream()
                        .filter(bus -> bus.id == busId)
                        .findFirst()
                        .orElse(null);
                if(foundBus != null){
                    return foundBus.schedules;
                }
                return null;
            }

            /**
             * Mendapatkan info berdasarkan ID bus
             * @param busId ID dari bus
             * @return semua informasi dari bus yang ingin diakses, jika bus tidak ada dalam database maka akan null
             */
            @GetMapping("/getBusInfo")
            BaseResponse<Bus> getBusInfo(@RequestParam int busId) {
                Bus foundBus = null;
                for (Bus bus : getJsonTable()) {
                    if (bus.getId() == busId) {
                        foundBus = bus;
                        break;
                    }
                }
                if (foundBus != null) {
                    return new BaseResponse<>(true, "Bus found", foundBus);
                } else {
                    return new BaseResponse<>(false, "Bus not found", null);
                }
            }

            /**
             * Melakukan filter pada daftar bus
             * @param stationDepartureId statsiun keberangkatan yang dipilih
             * @param stationArrivalId stasiun kedatangan yang dipilih
             * @return semua bis yang memilki id stasiun asal dan tujuan
             */
            @GetMapping("/filter")
            List<Bus> filterBus(@RequestParam int stationDepartureId, @RequestParam int stationArrivalId){
                List<Bus> filterBusses = new ArrayList<>();
                StationController stationController = new StationController();
                List<Station> allStations = stationController.getJsonTable();
                Station departureStation = allStations.stream()
                        .filter(station -> station.id == stationDepartureId)
                        .findFirst()
                        .orElse(null);

                Station arrivalStation = allStations.stream()
                        .filter(station -> station.id == stationArrivalId)
                        .findFirst()
                        .orElse(null);
                assert departureStation != null;
                assert arrivalStation != null;
                for(Bus busList : getJsonTable()){
                    if(busList.departure.stationName.equals(departureStation.stationName)) {
                        if (busList.arrival.stationName.equals(arrivalStation.stationName)) {
                            filterBusses.add(busList);
                        }
                    }
                }
                return filterBusses;
            }

            /**
             * Menghapus salah satu bus
             * @param busId ID dari bus yang ingin dipilih
             * @return menghapus bus yang sesuai pada database
             */
            @DeleteMapping("/removeBus")
            public BaseResponse<Bus> deleteBus(@RequestParam int busId) {
                Bus foundBus = busTable.stream()
                        .filter(bus -> bus.id == busId)
                        .findFirst()
                        .orElse(null);
                busTable.remove(foundBus);
                try {
                    busTable.writeJson();
                    return new BaseResponse<>(true, "Bus Berhasil Dihapus", null);
                } catch (IOException e) {
                    e.printStackTrace();
                    return new BaseResponse<>(false, "Error writing changes to JSON", null);
                }
            }
        }
