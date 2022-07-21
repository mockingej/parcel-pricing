package com.gcash.service;

import com.gcash.enums.VolumeBasedEnum;
import com.gcash.enums.WeightBasedEnum;
import com.gcash.model.DeliveryCost;
import com.gcash.model.ParcelRequest;
import com.gcash.model.VoucherItem;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;


@Service
public class ParcelService {

    private final VoucherService voucherService;

    public ParcelService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public DeliveryCost getPrice(ParcelRequest parcelRequest) {

        DeliveryCost deliveryCost = new DeliveryCost();
        double volume = parcelRequest.getHeight() * parcelRequest.getWidth() * parcelRequest.getLength();

        deliveryCost.setVolume(volume);
        deliveryCost.setWeight(parcelRequest.getWeight());

        WeightBasedEnum weightBasedEnum = Arrays.stream(WeightBasedEnum.values())
                .sorted(Comparator.comparingInt(WeightBasedEnum::getPriority))
                .filter(weight -> isExact(weight.getCondition(), parcelRequest.getWeight()))
                .findFirst()
                .orElse(null);

        if (!Objects.isNull(weightBasedEnum)) {

            if (weightBasedEnum == WeightBasedEnum.REJECT) {
                deliveryCost.setType(weightBasedEnum.getType());
                deliveryCost.setMessage("N/A");
                deliveryCost.setTotal(null);
                deliveryCost.setDiscountPercentage(null);
                deliveryCost.setDiscount(null);
                deliveryCost.setOverallTotal(null);
                return deliveryCost;
            }

            double cost = deliveryCost.getTotal();
            cost += (parcelRequest.getWeight() * weightBasedEnum.getCost());
            deliveryCost.setType(weightBasedEnum.getType());
            deliveryCost.setTotal(cost);

        }

        VolumeBasedEnum volumeBasedEnum = Arrays.stream(VolumeBasedEnum.values())
                .sorted(Comparator.comparingInt(VolumeBasedEnum::getPriority))
                .filter(vol -> isExact(vol.getCondition(), volume))
                .findFirst()
                .orElse(null);

        if (!Objects.isNull(volumeBasedEnum)) {
            String type = deliveryCost.getType();

            if (!StringUtils.isEmpty(type)) {
                deliveryCost.setType(volumeBasedEnum.getType() + " & " + type);
            } else {
                deliveryCost.setType(volumeBasedEnum.getType());
            }

            double cost = deliveryCost.getTotal();
            cost += (volume * volumeBasedEnum.getCost());
            deliveryCost.setTotal(cost);
        }

        deliveryCost.setType(deliveryCost.getType() + " package");

        return getDiscountPrice(deliveryCost, parcelRequest.getVoucher());
    }


    private DeliveryCost getDiscountPrice(DeliveryCost deliveryCost, String voucher) {
        VoucherItem voucherItem;
        try {
            if (!StringUtils.isEmpty(voucher)) {
                voucherItem = voucherService.voucherItem(voucher);
                double cost = deliveryCost.getTotal();
                double discount = voucherItem.getDiscount() / 100.0;
                double discountedCost = Math.round(cost * discount * 100.0) / 100.0;
                deliveryCost.setVoucherMessage(String.format("Voucher code %s is applied", voucherItem.getCode()));
                deliveryCost.setDiscountPercentage(voucherItem.getDiscount());
                deliveryCost.setDiscount(discountedCost);
                deliveryCost.setOverallTotal(cost - discountedCost);
            } else {
                deliveryCost.setOverallTotal(deliveryCost.getTotal());
                deliveryCost.setDiscountPercentage(null);
                deliveryCost.setDiscount(null);
                deliveryCost.setTotal(null);
            }

        } catch (ResponseStatusException ex) {
            deliveryCost.setOverallTotal(deliveryCost.getTotal());
            deliveryCost.setVoucherMessage("Invalid Voucher Code");
            deliveryCost.setDiscountPercentage(null);
            deliveryCost.setDiscount(null);
            deliveryCost.setTotal(null);
        }
        return deliveryCost;
    }


    private boolean isExact(String condition, Double metric) {
        if (condition.split(" ")[0].equals("<")) {
            return metric < Double.parseDouble(condition.split(" ")[1]);
        } else {
            return metric > Double.parseDouble(condition.split(" ")[1]);
        }

    }

}
