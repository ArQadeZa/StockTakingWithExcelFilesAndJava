package utils.jsonSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import runner.Runner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class JsonSerializer {

    /**
     * Deserialize file and create objects from data
     * @return list of DataItem
     */
    public static List<DataItem> deserialize(){
        //read data from json file
        ObjectMapper objectMapper = new ObjectMapper();
        List<DataItem> dataItems;

        try {
            InputStream inputStream = Runner.class.getClassLoader().getResourceAsStream("Data.json");
//            dataItems  = objectMapper.readValue(new File("src/main/java/resources/Data.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, DataItem.class));
            dataItems  = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, DataItem.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataItems;
    }

    /**
     * Save data to json file
     * @param list - list that contains data to be saved
     * @return true or false based on if the file saved or not
     */
    public static boolean serialize(List<DataItem> list){
        try {
            OutputStream outputStream = Runner.class.getClassLoader().getResource("Data.json").openConnection().getOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.writeValue(new File("src/main/java/resources/Data.json"),list);
            objectMapper.writeValue(outputStream,list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
