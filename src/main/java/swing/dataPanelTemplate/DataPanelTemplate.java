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
import java.util.Comparator;
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
    private JButton btnIncrease;
    private JButton btnDecrease;
    private JButton removeElementButton;
    private JTextArea txtDescription;
    private JButton btnAddElement;

    //TODO: ADD FUNCTIONALITY TO ADD BUTTON
    public DataPanelTemplate() {
        initComponents();
        setLayout(new FlowLayout(FlowLayout.LEFT));
        addComponentsToPanel();
    }

    /**
     * updates the data in the list based on what's populated in the textboxes
     *
     * @param list -list that contains all the rows
     * @param id   - id of the lement in the row
     */
    public void updateList(List<DataItem> list, String id) {
        DataItem rowWithId = list.stream().filter(p -> p.getTxtCode().equals(id)).findAny().get();
        rowWithId.setTxtCode(id);
        rowWithId.setTxtColour(txtColour.getText());
        rowWithId.setTxtDescription(txtDescription.getText());
        rowWithId.setTxtSellPrice(txtSellPrice.getText());
        rowWithId.setTxtCostToProduce(txtCostToProduce.getText());
        rowWithId.setTxtQuantity(txtQuantity.getText());
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

        btnIncrease = new JButton("+");


        btnDecrease = new JButton("-");
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

        add(lblQuantity);
        add(txtQuantity);

        add(lblDescription);
        add(new JScrollPane(txtDescription));

        add(new JPanel()); // Spacer or additional components can be added here

        add(btnIncrease);
        add(btnDecrease);
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
     * creating the onclick event for Adding a new product
     *
     * @return Action listener
     */
    private ActionListener getActionAddButton() {
        String code = getTxtCode().getText();
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //enter details of element
                String code = JOptionPane.showInputDialog("Enter the product code");
                if (code != null && code.length() != 0) {
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
                                        DataItem dataItem = new DataItem(code, colour, sellPrice, costToProduce, quantity, description);

                                        //add object to list
                                        Runner.listOfRows.add(dataItem);

                                        //update display
                                        Runner.mainForm.updateDisplay();
                                    }
                                }
                            }
                        }
                    }
                }
            }

        };
    }
}


