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

package cn.is4j.insp.core.intercept.aopalliance;

import cn.is4j.insp.core.annotation.Insp;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.util.Assert;

/**
 * @author zengzhihong
 */
public class MethodInspAnnotationAdvisor extends AbstractPointcutAdvisor {

    private final MethodInterceptor interceptor;
    private final Pointcut pointcut;

    public MethodInspAnnotationAdvisor(MethodInterceptor interceptor) {
        Assert.notNull(interceptor, "The advice cannot be null");
        this.interceptor = interceptor;
        this.pointcut = buildPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return interceptor;
    }

    private Pointcut buildPointcut() {
        ComposablePointcut result;
        // class annotation
        Pointcut cpc = new AnnotationMatchingPointcut(Insp.class, true);
        // method annotation
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(Insp.class);
        // composable class method
        result = new ComposablePointcut(cpc);
        return result.union(mpc);
    }
}
