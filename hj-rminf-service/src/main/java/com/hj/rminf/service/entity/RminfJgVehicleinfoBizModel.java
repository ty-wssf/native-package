
package com.hj.rminf.service.entity;

import io.nop.api.core.annotations.biz.BizModel;
import io.nop.biz.crud.CrudBizModel;

import com.hj.rminf.dao.entity.RminfJgVehicleinfo;

@BizModel("RminfJgVehicleinfo")
public class RminfJgVehicleinfoBizModel extends CrudBizModel<RminfJgVehicleinfo>{
    public RminfJgVehicleinfoBizModel(){
        setEntityName(RminfJgVehicleinfo.class.getName());
    }
}
