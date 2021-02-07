/*
 * Copyright 2020-2021 zengzhihong All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.is4j.insp.core.context;

/**
 * @author zengzhihong
 */
public class ThreadLocalInspContextHolderStrategy implements InspContextHolderStrategy {

    private static final ThreadLocal<InspContext> CONTEXT_HOLDER = new InheritableThreadLocal<>();

    @Override
    public void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    @Override
    public InspContext getContext() {
        InspContext ctx = CONTEXT_HOLDER.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            CONTEXT_HOLDER.set(ctx);
        }
        return ctx;
    }

    @Override
    public void setContext(InspContext context) {
        if (null == context) {
            throw new IllegalArgumentException(
                    "Only non-null InspContext instances are permitted");
        }
        CONTEXT_HOLDER.set(context);
    }

    @Override
    public InspContext createEmptyContext() {
        return new InspContextImpl();
    }
}
