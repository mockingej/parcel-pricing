package com.gcash.controller;

import com.gcash.model.DeliveryCost;
import com.gcash.model.ParcelRequest;
import com.gcash.model.VoucherItem;
import com.gcash.service.ParcelService;
import com.gcash.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "api")
public class ParcelController {

    private final ParcelService parcelService;
    private final VoucherService voucherService;

    public ParcelController(ParcelService parcelService, VoucherService voucherService) {
        this.parcelService = parcelService;
        this.voucherService = voucherService;
    }

    @PostMapping(value = "/price", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeliveryCost> getPrice(@RequestBody ParcelRequest parcelRequest) {

        double volume = parcelRequest.getHeight() * parcelRequest.getWidth() * parcelRequest.getLength();
        if (volume <= 0 || parcelRequest.getWeight() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid value (volume and weight)");
        }

        return ResponseEntity.ok(parcelService.getPrice(parcelRequest));
    }

    @GetMapping(value = "/voucher/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoucherItem> getPrice(@PathVariable("code") String code) {
        return ResponseEntity.ok(voucherService.voucherItem(code));
    }
}
