package swing.mainForm;

import lombok.Getter;
import runner.Runner;
import swing.dataPanelTemplate.DataPanelTemplate;
import swing.datePicker.DatePicker;
import utils.jsonSerializer.JsonSerializer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Getter
public class MainForm extends JFrame {
    private JLabel txtTitle;
    private JPanel pnlMain;
    private int min = 5;

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
        Button btnGenerateReport = new Button("Generate Report");
        mainPanel.add(btnGenerateReport);

        //on button click select a date
        btnGenerateReport.addActionListener((a) -> {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Select the Starting date");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new FlowLayout());

                //2 datepickers one for start date and other for end
                DatePicker comp = new DatePicker();
                frame.add(comp);
                DatePicker comp2 = new DatePicker();
                frame.add(comp2);

                Button btn = new Button("Done");
                btn.setVisible(true);

                frame.add(btn);
                frame.setVisible(true);
                frame.pack();

                //listener to save data to global variables
                btn.addActionListener((r) -> {
                    Runner.startDate = comp.getSelectedDate().toString();
                    Runner.endDate = comp2.getSelectedDate().toString();
                    JOptionPane.showMessageDialog(null, "Start Date Selected: " + Runner.startDate + "\nEnd Date Selected: " + Runner.endDate);

                    frame.dispose();
                });

                //iterate through entire list to find all dates of items sold
                for (int i = 0; i < Runner.listOfRows.size(); i++) {
                    int itemsSold = 0;
                    String[] itemsSoldArr = Runner.listOfRows.get(i).getItemSellTimes();
                    //iterate through array of dates sold
                    for (int j = 0; j < itemsSoldArr.length; j++) {
                        if (LocalDate.parse(itemsSoldArr[j]).isBefore(LocalDate.parse(Runner.endDate)) && LocalDate.parse(itemsSoldArr[j]).isAfter(LocalDate.parse(Runner.startDate))) {
                            ++itemsSold;
                        }
                    }

                    //TODO: Add functionality to calculate costs and everything required on the report

                }
            });


        });

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
            template.getTxtQuantitySold().setText(Runner.listOfRows.get(i).getTxtQuantitySold());

            //flag for minimum value
            if (Integer.parseInt(template.getTxtQuantity().getText()) <= 0) {
                template.setBackground(Color.RED);
                template.getBtnIncreaseSold().setEnabled(false);
                template.getBtnDecrease().setEnabled(false);

            } else if (Integer.parseInt(template.getTxtQuantity().getText()) < min) {
                template.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            }

            if (Integer.parseInt(template.getTxtQuantitySold().getText()) <= 0) {
                template.getBtnDecreaseSold().setEnabled(false);
            }

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
