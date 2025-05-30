package com.kumar.springbootlearning.client.shipping.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemInfo  implements Serializable {

    private String productId;

    private String itemName;

    private String description;

    private String skuCode;

    private String category;

    private String subcategory;

    private String brandName;

    private String orderNumber;

    private Instant orderTime;

    private String hsnCode;

    private String itemUrl;

    private List<String> itemImageUrls;

    private Double itemValue;

    private Integer quantity;

    private ShippingDimensions dimensions;

    private Boolean essential;

    private Boolean fragile;

    private Boolean dangerous;

    private Map<String, String> additionalAttributes;

    private TaxInfo taxInfo;

    private String returnReason;

    private List<QuestionDetails> questionDetails;

    private List<String> qcImageUrls;

}
