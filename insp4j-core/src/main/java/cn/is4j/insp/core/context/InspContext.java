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

package cn.is4j.insp.core.context;

import cn.is4j.insp.core.constants.InspConst;
import cn.is4j.insp.core.service.InspAuthentication;

import java.io.Serializable;

/**
 * @author zengzhihong
 */
public interface InspContext extends Serializable {


    /**
     * default groupName
     *
     * @return
     */
    default InspAuthentication getAuthentication() {
        return getAuthentication(InspConst.DEFAULT_GROUP_NAME);
    }

    /**
     * get by groupName
     *
     * @param groupName
     * @return
     */
    InspAuthentication getAuthentication(String groupName);


    void setAuthentication(String groupName, InspAuthentication authentication);
}
