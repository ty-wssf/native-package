package com.bcht.rminf.modules.sys.controller;

import com.bcht.rminf.modules.Immutables;
import com.bcht.rminf.modules.Tables;
import com.bcht.rminf.modules.sys.dto.PageList;
import com.bcht.rminf.modules.sys.model.SysRelation;
import com.bcht.rminf.modules.sys.model.SysRelationTable;
import com.bcht.rminf.modules.terminal.model.Result;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.noear.snack.ONode;
import org.noear.solon.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapping(("sys/relation"))
@Controller
public class SysRelationController {

    @Inject
    JSqlClient sqlClient;

    @Get
    @Mapping()
    public Result<PageList<ONode>> sysRelationList(int page, int perPage, String objectId, String category) {
        SysRelationTable table = Tables.SYS_RELATION_TABLE;
        Page<SysRelation> pageList = sqlClient.createQuery(table)
                .whereIf(
                        objectId != null && !objectId.isEmpty(),
                        () -> table.objectId().eq(objectId)
                )
                .whereIf(
                        category != null && !category.isEmpty(),
                        () -> table.category().eq(category)
                )
                .select(table)
                .fetchPage(page - 1, perPage);
        return Result.data(new PageList<>(pageList.getRows().stream().map(s -> ONode.loadStr(s.toString())).collect(Collectors.toList()),
                pageList.getTotalRowCount()));
    }

    @Post
    @Mapping()
    public Result<String> saveOrUpdate(Map<String, String> data) {
        if (data.containsKey("excel")) {
            List<SysRelation> list = new ArrayList<>();
            ONode.loadStr(data.get("excel")).forEach(s -> {
                SysRelation sysRelation = Immutables.createSysRelation(draft -> {
                    draft.setObjectId(s.get("objectId").getString());
                    draft.setTargetId(s.get("targetId").getString());
                    draft.setCategory(s.get("category").getString());
                    draft.setCreatedTime(LocalDateTime.now());
                    draft.setModifiedTime(LocalDateTime.now());
                });
                list.add(sysRelation);
            });
            sqlClient.saveEntities(list);
        } else {
            SysRelation sysRelation = Immutables.createSysRelation(draft -> {
                draft.setObjectId(data.get("objectId"));
                draft.setTargetId(data.get("targetId"));
                draft.setCategory(data.get("category"));
                draft.setCreatedTime(LocalDateTime.now());
                draft.setModifiedTime(LocalDateTime.now());
            });
            sqlClient.save(sysRelation);
        }
        return Result.ok();
    }

    @Delete
    @Mapping("{id}")
    public Result<String> delete(long id) {
        sqlClient.deleteById(SysRelation.class, id);
        return Result.ok();
    }

}
