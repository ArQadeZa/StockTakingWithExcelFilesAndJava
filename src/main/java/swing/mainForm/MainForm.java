package swing.mainForm;

import lombok.Getter;
import runner.Runner;
import swing.dataPanelTemplate.DataPanelTemplate;
import utils.jsonSerializer.JsonSerializer;

import javax.swing.*;
import java.awt.*;

@Getter
public class MainForm extends JFrame {
    private JLabel txtTitle;
    private JPanel pnlMain;

    /**
     * update the main display
     */
    public void updateDisplay() {
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
            template.getTxtCode().setText(Runner.listOfRows.get(i).getTxtCode());
            template.getTxtColour().setText(Runner.listOfRows.get(i).getTxtColour());
            template.getTxtSellPrice().setText(Runner.listOfRows.get(i).getTxtSellPrice());
            template.getTxtCostToProduce().setText(Runner.listOfRows.get(i).getTxtCostToProduce());
            template.getTxtQuantity().setText(Runner.listOfRows.get(i).getTxtQuantity());
            template.getTxtDescription().setText(Runner.listOfRows.get(i).getTxtDescription());

            mainPanel.add(template);
            template.activateListeners();
            this.pack();
        }
        this.repaint();

    }


    /**
     * Auto save every 5 seconds
     */
    public void saveFile() {

        Thread thread = new Thread(() -> {
            while (true) {
                if (Runner.listOfRows.size() > 0) {
                    JsonSerializer.serialize(Runner.listOfRows);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

}
