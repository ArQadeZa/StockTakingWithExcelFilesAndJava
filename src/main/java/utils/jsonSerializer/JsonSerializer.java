package utils.jsonSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import runner.Runner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonSerializer {
    private final static String filePath = System.getProperty("user.home") + "\\Documents\\karduart\\DoNotDelete\\";
    private final static String fileName = "data.json";

    /**
     * Deserialize file and create objects from data
     *
     * @return list of DataItem
     */
    public static List<DataItem> deserialize() {
        checkIfFileExist();

        //read data from json file
        ObjectMapper objectMapper = new ObjectMapper();
        List<DataItem> dataItems = new ArrayList<>();

        try {
            if (Files.size(Paths.get(filePath + fileName)) != 0) {
                InputStream inputStream = new FileInputStream(filePath + fileName);
                dataItems = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, DataItem.class));
            }
        } catch (JsonProcessingException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataItems;
    }

    /**
     * Save data to json file
     *
     * @param list - list that contains data to be saved
     * @return true or false based on if the file saved or not
     */
    public static boolean serialize(List<DataItem> list) {
        try {
            checkIfFileExist();
            try (OutputStream outputStream = new FileOutputStream(filePath + fileName);) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(outputStream, list);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }


    /**
     * Check if the data file exists in the users computer
     * if not the file gets created
     */
    @SneakyThrows
    private static void checkIfFileExist() {
        if (!new File(filePath + fileName).exists()) {
            Files.createDirectories(Path.of(filePath));
            Files.createFile(Paths.get(filePath + fileName));
        }
    }

}
