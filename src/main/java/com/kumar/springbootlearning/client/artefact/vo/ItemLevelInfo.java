package com.kumar.springbootlearning.client.artefact.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemLevelInfo implements Serializable {

    private String orderId;

    private String orderTime;

    private String orderChannel;

    private String essential;

    private String invoiceNumber;

    private String invoiceDate;

    private List<String> itemClassification;

    private String itemName;

    private String itemReferenceNumber;

    private String itemType;

    private String itemHsnCode;

    private Integer itemQuantity;

    /**
     * Item Unit Price (Tax Exclusive)
     */
    private Double itemUnitPrice;

    /**
     * Item Discount (Tax Exclusive) (Per unit) Item level discount + Order level discount split into item level
     */
    private Double itemDiscount;

    /**
     * Item Taxable Amount is (itemUnitPrice - itemDiscount) * itemQuantity
     */
    private Double itemTaxableAmount;

    /**
     * Item Tax Amount is Item Tax Rate % itemTaxableAmount
     */
    private Double itemTaxAmount;

    /**
     * Item CGST Tax Amount
     */
    private Double itemCgstAmount;

    /**
     * Item SGST Tax Amount
     */
    private Double itemSgstAmount;

    /**
     * Item IGST Tax Amount
     */
    private Double itemIgstAmount;

    /**
     * Item Value is itemTaxableAmount + itemTaxAmount
     */
    private Double itemValue;

    /**
     * Item CGST Tax Rate
     */
    private Double itemCgst;

    /**
     * Item CGST Tax Rate
     */
    private Double itemSgst;

    /**
     * Item CGST Tax Rate
     */
    private Double itemIgst;

    private String id;

}
