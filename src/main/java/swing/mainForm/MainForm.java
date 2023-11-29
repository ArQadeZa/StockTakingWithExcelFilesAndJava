package swing.mainForm;

import lombok.Getter;
import runner.Runner;
import swing.dataPanelTemplate.DataPanelTemplate;

import javax.swing.*;
import java.awt.*;

@Getter
public class MainForm extends JFrame {
    private JLabel txtTitle;
    private JPanel pnlMain;

    public void updateDisplay(){
        //create a main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create a scroll pane and add the mainPanel to it
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);


        this.setContentPane(scrollPane);

        for (int i = 0; i < Runner.listOfRows.size(); i++) {
            //create a data panel
            DataPanelTemplate template = new DataPanelTemplate();

            //set data for Data panel template
            template.setVisible(true);
            template.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            //populate the fields with the data
            template.getTxtCode().setText(Runner.listOfRows.get(i).get(0));
            template.getTxtColour().setText(Runner.listOfRows.get(i).get(1));
            template.getTxtSellPrice().setText(Runner.listOfRows.get(i).get(3));
            template.getTxtCostToProduce().setText(Runner.listOfRows.get(i).get(4));
            template.getTxtQuantity().setText(Runner.listOfRows.get(i).get(5));
            template.getTxtDescription().setText(Runner.listOfRows.get(i).get(2));


            mainPanel.add(template);
            this.pack();
        }
        this.pack();
    }
}
