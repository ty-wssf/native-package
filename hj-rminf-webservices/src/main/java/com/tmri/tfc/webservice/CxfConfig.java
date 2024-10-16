package com.tmri.tfc.webservice;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wyl
 * @date 2024年08月01日 15:50
 */
@Configuration
public class CxfConfig {

    @Autowired
    private TransWebService transWebService;

    @Autowired
    private Bus bus;

    @Bean
    public ServletRegistrationBean wsServlet(){
        return new ServletRegistrationBean(new CXFServlet(), "/rminf/services/*");
    }

    /**
     * 发布服务
     * @return
     */
    @Bean
    public EndpointImpl userServiceEndpoint() {
        System.out.println("服务发布");

        EndpointImpl userEndpoint = new EndpointImpl(bus, transWebService);
        userEndpoint.publish("/Trans");

        return userEndpoint;
    }

}
