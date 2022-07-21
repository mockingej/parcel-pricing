package com.gcash.api;

import com.gcash.model.VoucherItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "voucher", url = "${voucher.api}")
public interface VoucherAPI {

    @GetMapping(value = "voucher/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    VoucherItem getVoucher(@PathVariable("code") String code, @RequestParam("key") String key);

}
