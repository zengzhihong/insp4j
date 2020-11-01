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

package cn.is4j.insp.core.expression;

import cn.is4j.insp.core.expression.method.MethodInspEvaluationContext;
import cn.is4j.insp.core.service.InspAuthentication;
import lombok.Getter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author zengzhihong
 */
public class DefaultMethodInspExpressionHandler extends AbstractInspExpressionHandler<MethodInvocation> {

    @Getter
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public DefaultMethodInspExpressionHandler() {
    }

    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(InspAuthentication authentication,
                                                                        MethodInvocation invocation) {
        return new MethodInspEvaluationContext(invocation, getParameterNameDiscoverer());
    }

    @Override
    protected InspExpressionOperations createInspExpressionRoot(InspAuthentication authentication,
                                                                MethodInvocation invocation) {
        return new InspExpressionRoot(authentication);
    }


}
