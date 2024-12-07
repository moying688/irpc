package org.moying.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static org.moying.common.constants.RpcConstants.MAGIC_NUMBER;

public class RpcDecoder extends ByteToMessageDecoder {

    /**
     * 协议开头部分的标准长度
     */
    public final int BASE_LENGTH = 2 + 4;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
            if(byteBuf.readableBytes() >= BASE_LENGTH){
                // 防止收到一些体积过大的数据包
                if(byteBuf.readableBytes()>1000){
                    byteBuf.skipBytes(byteBuf.readableBytes());
                }
                int beginReader;
                while(true){
                    beginReader = byteBuf.readerIndex();
                    // 这里对应了RpcProtocol的魔数
                    byteBuf.markReaderIndex();
                    if(byteBuf.readShort() == MAGIC_NUMBER){
                        break;
                    }else{
                        // 非法
                        ctx.close();
                        return;
                    }
                }
                // contentLength
                int length = byteBuf.readInt();
                // 说明剩余的数据包是不完整的，需要重置下读索引
                if(byteBuf.readableBytes() < length){
                    byteBuf.readerIndex(beginReader);
                    return;
                }
                // content
                byte[] data = new byte[length];
                byteBuf.readBytes(data);
                RpcProtocol rpcProtocol = new RpcProtocol(data);
                out.add(rpcProtocol);
            }
        }
}
