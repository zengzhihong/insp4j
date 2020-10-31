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

package cn.is4j.insp4j.web.intercept.aopalliance;

import cn.is4j.insp4j.core.intercept.aopalliance.AbstractInspInterceptor;
import cn.is4j.insp4j.core.service.InspAuthentication;
import cn.is4j.insp4j.web.exception.DefaultInspWebExceptionTranslator;
import cn.is4j.insp4j.web.service.InspWebAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author zengzhihong
 */
@Slf4j
public class MethodInspWebInterceptor extends AbstractInspInterceptor implements MethodInterceptor {

    private final InspWebAuthenticationService authenticationService;

    public MethodInspWebInterceptor(InspWebAuthenticationService authenticationService) {
        super(new DefaultInspWebExceptionTranslator());
        this.authenticationService = authenticationService;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return super.proceed(invocation);
    }

    @Override
    protected InspAuthentication loadAuthentication(String groupName) {
        return authenticationService.loadAuthentication(
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest(), groupName);
    }

}
