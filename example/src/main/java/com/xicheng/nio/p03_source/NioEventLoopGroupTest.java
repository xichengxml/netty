package com.xicheng.nio.p03_source;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.util.concurrent.RejectedExecutionHandlers;

import java.nio.channels.spi.SelectorProvider;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/2/11 下午 07:19
 */
public class NioEventLoopGroupTest {

    public static void main(String[] args) throws Exception {
        singletonTest();
    }

    public static void singletonTest() throws Exception {
        // SelectorProvider.provider()
        System.out.println(SelectorProvider.provider());
        System.out.println(SelectorProvider.provider());
        // DefaultSelectStrategyFactory.INSTANCE
        System.out.println(DefaultSelectStrategyFactory.INSTANCE);
        System.out.println(DefaultSelectStrategyFactory.INSTANCE);
        // RejectedExecutionHandlers.reject()
        System.out.println(RejectedExecutionHandlers.reject());
        System.out.println(RejectedExecutionHandlers.reject());
        // 查看一下selector是否单例
        System.out.println(SelectorProvider.provider().openSelector());
        System.out.println(SelectorProvider.provider().openSelector());
    }
}
