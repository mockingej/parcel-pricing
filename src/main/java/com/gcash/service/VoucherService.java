package com.gcash.service;

import com.gcash.api.VoucherAPI;
import com.gcash.model.VoucherItem;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VoucherService {

    private final VoucherAPI voucherAPI;

    @Value("${voucher.key}")
    private String key;

    public VoucherService(VoucherAPI voucherAPI) {
        this.voucherAPI = voucherAPI;
    }

    public VoucherItem voucherItem(String code) {
        try {
            return voucherAPI.getVoucher(code, key);
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.valueOf(ex.status()), ex.getMessage());
        }
    }
}
