package com.kumar.springbootlearning.channel.exception;

public class ChannelProcessingException extends RuntimeException{
    public ChannelProcessingException(String message) {
        super(message);
    }

    public ChannelProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelProcessingException(String channelCode, String fulfillmentId, String orderId, String reason) {
        super(String.format("Error in Channel: %s, Fulfillment ID: %s, Order ID: %s. Cause: %s",
                channelCode, fulfillmentId, orderId != null ? orderId : "N/A", reason));
    }
}
