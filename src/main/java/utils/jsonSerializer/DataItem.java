package utils.jsonSerializer;


import lombok.*;

import java.time.LocalDateTime;

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
    private String txtQuantitySold;
}
