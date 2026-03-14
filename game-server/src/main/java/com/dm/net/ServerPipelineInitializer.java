package com.dm.net;

import com.dm.Config;
import com.dm.Starter;
import com.dm.net.codec.login.LoginDecoder;
import com.dm.net.codec.login.LoginResponseEncoder;
import com.dm.net.session.Session;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.haproxy.HAProxyMessageDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * The {@link ChannelInitializer} implementation that will setup the games networking pipeline.
 *
 * @author nshusa
 */
public final class ServerPipelineInitializer extends ChannelInitializer<SocketChannel> {

    private final Starter starter;

    public ServerPipelineInitializer(Starter starter) {
        this.starter = starter;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        final ChannelPipeline pipeline = ch.pipeline();
        ch.attr(Config.SESSION_KEY).setIfAbsent(new Session(ch));

        pipeline.addLast("timeout", new IdleStateHandler(Config.IDLE_TIMEOUT, 0, 0));

        if (Config.SUPPORT_HAPROXY) {
            pipeline.addLast("haproxy", new HAProxyMessageDecoder());
            pipeline.addLast("haproxy-handler", new HAProxyMessageHandler());
        }

        pipeline.addLast("login-decoder", new LoginDecoder(starter));
        pipeline.addLast("login-encoder", new LoginResponseEncoder());
        pipeline.addLast("channel-handler", new ChannelHandler());
    }

}
