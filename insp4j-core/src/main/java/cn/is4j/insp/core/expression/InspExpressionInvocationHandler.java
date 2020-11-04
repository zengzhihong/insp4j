package cn.is4j.insp.core.expression;

import cn.is4j.insp.core.intercept.aopalliance.InspInterceptor;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@AllArgsConstructor
public class InspExpressionInvocationHandler implements InvocationHandler {

    private InspExpressionOperations operations;
    private InspInterceptor inspInterceptor;
    private InspMetadataSource metadataSource;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        metadataSource.setExpressionArgs(args);
        operations.setAuthentication(inspInterceptor.onAuthentication(metadataSource));
        return method.invoke(operations, args);
    }
}