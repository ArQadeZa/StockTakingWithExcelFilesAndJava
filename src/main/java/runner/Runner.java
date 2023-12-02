package runner;

import lombok.SneakyThrows;
import swing.mainForm.MainForm;
import swing.saveFileForm.SelectFileForm;
import utils.ExcelUtils.ExcelUtils;
import utils.saveFileData.SaveFileUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Runner {
    public static MainForm mainForm = new MainForm();
    public static  List<List<String>> listOfRows;

    @SneakyThrows
    public static void main(String[] args) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        //set up properties of the main form
        mainForm.setTitle("Karduart");
        mainForm.setLayout(new GridLayout(0, 1));
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets the close button of the frame
        mainForm.setVisible(true);

        //displays file picker panel if file is not selected
        if (!SaveFileUtilities.isFileSelected()) {
            mainForm.setContentPane(new SelectFileForm().getPanel1());
            mainForm.setSize(400, 300);
            SwingUtilities.updateComponentTreeUI(mainForm);
        }

        //main flow
        mainForm.setSize(900, 900);
        mainForm.setLocationRelativeTo(null);

        //read file into list
        listOfRows = ExcelUtils.readExcelFile();
        listOfRows.remove(0);

        //update the display
        mainForm.updateDisplay();

        //activate autosaves
        mainForm.saveFile();
    }
}
