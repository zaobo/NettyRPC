package com.zab.netty.common.enums;

/**
 * @ClassName RequestTypeEnum
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:16
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public enum RequestTypeEnum {
    REQUEST(1),

    RESPONSE(2);

    private Byte type;

    RequestTypeEnum(int type) {
        this.type = (byte) type;
    }

    public Byte getType() {
        return type;
    }
}
