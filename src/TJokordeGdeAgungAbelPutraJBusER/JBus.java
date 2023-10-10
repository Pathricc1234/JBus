package TJokordeGdeAgungAbelPutraJBusER;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class JBus {
    public static void main(String[] args) {
        String filepath = "C:\\Users\\ASUS\\OneDrive\\Documents\\Kuliah\\Tugas\\Semester 3\\Praktikum OOP\\CS\\Modul 1\\JBus\\data\\station.json";
        Gson gson = new Gson();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filepath));
            List<Station> stationjson = gson.fromJson(buffer, new TypeToken<List<Station>>() {}.getType());
            stationjson.forEach(e -> System.out.println(e.toString()));
            System.out.println();
            buffer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
