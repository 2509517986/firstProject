package com.senxu.omserver.web.enums;


//拍照类型：定时拍照/主站请求拍照
public enum CapPicTypeEnum {

    DomainReqCap (1,"手动拍照"),
    TimerCap(2,"定时拍照"),
    AlarmCap(3,"报警上传");


    private Integer code;

    private String text;

    public Integer getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    CapPicTypeEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public static CapPicTypeEnum valueOf(Integer code) {
        for (CapPicTypeEnum anEnum : CapPicTypeEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }
}

