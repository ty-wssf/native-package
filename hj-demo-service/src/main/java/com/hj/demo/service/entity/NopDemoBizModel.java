
package com.hj.demo.service.entity;

import io.nop.api.core.annotations.biz.BizModel;
import io.nop.biz.crud.CrudBizModel;

import com.hj.demo.dao.entity.NopDemo;

@BizModel("NopDemo")
public class NopDemoBizModel extends CrudBizModel<NopDemo>{
    public NopDemoBizModel(){
        setEntityName(NopDemo.class.getName());
    }
}
