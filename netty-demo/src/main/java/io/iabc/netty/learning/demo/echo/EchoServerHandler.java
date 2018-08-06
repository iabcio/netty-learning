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

 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.ChannelInboundHandlerAdapter;

 /**
  * Project: netty-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-08-06 08:05
  */
 public class EchoServerHandler extends ChannelInboundHandlerAdapter {
     @Override
     public void channelRead(ChannelHandlerContext ctx, Object msg) {
         ByteBuf in = (ByteBuf) msg;
         in.markReaderIndex();
         while (in.isReadable()) {
             System.out.print((char) in.readByte());
             System.out.flush();
         }

         in.resetReaderIndex();
         ctx.write(in);
         ctx.flush();
     }

     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
         // Close the connection when an exception is raised.
         cause.printStackTrace();
         ctx.close();
     }
 }
