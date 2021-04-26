package com.zab.netty.common.serialize;

import com.zab.netty.common.serialize.impl.ProtostuffSerializer;

/**
 * @ClassName Serializer
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:01
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public interface Serializer {

    /**
     * 默认使用 Protostuff 序列化
     */
    Serializer DEFAULT = new ProtostuffSerializer();

    /**
     * 序列化
     */
    <T> byte[] serialize(T object);

    /**
     * 反序列化
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();
}
