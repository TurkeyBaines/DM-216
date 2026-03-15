package org.dm;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.IoHandlerFactory;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollIoHandler;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueIoHandler;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.uring.IoUring;
import io.netty.channel.uring.IoUringIoHandler;
import io.netty.channel.uring.IoUringServerSocketChannel;
import io.netty.channel.ServerChannel;

/**
 * @author Jire
 * @author Kris
 */
public class BootstrapFactory {

    private final ByteBufAllocator alloc;

    public BootstrapFactory() {
        this(PooledByteBufAllocator.DEFAULT);
    }

    public BootstrapFactory(ByteBufAllocator alloc) {
        this.alloc = alloc;
    }

    /**
     * Creates an IO handler factory based on the best available event loop group.
     */
    public IoHandlerFactory createIoHandlerFactory() {
        if (IoUring.isAvailable()) {
            return IoUringIoHandler.newFactory();
        } else if (Epoll.isAvailable()) {
            return EpollIoHandler.newFactory();
        } else if (KQueue.isAvailable()) {
            return KQueueIoHandler.newFactory();
        } else {
            return NioIoHandler.newFactory();
        }
    }

    /**
     * Creates a parent loop group with a single thread behind it.
     */
    public EventLoopGroup createParentLoopGroup() {
        return createParentLoopGroup(1);
    }

    public EventLoopGroup createParentLoopGroup(int nThreads) {
        return new MultiThreadIoEventLoopGroup(nThreads, createIoHandlerFactory());
    }

    /**
     * Creates a child loop group with a number of threads (0 = Netty default).
     */
    public EventLoopGroup createChildLoopGroup() {
        return createChildLoopGroup(0);
    }

    public EventLoopGroup createChildLoopGroup(int nThreads) {
        return new MultiThreadIoEventLoopGroup(nThreads, createIoHandlerFactory());
    }

    /**
     * Creates a server bootstrap using the parent and child event loop groups.
     */
    public ServerBootstrap createServerBootstrap(
            EventLoopGroup parentGroup,
            EventLoopGroup childGroup
    ) {
        Class<? extends ServerChannel> channelClass;

        if (IoUring.isAvailable()) {
            channelClass = IoUringServerSocketChannel.class;
        } else if (Epoll.isAvailable()) {
            channelClass = EpollServerSocketChannel.class;
        } else if (KQueue.isAvailable()) {
            channelClass = KQueueServerSocketChannel.class;
        } else {
            channelClass = NioServerSocketChannel.class;
        }

        return new ServerBootstrap()
                .group(parentGroup, childGroup)
                .channel(channelClass)
                .option(ChannelOption.ALLOCATOR, alloc)
                .childOption(ChannelOption.ALLOCATOR, alloc)
                .childOption(ChannelOption.AUTO_READ, false)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_RCVBUF, 65536)
                .childOption(ChannelOption.SO_SNDBUF, 65536)
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(524288, 2097152));
    }
}