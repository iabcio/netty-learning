 /*
  * Copyright 2017-2018 Iabc Co.Ltd.
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

 package io.iabc.netty.learning.demo.time;

 import io.netty.bootstrap.Bootstrap;
 import io.netty.channel.ChannelFuture;
 import io.netty.channel.ChannelInitializer;
 import io.netty.channel.ChannelOption;
 import io.netty.channel.EventLoopGroup;
 import io.netty.channel.nio.NioEventLoopGroup;
 import io.netty.channel.socket.SocketChannel;
 import io.netty.channel.socket.nio.NioSocketChannel;

 /**
  * Project: netty-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-08-06 09:21
  */
 public class TimeClient {

     public static void main(String[] args) throws Exception {
         //         final String host = args[0];
         //         final int port = Integer.parseInt(args[1]);
         final String host = "127.0.0.1";
         final int port = 8082;
         EventLoopGroup workerGroup = new NioEventLoopGroup();

         try {
             Bootstrap b = new Bootstrap();
             b.group(workerGroup);
             b.channel(NioSocketChannel.class);
             b.option(ChannelOption.SO_KEEPALIVE, true);
             b.handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new TimeClientHandler());
                 }
             });

             // Start the client.
             ChannelFuture f = b.connect(host, port).sync();

             // Wait until the connection is closed.
             f.channel().closeFuture().sync();
         } finally {
             workerGroup.shutdownGracefully();
         }
     }
 }
