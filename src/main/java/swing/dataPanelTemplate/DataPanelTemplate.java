package swing.dataPanelTemplate;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

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

        lblDescription = new JLabel("Description:");
        txtDescription = new JTextArea(5, 20);
        txtDescription.setLineWrap(true);

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
    }
}
