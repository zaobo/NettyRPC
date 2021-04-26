package com.zab.netty.common.serialize.impl;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.zab.netty.common.enums.SerializerAlgorithmEnum;
import com.zab.netty.common.serialize.Serializer;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ProtostuffSerializer
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:05
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class ProtostuffSerializer implements Serializer {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    @Override
    public <T> byte[] serialize(T object) {
        Class<T> clazz = (Class<T>) object.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtostuffIOUtil.toByteArray(object, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            T msg = objenesis.newInstance(clazz);
            Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, msg, schema);
            return msg;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithmEnum.PROTOSTUFF.getType();
    }

    private <T> Schema<T> getSchema(Class<T> clazz) {
        return (Schema<T>) cachedSchema.computeIfAbsent(clazz, RuntimeSchema::createFrom);
    }

}
