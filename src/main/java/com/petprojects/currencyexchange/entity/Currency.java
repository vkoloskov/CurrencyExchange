package com.petprojects.currencyexchange.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Currency {
    private Integer id;
    private String name;
    private String code;
    private String sign;

    public Currency(Integer id, String name, String code, String sign) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.sign = sign;
    }

    public Currency(String name, String code, String sign) {
        this.name = name;
        this.code = code;
        this.sign = sign;
    }

}
