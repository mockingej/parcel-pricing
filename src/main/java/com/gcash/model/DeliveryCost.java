package com.gcash.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class DeliveryCost {

    private String type;
    private Double volume = 0.0;
    private Double weight = 0.0;
    private String message;
    private String voucherMessage;
    private Double total = 0.0;
    private Double discountPercentage = 0.0;
    private Double discount = 0.0;
    private Double overallTotal = 0.0;

    public String getVoucherMessage() {
        return voucherMessage;
    }

    public void setVoucherMessage(String voucherMessage) {
        this.voucherMessage = voucherMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getOverallTotal() {
        return overallTotal;
    }

    public void setOverallTotal(Double overallTotal) {
        this.overallTotal = overallTotal;
    }
}
