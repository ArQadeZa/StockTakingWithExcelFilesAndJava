package swing.dataPanelTemplate;

import lombok.Getter;
import lombok.Setter;
import runner.Runner;
import utils.ExcelUtils.ExcelUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Getter
@Setter
public class DataPanelTemplate extends JPanel {
    private JLabel lblCode;
    private JTextField txtCode;
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
    //TODO: ADD FUNCTIONALITY TO REMOVE BUTTON
    //TODO: ADD FUNCTIONALITY TO UPDATE THE EXCEL FILE IF ANYTHING CHANGES
    //TODO: ADD AN ADD Button
    public DataPanelTemplate() {
        initComponents();
        setLayout(new FlowLayout(FlowLayout.LEFT));
        addComponentsToPanel();
    }

    private void initComponents() {
        lblCode = new JLabel("Code:");
        txtCode = new JTextField(10);

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
            if(response == 0){

                //find element that contains the code
                List<String> line = Runner.listOfRows.stream().filter(l-> l.contains(code)).findAny().get();
                //remove element
                Runner.listOfRows.remove(line);

                //save changes to file
                boolean saved =  ExcelUtils.writeExcelFile(Runner.listOfRows);

                //Display message to say element has been removed
                if(saved){
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
}
