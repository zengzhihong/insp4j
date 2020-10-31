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

package cn.is4j.insp.reactive.example.exception;

import cn.is4j.insp4j.core.exception.InspException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

@Component
@Order(-2)
public class ExceptionHandler implements ErrorWebExceptionHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        int code = 500;
        if (throwable instanceof InspException) {
            code = ((InspException) throwable).getCode();
        }
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.valueOf(code));
        final ObjectNode objectNode = OBJECT_MAPPER.createObjectNode();
        objectNode.put("code", code);
        objectNode.put("message", throwable.getMessage());
        DataBuffer buff = response.bufferFactory().allocateBuffer().write(OBJECT_MAPPER.writeValueAsBytes(objectNode));
        response.getHeaders().setContentType(MediaType.APPLICATION_STREAM_JSON);
        return response.writeAndFlushWith(Mono.just(ByteBufMono.just(buff)));
    }
}