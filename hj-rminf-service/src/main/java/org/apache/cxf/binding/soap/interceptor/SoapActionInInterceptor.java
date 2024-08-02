//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.cxf.binding.soap.interceptor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.cxf.attachment.AttachmentDeserializer;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.ws.addressing.JAXWSAConstants;

public class SoapActionInInterceptor extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(SoapActionInInterceptor.class);
    private static final String ALLOW_NON_MATCHING_TO_DEFAULT = "allowNonMatchingToDefaultSoapAction";
    private static final String CALCULATED_WSA_ACTION = SoapActionInInterceptor.class.getName() + ".ACTION";

    public SoapActionInInterceptor() {
        super("read");
        this.addAfter(ReadHeadersInterceptor.class.getName());
        this.addAfter(EndpointSelectionInterceptor.class.getName());
    }

    public static String getSoapAction(Message m) {
        if (!(m instanceof SoapMessage)) {
            return null;
        } else {
            SoapMessage message = (SoapMessage)m;
            if (message.getVersion() instanceof Soap11) {
                Map<String, List<String>> headers = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
                if (headers != null) {
                    List<String> sa = (List)headers.get("SOAPAction");
                    if (sa != null && !sa.isEmpty()) {
                        String action = (String)sa.get(0);
                        if (action.startsWith("\"") || action.startsWith("'")) {
                            action = action.substring(1, action.length() - 1);
                        }

                        return action;
                    }
                }
            } else if (message.getVersion() instanceof Soap12) {
                String ct = (String)message.get("Content-Type");
                if (ct == null) {
                    return null;
                }

                int start = ct.indexOf("action=");
                if (start == -1 && ct.indexOf("multipart/related") == 0 && ct.indexOf("start-info") == -1) {
                    List<String> cts = CastUtils.cast((List)((Map)message.get(AttachmentDeserializer.ATTACHMENT_PART_HEADERS)).get("Content-Type"));
                    if (cts != null && !cts.isEmpty()) {
                        ct = (String)cts.get(0);
                        start = ct.indexOf("action=");
                    }
                }

                if (start != -1) {
                    char c = ct.charAt(start + 7);
                    int end;
                    if (c == '"') {
                        start += 8;
                        end = ct.indexOf(34, start);
                    } else if (c == '\\' && ct.charAt(start + 8) == '"') {
                        start += 9;
                        end = ct.indexOf(92, start);
                    } else {
                        start += 7;
                        end = ct.indexOf(59, start);
                        if (end == -1) {
                            end = ct.length();
                        }
                    }

                    return ct.substring(start, end);
                }
            }

            return message.containsKey("jms.soap.action.value") ? (String)message.get("jms.soap.action.value") : null;
        }
    }

    public void handleMessage(SoapMessage message) throws Fault {
        if (!this.isRequestor(message)) {
            String action = getSoapAction(message);
            if (!StringUtils.isEmpty(action)) {
                getAndSetOperation(message, action);
                message.put("SOAPAction", action);
            }

        }
    }

    public static void getAndSetOperation(SoapMessage message, String action) {
        getAndSetOperation(message, action, true);
    }

    public static void getAndSetOperation(SoapMessage message, String action, boolean strict) {
        if (!StringUtils.isEmpty(action)) {
            Exchange ex = message.getExchange();
            Endpoint ep = ex.getEndpoint();
            if (ep != null) {
                BindingOperationInfo bindingOp = null;
                Collection<BindingOperationInfo> bops = ep.getEndpointInfo().getBinding().getOperations();
                if (bops != null) {
                    Iterator var7 = bops.iterator();

                    while(var7.hasNext()) {
                        BindingOperationInfo boi = (BindingOperationInfo)var7.next();
                        if (isActionMatch(message, boi, action)) {
                            if (bindingOp != null) {
                                return;
                            }

                            bindingOp = boi;
                        }

                        if (matchWSAAction(boi, action)) {
                            if (bindingOp != null && bindingOp != boi) {
                                return;
                            }

                            bindingOp = boi;
                        }
                    }
                }

                if (bindingOp == null) {
                    if (strict) {
                        message.getInterceptorChain().add(new SoapActionInAttemptTwoInterceptor(action));
                    }

                } else {
                    ex.put(BindingOperationInfo.class, bindingOp);
                }
            }
        }
    }

    private static boolean matchWSAAction(BindingOperationInfo boi, String action) {
        Object o = getWSAAction(boi);
        if (o != null) {
            String oa = o.toString();
            if (action.equals(oa) || action.equals(oa + "Request") || oa.equals(action + "Request")) {
                return true;
            }
        }

        return false;
    }

    private static String getWSAAction(BindingOperationInfo boi) {
        Object o = boi.getOperationInfo().getInput().getProperty(CALCULATED_WSA_ACTION);
        if (o == null) {
            o = boi.getOperationInfo().getInput().getExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME);
            if (o == null) {
                o = boi.getOperationInfo().getInput().getExtensionAttribute(JAXWSAConstants.WSAW_ACTION_QNAME);
            }

            if (o == null) {
                String start = getActionBaseUri(boi.getOperationInfo());
                if (null == boi.getOperationInfo().getInputName()) {
                    o = addPath(start, boi.getOperationInfo().getName().getLocalPart());
                } else {
                    o = addPath(start, boi.getOperationInfo().getInputName());
                }
            }

            if (o != null) {
                boi.getOperationInfo().getInput().setProperty(CALCULATED_WSA_ACTION, o);
            }
        }

        return o.toString();
    }

    private static String getActionBaseUri(OperationInfo operation) {
        String interfaceName = operation.getInterface().getName().getLocalPart();
        return addPath(operation.getName().getNamespaceURI(), interfaceName);
    }

    private static String getDelimiter(String uri) {
        return uri.startsWith("urn") ? ":" : "/";
    }

    private static String addPath(String uri, String path) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(uri);
        String delimiter = getDelimiter(uri);
        if (!uri.endsWith(delimiter) && !path.startsWith(delimiter)) {
            buffer.append(delimiter);
        }

        buffer.append(path);
        return buffer.toString();
    }

    private static boolean isActionMatch(SoapMessage message, BindingOperationInfo boi, String action) {
        SoapOperationInfo soi = (SoapOperationInfo)boi.getExtensor(SoapOperationInfo.class);
        if (soi == null) {
            return false;
        } else {
            boolean allowNoMatchingToDefault = MessageUtils.getContextualBoolean(message, "allowNonMatchingToDefaultSoapAction", false);
            return action.equals(soi.getAction()) || allowNoMatchingToDefault && StringUtils.isEmpty(soi.getAction()) || message.getVersion() instanceof Soap12 && StringUtils.isEmpty(soi.getAction());
        }
    }

    public static class SoapActionInAttemptTwoInterceptor extends AbstractSoapInterceptor {
        final String action;

        public SoapActionInAttemptTwoInterceptor(String action) {
            super(action, "pre-logical");
            this.action = action;
        }

        public void handleMessage(SoapMessage message) throws Fault {
            BindingOperationInfo boi = message.getExchange().getBindingOperationInfo();
            if (boi != null) {
                if (!StringUtils.isEmpty(this.action)) {
                    if (!SoapActionInInterceptor.isActionMatch(message, boi, this.action)) {
                        if (!SoapActionInInterceptor.matchWSAAction(boi, this.action)) {
                            boolean synthetic = Boolean.TRUE.equals(boi.getProperty("operation.is.synthetic"));
                            if (!synthetic) {
                                // throw new Fault("SOAP_ACTION_MISMATCH", SoapActionInInterceptor.LOG, (Throwable)null, new Object[]{this.action});
                            }
                        }
                    }
                }
            }
        }
    }
}
