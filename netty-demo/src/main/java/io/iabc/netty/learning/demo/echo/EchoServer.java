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

 package io.iabc.netty.learning.demo.echo;

 import io.netty.bootstrap.ServerBootstrap;
 import io.netty.channel.ChannelFuture;
 import io.netty.channel.ChannelInitializer;
 import io.netty.channel.ChannelOption;
 import io.netty.channel.EventLoopGroup;
 import io.netty.channel.nio.NioEventLoopGroup;
 import io.netty.channel.socket.SocketChannel;
 import io.netty.channel.socket.nio.NioServerSocketChannel;

 /**
  * Project: netty-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-08-06 08:16
  */
 public class EchoServer {

     private int port;

     public EchoServer(int port) {
         this.port = port;
     }

     public void run() throws Exception {
         EventLoopGroup bossGroup = new NioEventLoopGroup();
         EventLoopGroup workerGroup = new NioEventLoopGroup();
         try {
             ServerBootstrap b = new ServerBootstrap();
             b.group(bossGroup, workerGroup)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     public void initChannel(SocketChannel ch) throws Exception {
                         ch.pipeline().addLast(new EchoServerHandler());
                     }
                 })
                 .option(ChannelOption.SO_BACKLOG, 128)
                 .childOption(ChannelOption.SO_KEEPALIVE, true);

             // Bind and start to accept incoming connections.
             ChannelFuture f = b.bind(port).sync();

             // Wait until the server socket is closed.
             // In this example, this does not happen, but you can do that to gracefully
             // shut down your server.
             f.channel().closeFuture().sync();
         } finally {
             workerGroup.shutdownGracefully();
             bossGroup.shutdownGracefully();
         }
     }

     public static void main(String[] args) throws Exception {
         int port;
         if (args.length > 0) {
             port = Integer.parseInt(args[0]);
         } else {
             port = 8081;
         }
         new EchoServer(port).run();
     }

 }
