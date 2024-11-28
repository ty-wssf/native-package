package com.bcht.rminf.modules.sys.controller;

import com.bcht.rminf.modules.Immutables;
import com.bcht.rminf.modules.Tables;
import com.bcht.rminf.modules.sys.dto.Result;
import com.bcht.rminf.modules.sys.model.SysConfig;
import com.bcht.rminf.modules.sys.model.SysConfigTable;
import org.babyfish.jimmer.sql.JSqlClient;
import org.noear.solon.Solon;
import org.noear.solon.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapping(("sys/config"))
@Controller
public class SysConfigController {

    @Inject
    JSqlClient sqlClient;

    /**
     * 配置更新
     *
     * @param config   配置
     * @param category 配置分类
     * @return
     */
    @Post
    @Mapping
    public Result<String> saveConfig(Map<String, String> config, @Param("category") String category) {
        if (config != null) {
            List<SysConfig> configList = new ArrayList<>();
            for (Map.Entry<String, String> entry : config.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                SysConfig sysConfig = Immutables.createSysConfig(draft -> {
                    draft.setConfigKey(key);
                    draft.setConfigValue(value);
                    draft.setConfigCategory(category);
                    draft.setCreatedTime(LocalDateTime.now());
                    draft.setModifiedTime(LocalDateTime.now());
                });
                Solon.cfg().put(sysConfig.configKey().replace("_", "."), sysConfig.configValue());
                configList.add(sysConfig);
            }
            sqlClient.saveEntities(configList); // 保存或者更新
        }
        return Result.ok("保存成功");
    }

    /**
     * 配置更新
     *
     * @param category 配置分类
     * @return
     */
    @Get()
    @Mapping("/category/{category}")
    public Result<Map<String, String>> findByCategory(String category) {
        SysConfigTable table = Tables.SYS_CONFIG_TABLE;
        List<SysConfig> list = sqlClient.createQuery(table)
                .where(table.configCategory().eq(category))
                .select(table)
                .execute();
        Map<String, String> config = new HashMap<>();
        if (list != null) {
            for (SysConfig sysConfig : list) {
                config.put(sysConfig.configKey(), sysConfig.configValue());
            }
        }
        return Result.data(config);
    }


}
