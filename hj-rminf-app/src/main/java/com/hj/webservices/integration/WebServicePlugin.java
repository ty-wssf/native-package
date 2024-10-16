package com.hj.webservices.integration;

import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.ApiStringHelper;
import jakarta.jws.WebService;
import jakarta.xml.soap.*;
import org.noear.solon.Solon;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.solon.core.handle.MethodHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author wyl
 * @date 2024年08月04日 8:39
 */
public class WebServicePlugin implements Plugin {

    private Logger log = LoggerFactory.getLogger(WebServicePlugin.class);

    @Override
    public void start(AppContext context) throws Throwable {
        context.beanBuilderAdd(WebService.class, WebServiceBeanBuilder.instance);

        Solon.app().http(Solon.cfg().get("server.webservices.path") + "*", new Handler() {
            @Override
            public void handle(Context ctx) throws Throwable {
                SOAPElement rootElement = null;
                try {
                    // 创建SOAPMessage
                    MessageFactory messageFactory = MessageFactory.newInstance();
                    SOAPMessage soapMessage = messageFactory.createMessage(new MimeHeaders(), new ByteArrayInputStream(ctx.body().getBytes(StandardCharsets.UTF_8)));
                    SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
                    SOAPBody soapBody = envelope.getBody();

                    // 获取体部的根元素
                    rootElement = (SOAPElement) soapBody.getFirstChild();
                    // 获取根元素的命名空间URI（如果有的话）
                    String namespaceURI = rootElement.getNamespaceURI();
                    String localName = rootElement.getLocalName();
                    String[] paths = ctx.pathNew().split("/");
                    String serviceName = paths[paths.length - 1];
                    // 设置值
                    // ctx.paramMap().put()
                    rootElement.getChildElements().forEachRemaining(node -> {
                        ctx.paramMap().put(node.getNodeName(), node.getTextContent());
                    });
                    MethodHandler methodHandler = Solon.context().getBean((namespaceURI == null ? "" : namespaceURI) + "_" + serviceName + "_" + localName);
                    methodHandler.handle(ctx);

                    // 创建SOAP响应消息
                    response(ctx, rootElement);
                } catch (Exception e) {
                    log.error("webservices exception", e);
                    ctx.result = e.getMessage();
                    response(ctx, rootElement);
                }
            }
        });
    }

    static void response(Context ctx, SOAPElement rootElement) throws Throwable {
        // 创建MessageFactory
        MessageFactory messageFactory = MessageFactory.newInstance();

        // 创建SOAPMessage
        SOAPMessage soapResponseMessage = messageFactory.createMessage();

        // 获取SOAPPart
        SOAPPart soapResponsePart = soapResponseMessage.getSOAPPart();

        // 获取SOAPEnvelope
        SOAPEnvelope responseEnvelope = soapResponsePart.getEnvelope();

        // 可选：添加命名空间声明（通常SOAP Envelope已经有默认的命名空间声明了）
        // responseEnvelope.addNamespaceDeclaration("soap", "http://schemas.xmlsoap.org/soap/envelope/");

        // 创建SOAPBody
        SOAPBody soapResponseBody = responseEnvelope.getBody();

        // 添加响应元素到SOAPBody
        // 注意：前缀通常与命名空间URI相关联，但在这里我们留空（无前缀）
        SOAPElement responseElement = soapResponseBody.addChildElement(rootElement.getLocalName() + "Response", "", rootElement.getNamespaceURI());

        // 添加一些子元素到响应元素（可选）
        SOAPElement childElement = responseElement.addChildElement(rootElement.getLocalName() + "Result", "", rootElement.getNamespaceURI());

        childElement.addTextNode(ctx.result.toString());

        soapResponseMessage.writeTo(ctx.outputStream());
        // ctx.render(xml);
    }

}
