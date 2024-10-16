package com.hj.webservices.integration;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.soap.*;
import org.noear.snack.core.utils.StringUtil;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Bean;
import org.noear.solon.core.BeanBuilder;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.solon.core.handle.MethodHandler;
import org.noear.solon.core.wrap.ClassWrap;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyl
 * @date 2024年08月04日 8:39
 */
public class WebServiceBeanBuilder implements BeanBuilder<WebService> {

    public static final WebServiceBeanBuilder instance = new WebServiceBeanBuilder();

    public WebServiceBeanBuilder() {
    }

    public void doBuild(Class<?> clz, BeanWrap bw, WebService anno) throws Throwable {
        if (!clz.isInterface()) {
            for (Method m : ClassWrap.get(bw.clz()).getDeclaredMethods()) {
                String operationName = m.getName();
                WebMethod webMethod = m.getAnnotation(WebMethod.class);
                if (webMethod != null && !StringUtil.isEmpty(webMethod.operationName())) {
                    operationName = webMethod.operationName();
                }
                BeanWrap beanWrap = Solon.context().wrap(anno.targetNamespace() + "_" + anno.serviceName() + "_" + operationName,
                        new MethodHandler(bw, m, true));
                Solon.context().putWrap(anno.targetNamespace() + "_" + anno.serviceName() + "_" + operationName, beanWrap);
            }
        }

    }

}
