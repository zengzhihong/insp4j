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

package cn.is4j.insp.reactive.intercept.aopalliance;

import cn.is4j.insp.core.expression.InspMetadataSource;
import cn.is4j.insp.core.intercept.aopalliance.AbstractInspInterceptor;
import cn.is4j.insp.core.service.InspAuthentication;
import cn.is4j.insp.reactive.ReactiveRequestContextHolder;
import cn.is4j.insp.reactive.exception.DefaultInspReactiveExceptionTranslator;
import cn.is4j.insp.reactive.service.InspReactiveAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author zengzhihong
 */
@Slf4j
public class MethodInspReactiveInterceptor extends AbstractInspInterceptor
        implements MethodInterceptor {

    private final InspReactiveAuthenticationService authenticationService;

    public MethodInspReactiveInterceptor(
            InspReactiveAuthenticationService authenticationService) {
        super(new DefaultInspReactiveExceptionTranslator());
        this.authenticationService = authenticationService;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return super.proceed(invocation);
    }

    @Override
    public InspAuthentication loadAuthentication(InspMetadataSource metadataSource) {
        return authenticationService.loadAuthentication(
                ReactiveRequestContextHolder.getRequest(), metadataSource);
    }
}
