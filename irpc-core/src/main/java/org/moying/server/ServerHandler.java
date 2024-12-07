package org.moying.server;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.moying.common.RpcInvocation;
import org.moying.common.RpcProtocol;

import java.lang.reflect.Method;

import static org.moying.common.cache.CommonServerCache.PROVIDER_CLASS_MAP;

public class ServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //服务端接收数据的时候统一以RpcProtocol协议的格式接收，具体的发送逻辑（等下方客户端发送部分）
        RpcProtocol rpcProtocol = (RpcProtocol) msg;
        String json = new String(rpcProtocol.getContent(),0,rpcProtocol.getContentLength());
//        RpcInvocation rpcInvocation = JSON.parseObject(json,RpcInvocation.class);
        Gson gson =new Gson();
        RpcInvocation rpcInvocation = gson.fromJson(json, RpcInvocation.class);
        Object aimObject = PROVIDER_CLASS_MAP.get(rpcInvocation.getTargetServiceName());
        Method[] methods = aimObject.getClass().getMethods();
        Object result = null;
    }



}
