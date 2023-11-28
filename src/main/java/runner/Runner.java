package runner;

import lombok.SneakyThrows;
import swing.dataPanelTemplate.DataPanelTemplate;
import swing.mainForm.MainForm;
import swing.saveFileForm.SelectFileForm;
import utils.ExcelUtils.ExcelUtils;
import utils.saveFileData.SaveFileUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Runner {
    public static MainForm mainForm = new MainForm();

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
        List<List<String>> listOfRows = ExcelUtils.readExcelFile();
        listOfRows.remove(0);

        //create a main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create a scroll pane and add the mainPanel to it
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);


        mainForm.setContentPane(scrollPane);

        for (int i = 0; i < listOfRows.size(); i++) {
            //create a data panel
            DataPanelTemplate template = new DataPanelTemplate();

            //set data for Data panel template
            template.setVisible(true);
            template.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            //populate the fields with the data
            template.getTxtCode().setText(listOfRows.get(i).get(0));
            template.getTxtColour().setText(listOfRows.get(i).get(1));
            template.getTxtSellPrice().setText(listOfRows.get(i).get(3));
            template.getTxtCostToProduce().setText(listOfRows.get(i).get(4));
            template.getTxtQuantity().setText(listOfRows.get(i).get(5));
            template.getTxtDescription().setText(listOfRows.get(i).get(2));



            mainPanel.add(template);
            mainForm.pack();
        }
        mainForm.pack();
    }
}
