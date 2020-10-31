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

package cn.is4j.insp.web.exception;

import cn.is4j.insp.core.constants.InspConst;
import cn.is4j.insp.core.exception.InspException;
import cn.is4j.insp.core.exception.InspExceptionTranslator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author zengzhihong
 */
public class DefaultInspWebExceptionTranslator implements InspExceptionTranslator {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    @Override
    public Object translate(InspException e) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        response.setCharacterEncoding(InspConst.UTF8);
        response.setContentType(InspConst.HTTP_CONTENT_TYPE_JSON);
        response.setStatus(e.getCode());
        final ObjectNode objectNode = OBJECT_MAPPER.createObjectNode();
        objectNode.put("code", e.getCode());
        objectNode.put("message", e.getMessage());
        response.getOutputStream().write(OBJECT_MAPPER.writeValueAsBytes(objectNode));
        return null;
    }
}
