package com.senxu.omserver.web.enums;


//拍照类型：定时拍照/主站请求拍照
public enum DyTaskStatusEnum {
    created("created","已创建"),
    started("started","进行中"),
    done ("done","已完成"),
    canceled("canceled","已取消"),
    delay("delay","已过期"),
    interrupted("interrupted","已中断");
    private String status;



    private String desc;
    public String getStatus() {
        return status;
    }

    DyTaskStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static DyTaskStatusEnum getByVal(String status) {
        for (DyTaskStatusEnum anEnum : DyTaskStatusEnum.values()) {
            if (anEnum.getStatus().equals(status)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }
}

