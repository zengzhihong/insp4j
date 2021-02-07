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

package cn.is4j.insp.core.expression;

import cn.is4j.insp.core.service.InspAuthentication;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * @author zengzhihong
 */
public class InspExpressionRoot implements InspExpressionOperations {

    @Setter
    private InspAuthentication authentication;

    @Override
    public boolean hasFuncData(String[] funcAuthorities, String[] dataAuthorities) {
        return hasFunc(funcAuthorities) && hasData(dataAuthorities);
    }

    @Override
    public boolean hasFunc(String[] funcAuthorities) {
        return matches(true, authentication.getFuncAuthorities(), funcAuthorities);
    }

    @Override
    public boolean hasAnyFunc(String[] funcAuthorities) {
        return matches(false, authentication.getFuncAuthorities(), funcAuthorities);
    }

    @Override
    public boolean hasData(String[] dataAuthorities) {
        return matches(true, authentication.getDataAuthorities(), dataAuthorities);
    }

    @Override
    public boolean hasAnyData(String[] dataAuthorities) {
        return matches(false, authentication.getDataAuthorities(), dataAuthorities);
    }

    @Override
    public boolean hasRole(String[] roles) {
        return matches(true, authentication.getRoles(), roles);
    }

    @Override
    public boolean hasAnyRole(String[] roles) {
        return matches(false, authentication.getRoles(), roles);
    }

    private boolean matches(boolean allMatches, List<String> authenticationAuthorities,
                            String... inspAuthorities) {
        if (authentication.isHighestAuth()) {
            return true;
        }
        if (null == inspAuthorities) {
            return false;
        }
        return allMatches
                ? Arrays.stream(inspAuthorities)
                .allMatch(authenticationAuthorities::contains)
                : Arrays.stream(inspAuthorities)
                .anyMatch(authenticationAuthorities::contains);
    }

}
