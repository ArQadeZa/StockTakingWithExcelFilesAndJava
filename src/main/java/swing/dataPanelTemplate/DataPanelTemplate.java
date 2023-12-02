package swing.dataPanelTemplate;

import lombok.Getter;
import lombok.Setter;
import runner.Runner;
import utils.ExcelUtils.ExcelUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
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
    private JTextField txtQuantity;
    private JButton btnIncrease;
    private JButton btnDecrease;
    private JButton removeElementButton;
    private JTextArea txtDescription;
    private JButton btnAddElement;


    //TODO: ADD FUNCTIONALITY TO + & - BUTTONS
    //TODO: ADD FUNCTIONALITY TO ADD BUTTON
    //TODO: ADD FUNCTIONALITY TO UPDATE THE EXCEL FILE IF ANYTHING CHANGES
    //TODO: ADD AUTO-SAVE FUNCTIONALITY WHEN ANYTHING IS CHANGED
    public DataPanelTemplate() {
        initComponents();
        setLayout(new FlowLayout(FlowLayout.LEFT));
        addComponentsToPanel();
    }

    /**
     * updates the data in the list based on what's populated in the textboxes
     * @param list -list that contains all the rows
     * @param id - id of the lement in the row
     */
    public void updateList(List<List<String>> list, String id) {
        List<String> rowWithId = list.stream().filter(l -> l.get(0).equals(id)).findFirst().get();
        list.set(list.indexOf(rowWithId), Arrays.asList(id, txtColour.getText(), txtDescription.getText(), txtSellPrice.getText(), txtCostToProduce.getText(), txtQuantity.getText()));
    }

    /**
     * Activates the event listeners for when the text in a text box changes
     */
    public void activateOnTextChangeListeners(){
        txtColour.getDocument().addDocumentListener(this.getDocumentListener(txtCode.getText()));
        txtSellPrice.getDocument().addDocumentListener(this.getDocumentListener(txtCode.getText()));
        txtCostToProduce.getDocument().addDocumentListener(this.getDocumentListener(txtCode.getText()));
        txtQuantity.getDocument().addDocumentListener(this.getDocumentListener(txtCode.getText()));
        txtDescription.getDocument().addDocumentListener(this.getDocumentListener(txtCode.getText()));
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
        txtQuantity = new JTextField(10);

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
                List<String> line = Runner.listOfRows.stream().filter(l -> l.contains(code)).findAny().get();
                //remove element
                Runner.listOfRows.remove(line);

                //save changes to file
                boolean saved = ExcelUtils.writeExcelFile(Runner.listOfRows);

                //Display message to say element has been removed
                if (saved) {
                    JOptionPane.showMessageDialog(Runner.mainForm, String.format("The element with code %s has been removed", line.get(0)));
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
     * @return document listener
     */
    private DocumentListener getDocumentListener(String id){
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateList(Runner.listOfRows,id);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateList(Runner.listOfRows,id);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateList(Runner.listOfRows,id);
            }
        };
    }
}
