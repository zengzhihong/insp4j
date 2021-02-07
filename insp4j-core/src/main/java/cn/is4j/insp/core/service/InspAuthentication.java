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

    private List<String> roles;

    private boolean highestAuth = false;

    public InspAuthentication() {
    }

    public InspAuthentication(String id, List<String> funcAuthorities, List<String> dataAuthorities) {
        this(id, funcAuthorities, dataAuthorities, null);
    }

    public InspAuthentication(String id, List<String> funcAuthorities,
                              List<String> dataAuthorities, List<String> roles) {
        this.id = id;
        this.funcAuthorities = defaultEmptyListIfNull(funcAuthorities);
        this.dataAuthorities = defaultEmptyListIfNull(dataAuthorities);
        this.roles = defaultEmptyListIfNull(roles);
    }

    public static InspAuthentication createHighestAuth(String id) {
        final InspAuthentication authentication = new InspAuthentication(id, null, null);
        authentication.setHighestAuth(true);
        return authentication;
    }

    private List<String> defaultEmptyListIfNull(List<String> source) {
        return null == source ? Collections.emptyList() : source;
    }

}
