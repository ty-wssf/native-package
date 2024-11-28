package com.bcht.rminf.config;

import org.noear.solon.annotation.Component;
import org.noear.solon.aot.RuntimeNativeMetadata;
import org.noear.solon.aot.RuntimeNativeRegistrar;
import org.noear.solon.aot.hint.MemberCategory;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.util.ClassUtil;
import org.noear.solon.core.util.ScanUtil;

@Component
public class RuntimeNativeRegistrarImpl implements RuntimeNativeRegistrar {

    @Override
    public void register(AppContext context, RuntimeNativeMetadata metadata) {
        /*metadata.registerReflection(OpenApiSetting.class, MemberCategory.values());
        metadata.registerReflection(OpenApiExtendSetting.class, MemberCategory.values());*/
        // metadata.registerReflection(BaseEntity.class, MemberCategory.values());
        //扫描类文件并处理（采用两段式加载，可以部分bean先处理；剩下的为第二段处理）
        ScanUtil.scan(context.getClassLoader(), "com/bcht", n -> n.endsWith(".class"))
                .forEach(name -> {
                    String className = name.substring(0, name.length() - 6);
                    className = className.replace('/', '.');
                    Class<?> clz = ClassUtil.loadClass(context.getClassLoader(), className);
                    metadata.registerReflection(clz, MemberCategory.values());
                });

        // 注册静态资源
        metadata.registerResourceInclude("h2-database.sql");
        // metadata.registerResourceInclude("app.yml");
       //  metadata.registerResourceInclude("static/index.html");

        metadata.registerArg("-J--add-opens=java.base/java.lang.invoke=ALL-UNNAMED");
        metadata.registerArg("-J--add-opens=java.base/java.nio=ALL-UNNAMED");
        metadata.registerArg("-J--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED");

        metadata.registerArg("-march=compatibility");
    }

}
