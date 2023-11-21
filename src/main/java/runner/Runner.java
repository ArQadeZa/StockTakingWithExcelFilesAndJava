package runner;

import lombok.SneakyThrows;
import swing.mainForm.MainForm;
import swing.saveFileForm.SelectFileForm;
import utils.ExcelUtils.ExcelUtils;
import utils.saveFileData.SaveFileUtilities;

import javax.swing.*;

public class Runner {
   public static MainForm mainForm = new MainForm();

    @SneakyThrows
    public static void main(String[] args) {
        //set up properties of the main form

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        mainForm.setTitle("Karduart");
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets the close button of the frame
        mainForm.setVisible(true);

        //displays file picker panel if file is not selected
        if (!SaveFileUtilities.isFileSelected()) {
            mainForm.setContentPane(new SelectFileForm().getPanel1());
            mainForm.setSize(400, 300);
            SwingUtilities.updateComponentTreeUI(mainForm);
        }

        mainForm.setSize(900,900);
        ExcelUtils.readExcelFile().forEach(System.out::println);


    }
}
