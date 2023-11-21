package utils.saveFileData;

import lombok.SneakyThrows;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SaveFileUtilities {
    //save excel file location

    /**
     * Checks if the file location field is populated
     *
     * @return boolean
     */
    public static boolean isFileSelected() {
        Properties properties = new Properties();
        try {
            //checks if file is empty
            properties.load(new FileInputStream("src\\main\\java\\resources\\Resources.properties"));
            if (String.valueOf(properties.get("excelFileLocation")).isEmpty()) {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //returns true if file field is populated
        return true;
    }


    /**
     * Sets the location of the file in the resources property
     *
     * @param file - File location of the Excel file
     * @return true if data is saved , false if data could not be saved
     */
    public static boolean setFileLocation(File file) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\main\\java\\resources\\Resources.properties"));
            properties.setProperty("excelFileLocation", file.getAbsolutePath());
            properties.store(new FileOutputStream("src\\main\\java\\resources\\Resources.properties"), "");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File could not be saved please try again");
            return false;
        }
    }

    @SneakyThrows
    public static File getFileLocation() {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\main\\java\\resources\\Resources.properties"));
        return new File(properties.getProperty("excelFileLocation"));
    }
}

