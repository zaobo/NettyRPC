package com.zab.netty.common.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.zab.netty.common.enums.SerializerAlgorithmEnum;
import com.zab.netty.common.serialize.Serializer;

/**
 * @ClassName JSONSerializer
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:02
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class JSONSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithmEnum.JSON.getType();
    }
}
