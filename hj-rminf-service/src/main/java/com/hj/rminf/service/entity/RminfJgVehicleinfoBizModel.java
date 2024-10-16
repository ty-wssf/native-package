
package com.hj.rminf.service.entity;

import io.nop.api.core.annotations.biz.BizModel;
import io.nop.api.core.ioc.BeanContainer;
import io.nop.biz.crud.CrudBizModel;

import com.hj.rminf.dao.entity.RminfJgVehicleinfo;
import io.nop.core.context.IServiceContext;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@BizModel("RminfJgVehicleinfo")
public class RminfJgVehicleinfoBizModel extends CrudBizModel<RminfJgVehicleinfo> {


    public RminfJgVehicleinfoBizModel() {
        setEntityName(RminfJgVehicleinfo.class.getName());
    }

    @Override
    protected void afterEntityChange(RminfJgVehicleinfo entity, IServiceContext context) {
        BeanContainer.getBeanByType(Vertx.class).eventBus().send("test", new JsonObject(entity.orm_dirtyNewValues()));
    }

}
