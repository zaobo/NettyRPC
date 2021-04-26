package com.zab.netty.common.utils;

import com.zab.netty.common.enums.RequestTypeEnum;
import com.zab.netty.common.protocol.RpcProtocol;
import com.zab.netty.common.protocol.RpcRequest;
import com.zab.netty.common.protocol.RpcResponse;
import com.zab.netty.common.serialize.Serializer;
import com.zab.netty.common.serialize.impl.ProtostuffSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CodeUtil
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 16:59
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class CodeUtil {

    /**
     * 魔数
     */
    public static final int MAGIC_NUMBER = 0x65321487;
    /**
     * 协议版本
     */
    public static final int PROTOCOL_VERSION = 1;

    private static final Map<Byte, Serializer> serializerMap;

    private static final Map<Byte, Class<? extends RpcProtocol>> requestTypeMap;


    static {
        //
        serializerMap = new HashMap<>();
        ProtostuffSerializer serializer = new ProtostuffSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);

        //
        requestTypeMap = new HashMap<>();
        requestTypeMap.put(RequestTypeEnum.REQUEST.getType(), RpcRequest.class);
        requestTypeMap.put(RequestTypeEnum.RESPONSE.getType(), RpcResponse.class);
    }

    /**
     * 编码
     */
    public static void encoder(ByteBuf out, RpcProtocol protocol) {
        byte[] data = Serializer.DEFAULT.serialize(protocol);
        out.writeInt(MAGIC_NUMBER); // 4
        out.writeByte(PROTOCOL_VERSION); //1
        out.writeByte(Serializer.DEFAULT.getSerializerAlgorithm()); // 1
        out.writeByte(protocol.getRequestType()); // 1
        out.writeInt(data.length); // 4
        out.writeBytes(data);
    }

    /**
     * 解码
     */
    public static RpcProtocol decoder(ByteBuf byteBuf) {
        byteBuf.skipBytes(4); //跳过魔数
        byteBuf.skipBytes(1); // 跳过协议版本
        byte serializeAlgorithm = byteBuf.readByte();
        byte requestType = byteBuf.readByte();
        int dataLength = byteBuf.readInt();

        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);

        Serializer serializer = serializerMap.get(serializeAlgorithm);
        return serializer.deserialize(requestTypeMap.get(requestType), data);
    }

}
