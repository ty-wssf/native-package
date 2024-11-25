package com.bcht.rminf.config;

import com.bcht.rminf.modules.demo.model.Author;
import com.bcht.rminf.modules.demo.model.Book;
import com.bcht.rminf.modules.demo.model.BookStore;
import com.bcht.rminf.modules.demo.model.TreeNode;
import com.bcht.rminf.modules.demo.model.common.BaseEntity;
import com.bcht.rminf.modules.hkpostgresql.model.WarningInstanceInfo;
import com.bcht.rminf.modules.hkpostgresql.model.WarningInstanceInfoDraft;
import com.bcht.rminf.modules.hkpostgresql.model.WeatherInfo;
import com.bcht.rminf.modules.hkpostgresql.model.WeatherInfoDraft;
import com.bcht.rminf.modules.sys.model.SysConfig;
import com.bcht.rminf.modules.sys.model.SysConfigDraft;
import com.bcht.rminf.modules.sys.model.SysRelation;
import com.bcht.rminf.modules.sys.model.SysRelationDraft;
import com.github.xiaoymin.knife4j.solon.settings.OpenApiExtendSetting;
import com.github.xiaoymin.knife4j.solon.settings.OpenApiSetting;
import org.noear.solon.annotation.Component;
import org.noear.solon.aot.RuntimeNativeMetadata;
import org.noear.solon.aot.RuntimeNativeRegistrar;
import org.noear.solon.aot.hint.MemberCategory;
import org.noear.solon.core.AppContext;

@Component
public class RuntimeNativeRegistrarImpl implements RuntimeNativeRegistrar {

    @Override
    public void register(AppContext context, RuntimeNativeMetadata metadata) {
        metadata.registerReflection(OpenApiSetting.class, MemberCategory.values());
        metadata.registerReflection(OpenApiExtendSetting.class, MemberCategory.values());
        metadata.registerReflection(BaseEntity.class, MemberCategory.values());
        metadata.registerReflection(Author.class, MemberCategory.values());
        metadata.registerReflection(Book.class, MemberCategory.values());
        metadata.registerReflection(BookStore.class, MemberCategory.values());
        metadata.registerReflection(TreeNode.class, MemberCategory.values());
        metadata.registerReflection(WeatherInfo.class, MemberCategory.values());
        metadata.registerReflection(WarningInstanceInfo.class, MemberCategory.values());
        metadata.registerReflection(SysConfig.class, MemberCategory.values());
        metadata.registerReflection(SysRelation.class, MemberCategory.values());
        metadata.registerReflection(AuthorDraft.class, MemberCategory.values());
        metadata.registerReflection(BookDraft.class, MemberCategory.values());
        metadata.registerReflection(BookStoreDraft.class, MemberCategory.values());
        metadata.registerReflection(TreeNodeDraft.class, MemberCategory.values());
        metadata.registerReflection(WeatherInfoDraft.class, MemberCategory.values());
        metadata.registerReflection(WarningInstanceInfoDraft.class, MemberCategory.values());
        metadata.registerReflection(SysConfigDraft.class, MemberCategory.values());
        metadata.registerReflection(SysRelationDraft.class, MemberCategory.values());
    }

}
