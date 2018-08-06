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

 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelFuture;
 import io.netty.channel.ChannelFutureListener;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.ChannelInboundHandlerAdapter;

 /**
  * Project: netty-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-08-06 08:59
  */
 public class TimeServerHandler extends ChannelInboundHandlerAdapter {

     @Override
     public void channelActive(final ChannelHandlerContext ctx) {
         final ByteBuf time = ctx.alloc().buffer(4);
         time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

         final ChannelFuture f = ctx.writeAndFlush(time);
         f.addListener(ChannelFutureListener.CLOSE);
         //         f.addListener(new ChannelFutureListener() {
         //             @Override
         //             public void operationComplete(ChannelFuture future) {
         //                 assert f == future;
         //                 ctx.close();
         //             }
         //         });
     }

     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
         cause.printStackTrace();
         ctx.close();
     }
 }
