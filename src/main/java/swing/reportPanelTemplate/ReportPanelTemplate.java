package swing.reportPanelTemplate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class ReportPanelTemplate extends JPanel {

    //TODO: Code , QTY , Cost to produce ,Sell price , Totals to discern if its profitable or not

    private Label lblCode = new Label("Code: ");
    private Label txtCode;

    private Label lblQuantity = new Label("Quantity: ");
    private Label txtQuantity;

    private Label lblCostToProduce = new Label("Cost to produce: ");
    private Label txtCostToProduce;

    private Label lblSellPrice = new Label("Sell Price: ");
    private Label txtSellPrice;

    private Label lblProfitsCalculator;


    public ReportPanelTemplate(String txtCode, String txtQuantity, String txtCostToProduce, String txtSellPrice, String lblProfitsCalculator) {
        //initialize objects
        this.txtCode = new Label();
        this.txtCode.setText(txtCode);

        this.txtQuantity = new Label();
        this.txtQuantity.setText(txtQuantity);

        this.txtCostToProduce = new Label();
        this.txtCostToProduce.setText(txtCostToProduce);

        this.txtSellPrice = new Label();
        this.txtSellPrice.setText(txtSellPrice);

        this.lblProfitsCalculator = new Label();
        this.lblProfitsCalculator.setText(lblProfitsCalculator);

        //Set Color of text to red if it is a loss
        if(Double.parseDouble(this.lblProfitsCalculator.getText().replace("Calculated Value: R","").replace(",","")) < 0){
            this.lblProfitsCalculator.setForeground(Color.RED);
        }else{
            this.lblProfitsCalculator.setForeground(Color.GREEN);
        }

        //Add objects to the panel
        this.add(lblCode);
        this.add(this.txtCode);

        this.add(lblQuantity);
        this.add(this.txtQuantity);

        this.add(lblCostToProduce);
        this.add(this.txtCostToProduce);

        this.add(lblSellPrice);
        this.add(this.txtSellPrice);

        this.add(this.lblProfitsCalculator);

        this.setVisible(true);
    }

    @Override
    public String toString() {
        return "ReportPanelTemplate{" +
                "lblCode=" + lblCode.getText() +
                ", txtCode=" + txtCode.getText() +
                ", lblQuantity=" + lblQuantity.getText() +
                ", txtQuantity=" + txtQuantity.getText() +
                ", lblCostToProduce=" + lblCostToProduce.getText() +
                ", txtCostToProduce=" + txtCostToProduce.getText() +
                ", lblSellPrice=" + lblSellPrice.getText() +
                ", txtSellPrice=" + txtSellPrice.getText() +
                ", lblProfitsCalculator=" + lblProfitsCalculator.getText() +
                '}';
    }
}
