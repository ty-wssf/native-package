package com.tmri.tfc.webservice;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.bean.LifecycleBean;
import org.noear.solon.core.runtime.NativeDetector;

import java.nio.charset.StandardCharsets;

/**
 * @author wyl
 * @date 2024年08月03日 7:05
 */
@Component
public class RabbitmqLifecycleBean implements LifecycleBean {

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Channel channel;
    private AMQP.BasicProperties eventPropsDefault;

    @Override
    public void start() throws Throwable {
        if (!Solon.cfg().getBool("solon.aot", false)) {
            String host = Solon.cfg().get("solon.cloud.rabbitmq.server").split(":")[0];
            int port = Integer.parseInt(Solon.cfg().get("solon.cloud.rabbitmq.server").split(":")[1]);

            connectionFactory = new ConnectionFactory();

            // 配置连接信息
            connectionFactory.setHost(host);
            connectionFactory.setPort(port);
            connectionFactory.setRequestedHeartbeat(30);

            String username = Solon.cfg().get("solon.cloud.rabbitmq.username");
            if (Utils.isNotEmpty(username)) {
                connectionFactory.setUsername(username);
            }
            String password = Solon.cfg().get("solon.cloud.rabbitmq.password");
            if (Utils.isNotEmpty(password)) {
                connectionFactory.setPassword(password);
            }

            // 网络异常自动连接恢复
            connectionFactory.setAutomaticRecoveryEnabled(true);
            // 每5秒尝试重试连接一次
            connectionFactory.setNetworkRecoveryInterval(5000L);

            //创建连接
            connection = connectionFactory.newConnection();

            channel = connection.createChannel();
            // 启用消息确认
            channel.confirmSelect();
            eventPropsDefault = newEventProps().build();
        }
    }

    public boolean basicPublish(String exchangeName, String topic, String event_data) throws Exception {
        channel.basicPublish(exchangeName, topic, false, eventPropsDefault, event_data.getBytes(StandardCharsets.UTF_8));
        return channel.waitForConfirms();
    }

    public AMQP.BasicProperties.Builder newEventProps() {
        return new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .contentType("application/json");
    }

}
