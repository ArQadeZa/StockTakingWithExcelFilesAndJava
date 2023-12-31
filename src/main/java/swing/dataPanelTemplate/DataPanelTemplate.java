package swing.dataPanelTemplate;

import lombok.Getter;
import lombok.Setter;
import runner.Runner;
import utils.jsonSerializer.DataItem;
import utils.jsonSerializer.JsonSerializer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class DataPanelTemplate extends JPanel {
    private JLabel lblCode;
    private JLabel txtCode;
    private JLabel lblColour;
    private JTextField txtColour;
    private JLabel lblDescription;
    private JTextField txtSellPrice;
    private JLabel lblSellPrice;
    private JLabel lblCostToProduce;
    private JTextField txtCostToProduce;
    private JLabel lblQuantity;
    private JLabel txtQuantity;
    private JLabel txtQuantitySold;
    private JLabel lblQuantitySold;
    private JButton btnIncrease;
    private JButton btnDecrease;
    private JButton btnIncreaseSold;
    private JButton btnDecreaseSold;
    private JButton removeElementButton;
    private JTextArea txtDescription;
    private JButton btnAddElement;

    public DataPanelTemplate() {
        initComponents();
        setLayout(new FlowLayout(FlowLayout.LEFT));
        addComponentsToPanel();
    }

    /**
     * updates the data in the list based on what's populated in the textboxes
     *
     * @param list -list that contains all the rows
     * @param id   - id of the element in the row
     */
    public void updateList(List<DataItem> list, String id) {
        DataItem rowWithId = list.stream().filter(p -> p.getTxtCode().equals(id)).findAny().get();
        rowWithId.setTxtCode(id);
        rowWithId.setTxtColour(txtColour.getText());
        rowWithId.setTxtDescription(txtDescription.getText());
        rowWithId.setTxtSellPrice(txtSellPrice.getText());
        rowWithId.setTxtCostToProduce(txtCostToProduce.getText());
        rowWithId.setTxtQuantity(txtQuantity.getText());
        rowWithId.setTxtQuantitySold(txtQuantitySold.getText());
    }

    /**
     * Activates the event listeners for when the text in a text box changes
     */
    public void activateListeners() {
        txtColour.getDocument().addDocumentListener(this.getDocumentListener(txtCode.getText()));
        txtSellPrice.getDocument().addDocumentListener(this.getDocumentListener(txtCode.getText()));
        txtCostToProduce.getDocument().addDocumentListener(this.getDocumentListener(txtCode.getText()));
        txtDescription.getDocument().addDocumentListener(this.getDocumentListener(txtCode.getText()));
        btnIncrease.addActionListener(getActionListenerIncrease());
        btnDecrease.addActionListener(getActionListenerDecrease());
        btnDecreaseSold.addActionListener(getActionListenerDecreaseSold());
        btnIncreaseSold.addActionListener(getActionListenerIncreaseSold());
        btnAddElement.addActionListener(getActionAddButton());
    }

    /**
     * initializing the components of the object
     */
    private void initComponents() {
        lblCode = new JLabel("Code:");

        txtCode = new JLabel();
        Font font = new Font("Courier", Font.BOLD, 12);
        txtCode.setFont(font);

        lblColour = new JLabel("Colour: ");
        txtColour = new JTextField(10);

        txtSellPrice = new JTextField(10);

        lblSellPrice = new JLabel("Sell Price:");
        txtCostToProduce = new JTextField(10);

        lblCostToProduce = new JLabel("Cost to Produce:");
        txtQuantity = new JLabel();
        txtQuantity.setFont(font);

        lblQuantity = new JLabel("Quantity:");

        lblQuantitySold = new JLabel("Quantity Sold:");
        txtQuantitySold = new JLabel();
        txtQuantitySold.setFont(font);


        btnIncrease = new JButton("+");
        btnDecrease = new JButton("-");

        btnIncreaseSold = new JButton("+");
        btnDecreaseSold = new JButton("-");
        removeElementButton = new JButton("Remove element");

        //create an on click method for the button
        removeElementButton.addActionListener(al -> {
            String code = txtCode.getText();
            int response = JOptionPane.showConfirmDialog(null, String.format("Are you sure you want to delete element with code %s ?", code));

            //if the response is yes remove the element from the list
            if (response == 0) {

                //find element that contains the code
                DataItem dataItem = Runner.listOfRows.stream().filter(di -> di.getTxtCode().equals(code)).findAny().get();

                //remove element
                Runner.listOfRows.remove(dataItem);

                //save changes to file
                boolean saved = JsonSerializer.serialize(Runner.listOfRows);

                //Display message to say element has been removed
                if (saved) {
                    JOptionPane.showMessageDialog(Runner.mainForm, String.format("The element with code %s has been removed", dataItem.getTxtCode()));
                }

                //update ui
                Runner.mainForm.updateDisplay();
            }
        });

        lblDescription = new JLabel("Description:");
        txtDescription = new JTextArea(5, 20);
        txtDescription.setLineWrap(true);
        btnAddElement = new JButton("Add Product");
    }


    /**
     * add the components to the display panel\
     */
    private void addComponentsToPanel() {

        add(lblCode);
        add(txtCode);

        add(lblColour);
        add(txtColour);

        add(lblSellPrice);
        add(txtSellPrice);

        add(lblCostToProduce);
        add(txtCostToProduce);

        Panel quantity = new Panel();
        add(quantity);
        quantity.add(lblQuantity);
        quantity.add(txtQuantity);
        quantity.add(btnIncrease);
        quantity.add(btnDecrease);

        Panel quantitySold = new Panel();
        add(quantitySold);
        quantitySold.add(lblQuantitySold);
        quantitySold.add(txtQuantitySold);
        quantitySold.add(btnIncreaseSold);
        quantitySold.add(btnDecreaseSold);

        add(lblDescription);
        add(new JScrollPane(txtDescription));

        add(removeElementButton);
        add(btnAddElement);
    }


    /**
     * Create a document listener for the text files
     *
     * @return document listener
     */
    private DocumentListener getDocumentListener(String id) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateList(Runner.listOfRows, id);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateList(Runner.listOfRows, id);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateList(Runner.listOfRows, id);
            }
        };
    }

    /**
     * creating the onclick event for Increasing the count of items sold and reducing the amount of stock left
     *
     * @return Action listener
     */
    private ActionListener getActionListenerIncreaseSold() {
        String code = getTxtCode().getText();
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get item
                DataItem dataItem = Runner.listOfRows.stream().filter(di -> di.getTxtCode().equals(code)).findAny().get();

                //update item quantity
                dataItem.setTxtQuantity(String.valueOf(Integer.parseInt(dataItem.getTxtQuantity()) - 1));
                dataItem.setTxtQuantitySold(String.valueOf(Integer.parseInt(dataItem.getTxtQuantitySold()) + 1));

                //add timestamp
                int length = dataItem.getItemSellTimes().length;
                String[] array = new String[length + 1];

                //populate array
                for (int i = 0; i < array.length; i++) {
                    if (i == array.length - 1) {
                        array[i] = String.valueOf(LocalDate.now());
                    } else {
                        array[i] = dataItem.getItemSellTimes()[i];
                    }
                }

                //set new array
                dataItem.setItemSellTimes(array);

                //update the Ui
                Runner.mainForm.updateDisplay();
            }
        };
    }

    /**
     * creating the onclick event for Decreasing the count
     *
     * @return Action listener
     */
    private ActionListener getActionListenerIncrease() {
        String code = getTxtCode().getText();
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get item
                DataItem dataItem = Runner.listOfRows.stream().filter(di -> di.getTxtCode().equals(code)).findAny().get();

                //update item quantity
                dataItem.setTxtQuantity(String.valueOf(Integer.parseInt(dataItem.getTxtQuantity()) + 1));

                //update the Ui
                Runner.mainForm.updateDisplay();
            }
        };
    }

    /**
     * creating the onclick event for Decreasing the count
     *
     * @return Action listener
     */
    private ActionListener getActionListenerDecrease() {
        String code = getTxtCode().getText();
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get item
                DataItem dataItem = Runner.listOfRows.stream().filter(di -> di.getTxtCode().equals(code)).findAny().get();

                //update item quantity
                dataItem.setTxtQuantity(String.valueOf(Integer.parseInt(dataItem.getTxtQuantity()) - 1));

                //update the Ui
                Runner.mainForm.updateDisplay();
            }
        };
    }

    /**
     * creating the onclick event for Decreasing the count of items sold
     *
     * @return Action listener
     */
    private ActionListener getActionListenerDecreaseSold() {
        String code = getTxtCode().getText();
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get item
                DataItem dataItem = Runner.listOfRows.stream().filter(di -> di.getTxtCode().equals(code)).findAny().get();

                //update item quantity
                dataItem.setTxtQuantitySold(String.valueOf(Integer.parseInt(dataItem.getTxtQuantitySold()) - 1));
                dataItem.setTxtQuantity(Integer.parseInt(dataItem.getTxtQuantity()) + 1 + "");
                dataItem.setItemSellTimes(Arrays.copyOf(dataItem.getItemSellTimes(), dataItem.getItemSellTimes().length - 1));

                //update the Ui
                Runner.mainForm.updateDisplay();
            }
        };
    }

    /**
     * creating the onclick event for Adding a new product
     *
     * @return Action listener
     */
    public ActionListener getActionAddButton() {
        String code = getTxtCode().getText();
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //enter details of element

                // if element contains the same code it should prompt for another
                String code;
                int count;

                code = getUniqueCode();

                if (code != null && !code.isEmpty()) {
                    String colour = JOptionPane.showInputDialog(String.format("Code:%s | please enter the product colour", code));
                    if (colour != null && colour.length() != 0) {
                        String sellPrice = JOptionPane.showInputDialog(String.format("Code:%s Colour:%s | please enter the sell price", code, colour)).toUpperCase();

                        if (sellPrice != null && sellPrice.length() != 0) {
                            //add Rand
                            if (!sellPrice.contains("R")) {
                                sellPrice = "R" + sellPrice;
                            }
                            String costToProduce = JOptionPane.showInputDialog(String.format("Code:%s Colour:%s Sell Price: %s | please enter the Cost to produce", code, colour, sellPrice));


                            if (costToProduce != null && costToProduce.length() != 0) {
                                //add Rand
                                if (!costToProduce.contains("R")) {
                                    costToProduce = "R" + costToProduce;
                                }


                                String quantity = JOptionPane.showInputDialog(String.format("Code:%s Colour:%s Sell Price:%s Cost to produce:%s | please enter the quantity", code, colour, sellPrice, costToProduce));
                                if (quantity != null && quantity.length() != 0) {
                                    String description = JOptionPane.showInputDialog(String.format("Code:%s Colour:%s Sell Price:%s Cost to produce:%s Quantity:%s | please enter the Description", code, colour, sellPrice, costToProduce, quantity));
                                    if (description != null && description.length() != 0) {

                                        // create object
                                        DataItem dataItem = new DataItem(code, colour, sellPrice, costToProduce, quantity, description, "0", new String[]{});

                                        //add object to list
                                        Runner.listOfRows.add(dataItem);

                                        //update display
                                        Runner.mainForm.updateDisplay();

                                        //Save file
                                        Runner.mainForm.saveFile();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            /**
             * Get a unique item code from the user
             * @return unique item code
             */
            private static String getUniqueCode() {
                String code;
                int count;
                do {
                    count = 0;
                    code = JOptionPane.showInputDialog("Enter the product code");

                    for (DataItem item : Runner.listOfRows) {
                        if (item.getTxtCode().equals(code)) {
                            ++count;
                            JOptionPane.showMessageDialog(null, "Cannot have the same code twice: " + code, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
                while (count != 0);
                return code;
            }

        };
    }
}


