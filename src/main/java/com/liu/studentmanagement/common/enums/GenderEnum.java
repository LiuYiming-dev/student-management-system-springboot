package com.liu.studentmanagement.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE(1, "男"),
    FEMALE(0, "女");

    @EnumValue
    private final int code;

    @JsonValue
    private final String desc;

    GenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
