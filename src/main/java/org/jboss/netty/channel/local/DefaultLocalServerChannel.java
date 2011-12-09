/*
 * Copyright 2009 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.local;

import static io.netty.channel.Channels.*;

import java.util.concurrent.atomic.AtomicBoolean;

import io.netty.channel.AbstractServerChannel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelSink;
import io.netty.channel.DefaultServerChannelConfig;

/**
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author Andy Taylor (andy.taylor@jboss.org)
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 */
final class DefaultLocalServerChannel extends AbstractServerChannel implements
        LocalServerChannel {

    final ChannelConfig channelConfig;

    final AtomicBoolean bound = new AtomicBoolean();

    volatile LocalAddress localAddress;

    static DefaultLocalServerChannel create(ChannelFactory factory,
            ChannelPipeline pipeline, ChannelSink sink) {
        DefaultLocalServerChannel instance =
                new DefaultLocalServerChannel(factory, pipeline, sink);
        fireChannelOpen(instance);
        return instance;
    }

    private DefaultLocalServerChannel(ChannelFactory factory,
            ChannelPipeline pipeline, ChannelSink sink) {
        super(factory, pipeline, sink);
        channelConfig = new DefaultServerChannelConfig();
    }

    @Override
    public ChannelConfig getConfig() {
        return channelConfig;
    }

    @Override
    public boolean isBound() {
        return isOpen() && bound.get();
    }

    @Override
    public LocalAddress getLocalAddress() {
        return isBound() ? localAddress : null;
    }

    @Override
    public LocalAddress getRemoteAddress() {
        return null;
    }

    @Override
    protected boolean setClosed() {
        return super.setClosed();
    }
}
