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

 import java.util.Date;

 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.ChannelInboundHandlerAdapter;

 /**
  * Project: netty-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-08-06 09:22
  */
 public class TimeClientHandler extends ChannelInboundHandlerAdapter {
     @Override
     public void channelRead(ChannelHandlerContext ctx, Object msg) {
         ByteBuf m = (ByteBuf) msg;
         try {
             long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
             System.out.println(new Date(currentTimeMillis));
             ctx.close();
         } finally {
             m.release();
         }
     }

     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
         cause.printStackTrace();
         ctx.close();
     }

 }
