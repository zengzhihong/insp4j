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

package cn.is4j.insp.core.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author zengzhihong
 */
@Getter
@Setter
public class InspAuthentication implements Serializable {

    private String id;

    private List<String> funcAuthorities;

    private List<String> dataAuthorities;

    private boolean highestAuth = false;

    public InspAuthentication(String id, List<String> funcAuthorities, List<String> dataAuthorities) {
        if (null == funcAuthorities || null == dataAuthorities) {
            throw new IllegalArgumentException("funcAuthorities and dataAuthorities cannot be null");
        }
        this.id = id;
        this.funcAuthorities = funcAuthorities;
        this.dataAuthorities = dataAuthorities;
    }

    public static InspAuthentication createHighestAuth(String id) {
        final List<String> emptyList = Collections.emptyList();
        final InspAuthentication authentication = new InspAuthentication(id, emptyList, emptyList);
        authentication.setHighestAuth(true);
        return authentication;
    }

}
