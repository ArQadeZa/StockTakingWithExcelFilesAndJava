package swing.mainForm;

import lombok.Getter;
import runner.Runner;
import swing.dataPanelTemplate.DataPanelTemplate;
import swing.datePicker.DatePicker;
import swing.reportPanelTemplate.ReportPanelTemplate;
import utils.jsonSerializer.DataItem;
import utils.jsonSerializer.JsonSerializer;

import javax.swing.*;
import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        //on button click select a date and generate a report based off the selected dates
        onClickForGeneratingAReport(btnGenerateReport);

        //iterates through the main list and creates objects of each item in the list and displays them on the main panel=
        iterateThroughListAndDisplayDataTemplates(mainPanel);

        //add, add button
        addButtonToAddNewProduct(mainPanel);

        //Save file
        Runner.mainForm.saveFile();

        this.repaint();

    }

    private static void onClickForGeneratingAReport(Button btnGenerateReport) {
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

                    //iterate through entire list to find all dates of items sold
                    List<ReportPanelTemplate> list = new ArrayList<>();
                    for (int i = 0; i < Runner.listOfRows.size(); i++) {
                        int itemsSold = 0;
                        String[] itemsSoldArr = Runner.listOfRows.get(i).getItemSellTimes();

                        //iterate through array of dates sold
                        for (int j = 0; j < itemsSoldArr.length; j++) {
                            if (
                                    (LocalDate.parse(itemsSoldArr[j]).isBefore(LocalDate.parse(Runner.endDate)) || (LocalDate.parse(itemsSoldArr[j]).isEqual(LocalDate.parse(Runner.endDate))))
                                            &&
                                            (LocalDate.parse(itemsSoldArr[j]).isAfter(LocalDate.parse(Runner.startDate)) || LocalDate.parse(itemsSoldArr[j]).isEqual(LocalDate.parse(Runner.startDate)))
                            ) {
                                ++itemsSold;
                            }
                        }

                        //add template to list
                        DataItem dataItem = Runner.listOfRows.get(i);

                        double cost = Double.parseDouble(dataItem.getTxtCostToProduce().replace("R", "").replace(",", "."));
                        double sellPrice = Double.parseDouble(dataItem.getTxtSellPrice().replace("R", "").replace(",", "."));

                        //calculate the profit or loss
                        double calculatedValue = (sellPrice - cost) * itemsSold;
                        if (itemsSold > 0) {
                            list.add(new ReportPanelTemplate(dataItem.getTxtCode(), itemsSold + "", dataItem.getTxtCostToProduce(), dataItem.getTxtSellPrice(), "Calculated Value: R" + calculatedValue + ""));
                        }
                    }

                    JFrame reportFrame = new JFrame(String.format("Report for %s to %s ", Runner.startDate, Runner.endDate));
                    reportFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    reportFrame.setLayout(new GridLayout(0, 1));
                    reportFrame.setSize(900, 900);

                    for (ReportPanelTemplate panel : list) {
                        reportFrame.add(panel);
                    }

                    reportFrame.pack();
                    reportFrame.setVisible(true);

                });

            });
        });
    }

    private void iterateThroughListAndDisplayDataTemplates(JPanel mainPanel) {
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
    }

    /**
     * Adds a button to the main panel to add a new element
     * @param mainPanel - panel on which to add the button
     */
    private void addButtonToAddNewProduct(JPanel mainPanel) {
        if(Runner.listOfRows.isEmpty()){
            JButton btnAddElement = new DataPanelTemplate().getBtnAddElement();
            btnAddElement.addActionListener(new DataPanelTemplate().getActionAddButton());
            mainPanel.add(btnAddElement);
            this.pack();
        }
    }


    /**
     * Auto save every 2 seconds
     */
    public void saveFile() {

        Thread thread = new Thread(() -> {
            while (true) {
                if (Runner.listOfRows.size() > 0) {
                    JsonSerializer.serialize(Runner.listOfRows);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

}
