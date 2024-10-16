package com.hj.rminf.app;

import io.nop.core.lang.xml.parse.XNodeParser;
import io.nop.core.model.object.DynamicObject;
import io.nop.excel.imp.model.ImportFieldModel;
import io.nop.excel.imp.model.ImportModel;
import io.nop.excel.imp.model.ImportSheetModel;
import io.nop.excel.imp.util.ImportDataHelper;
import io.nop.rule.core.model.*;
import io.nop.sys.service.entity.NopSysCheckerRecordBizModel;
import io.nop.xlang.feature.XModelInclude;
import org.noear.solon.annotation.Component;
import org.noear.solon.aot.RuntimeNativeMetadata;
import org.noear.solon.aot.RuntimeNativeRegistrar;
import org.noear.solon.aot.hint.MemberCategory;
import org.noear.solon.core.AppContext;

/**
 * @author wyl
 * @date 2024年08月11日 9:00
 */
@Component
public class MyRuntimeNativeRegistrar implements RuntimeNativeRegistrar {

    @Override
    public void register(AppContext context, RuntimeNativeMetadata metadata) {
        metadata.registerReflection(RuleAggregateMethod.class, MemberCategory.values());
        metadata.registerReflection(ImportModel.class, MemberCategory.values());
        metadata.registerReflection(ImportSheetModel.class, MemberCategory.values());
        metadata.registerReflection(ImportFieldModel.class, MemberCategory.values());
        metadata.registerReflection(ImportDataHelper.class, MemberCategory.values());
        metadata.registerReflection(DynamicObject.class, MemberCategory.values());
        metadata.registerReflection(RuleModel.class, MemberCategory.values());
        metadata.registerReflection(RuleInputDefineModel.class, MemberCategory.values());
        metadata.registerReflection(RuleOutputDefineModel.class, MemberCategory.values());
        metadata.registerReflection(RuleDecisionTreeModel.class, MemberCategory.values());
        metadata.registerReflection(RuleOutputValueModel.class, MemberCategory.values());

        metadata.registerReflection(NopSysCheckerRecordBizModel.class, MemberCategory.values());
        metadata.registerReflection(XNodeParser.class, MemberCategory.values());
        metadata.registerReflection(XModelInclude.class, MemberCategory.values());
        // metadata.registerReflection(AuthFilterConfig.class, MemberCategory.values());

        metadata.registerArg("-J--add-opens=java.base/java.lang.invoke=ALL-UNNAMED");
        metadata.registerArg("-J--add-opens=java.base/java.nio=ALL-UNNAMED");
        metadata.registerArg("-J--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED");

        metadata.registerArg("-march=compatibility");

    }

}
