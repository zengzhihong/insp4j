package cn.is4j.insp.core.intercept.aopalliance;

import cn.is4j.insp.core.expression.InspMetadataSource;
import cn.is4j.insp.core.service.InspAuthentication;

/**
 * @author zengzhihong
 */
public interface InspInterceptor {

	InspAuthentication onAuthentication(InspMetadataSource metadataSource);
}
