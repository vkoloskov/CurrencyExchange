package com.petprojects.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
@AllArgsConstructor
public class CurrencyDto {
        private Integer id;
        private String name;
        private String code;
        private String sign;

        @ConstructorProperties({"name","code","sign"})
        public CurrencyDto(String name, String code, String sign) {
                this.name = name;
                this.code = code;
                this.sign = sign;
        }
}
