package com.senxu.omserver.web.enums;


//拍照类型：定时拍照/主站请求拍照
public enum ManualCapTypeEnum {

    CurPreset(0,"当前预置位"),
    AllPreset(1,"所有预置位"),
    ThatPreset(2,"那个预置位");


    private Integer code;

    private String text;

    public Integer getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    ManualCapTypeEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public static ManualCapTypeEnum valueOf(Integer code) {
        for (ManualCapTypeEnum anEnum : ManualCapTypeEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }

}

