/*
 * Copyright 2020 zengzhihong All rights reserved.
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

package cn.is4j.insp.core.expression.method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * @author zengzhihong
 */
public class MethodInspEvaluationContext extends MethodBasedEvaluationContext {

    public MethodInspEvaluationContext(MethodInvocation invocation,
                                       ParameterNameDiscoverer parameterNameDiscoverer) {
        super(invocation.getThis(), getSpecificMethod(invocation),
                invocation.getArguments(), parameterNameDiscoverer);
    }

    private static Method getSpecificMethod(MethodInvocation mi) {
        return AopUtils.getMostSpecificMethod(mi.getMethod(),
                AopProxyUtils.ultimateTargetClass(mi.getThis()));
    }
}
