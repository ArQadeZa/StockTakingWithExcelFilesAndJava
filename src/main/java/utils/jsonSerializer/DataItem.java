package utils.jsonSerializer;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataItem {

    private String txtCode;
    private String txtColour;
    private String txtSellPrice;
    private String txtCostToProduce;
    private String txtQuantity;
    private String txtDescription;

}
