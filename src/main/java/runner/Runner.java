package runner;

import lombok.SneakyThrows;
import swing.mainForm.MainForm;
import utils.jsonSerializer.DataItem;
import utils.jsonSerializer.JsonSerializer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Runner {
    public static MainForm mainForm = new MainForm();
    public static List<DataItem> listOfRows;
    public static String startDate;
    public static String endDate;

    @SneakyThrows
    public static void main(String[] args) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        //set up properties of the main form
        mainForm.setTitle("Karduart");
        mainForm.setLayout(new GridLayout(1, 1));
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets the close button of the frame
        mainForm.setVisible(true);

        //main flow
        mainForm.setSize(900, 900);
        mainForm.setLocationRelativeTo(null);

        //read file into list
        listOfRows = JsonSerializer.deserialize();

        //update the display
        mainForm.updateDisplay();

        //activate autosaves
        mainForm.saveFile();

        //TODO: add functionality to select all items sold from specific date to another and generate a report on the evaluated values
    }

}
