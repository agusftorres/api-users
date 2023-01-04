package com.module.apiusers.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PhoneModel {
    String number;
    String cityCode;
    String countryCode;
}
