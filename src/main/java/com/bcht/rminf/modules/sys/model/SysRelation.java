package com.bcht.rminf.modules.sys.model;

import com.bcht.rminf.modules.demo.model.common.BaseEntity;
import org.babyfish.jimmer.sql.*;

@Entity
public interface SysRelation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    @Key
    String objectId();

    String targetId();

    @Key
    String category();

}
