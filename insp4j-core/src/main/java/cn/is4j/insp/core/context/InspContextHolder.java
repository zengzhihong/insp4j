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

/**
 * @author zengzhihong
 */
public class InspContextHolder {

    private static InspContextHolderStrategy strategy;
    private static int initializeCount = 0;

    static {
        initialize();
    }

    private static void initialize() {
        strategy = new ThreadLocalInspContextHolderStrategy();
        initializeCount++;
    }

    /**
     * Primarily for troubleshooting purposes, this method shows how many times the class
     *
     * @return initializeCount
     */
    public static int getInitializeCount() {
        return initializeCount;
    }

    public static void clearContext() {
        strategy.clearContext();
    }

    public static InspContext getContext() {
        return strategy.getContext();
    }

    public static void setContext(InspContext context) {
        strategy.setContext(context);
    }

    public static InspContext createEmptyContext(String groupName) {
        return strategy.createEmptyContext();
    }
}
