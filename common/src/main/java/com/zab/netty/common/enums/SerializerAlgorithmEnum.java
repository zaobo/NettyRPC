package com.zab.netty.common.enums;

/**
 * @ClassName SerializerAlgorithmEnum
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:03
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public enum SerializerAlgorithmEnum {

    PROTOSTUFF(1),

    JSON(2);

    private byte type;

    SerializerAlgorithmEnum(int type) {
        this.type = (byte) type;
    }

    public byte getType() {
        return type;
    }

}
