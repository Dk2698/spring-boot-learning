package com.kumar.springbootlearning.client.artefact.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kumar.springbootlearning.client.shipping.vo.ShippingDimensions;
import com.kumar.springbootlearning.client.shipping.vo.ShippingInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Shipment implements Serializable {

    private String workUnitId;

    private ShippingInfo shipTo;

    private ShippingInfo shipFrom;

    private ShippingInfo returnAddress;

    private String senderContactNumber;

    private String receiverContactNumber;

    private String courierName;

    private String awbNo;

    private ShippingDimensions shipmentDimension;

    private Double weight;

    private Double volWt;

    private String creationDate;

    private Double productValue;

    private Double chargeableWeight;

    private String shipmentReferenceNumber;

    private String shipperName;

    private List<ItemLevelInfo> itemDetails;

    private List<ArtefactPaymentInfo> paymentDetails;

    /**
     * Order Discount (Tax Inclusive)
     */
    private Double orderDiscount;

    private Double totalTaxAmount;

    private Double totalAmount;

    private ShippingChargeDetails shippingChargeDetails;

    private String logistiexLogo;

    private String placeholderImage1;

    private String placeholderImage2;

    private String placeholderImage3;

    private ShippingInfo originSender;

    private String totalAmountInWords;

    private String supplyState;

    private String deliveryState;

    private String originGstn;

    private String originPan;

    private String sellerLogo;

    private String signatureImage;

//    private PaymentMode paymentMode;

    private Double amountToBeCollected;

    private Integer boxCount;

    private String ewayBillNumber;

    private String handlingInstruction;

    private String shipperLogo;

    private String platformLogo;

    private String courierRouteCode;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ShippingChargeDetails implements Serializable {

        /**
         * Shipping Unit Price (Tax Exclusive)
         */
        private Double shippingUnitPrice;

        /**
         * Shipping Discount (Tax Exclusive)
         */
        private Double shippingDiscount;

        /**
         * Shipping Taxable Amount is (shippingUnitPrice - shippingDiscount)
         */
        private Double shippingTaxableAmount;

        /**
         * Shipping Tax Amount is Item Tax Rate % itemTaxableAmount
         */
        private Double shippingTaxAmount;

        /**
         * Shipping CGST Tax Amount
         */
        private Double shippingCgstAmount;

        /**
         * Shipping SGST Tax Amount
         */
        private Double shippingSgstAmount;

        /**
         * Shipping IGST Tax Amount
         */
        private Double shippingIgstAmount;

        /**
         * Shipping Value is shippingTaxableAmount + shippingTaxAmount
         */
        private Double shippingValue;

        // Note: Over all tax rate on shipping charge is fix i.e, 18%

        /**
         * Shipping CGST Tax Rate
         */
        private Double shippingCgst;

        /**
         * Shipping CGST Tax Rate
         */
        private Double shippingSgst;

        /**
         * Shipping CGST Tax Rate
         */
        private Double shippingIgst;

    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ArtefactPaymentInfo implements Serializable {
        private Double paymentAmount;
        private String paymentMethod;
        private String paymentTime;
        private String paymentTransactionId;
    }
}
