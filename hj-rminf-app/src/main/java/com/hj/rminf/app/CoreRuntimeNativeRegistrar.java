package com.hj.rminf.app;

import org.noear.solon.annotation.Component;
import org.noear.solon.aot.RuntimeNativeMetadata;
import org.noear.solon.aot.RuntimeNativeRegistrar;
import org.noear.solon.core.AppContext;

/**
 * @author wyl
 * @date 2024年08月03日 8:45
 */
@Component
public class CoreRuntimeNativeRegistrar implements RuntimeNativeRegistrar {

    @Override
    public void register(AppContext context, RuntimeNativeMetadata metadata) {
        // jdk.internal.reflect;
        metadata.registerArg("-J--add-opens=java.base/java.lang.invoke=ALL-UNNAMED");
        metadata.registerArg("-J--add-opens=java.base/java.nio=ALL-UNNAMED");
        metadata.registerArg("-J--add-opens=java.base/jdk.internal.reflect=ALL-UNNAMED");
        metadata.registerArg("-J--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED");
        metadata.registerArg("-J--add-exports=java.base/jdk.internal.reflect=ALL-UNNAMED");

        metadata.registerArg("-march=compatibility");
    }

}
