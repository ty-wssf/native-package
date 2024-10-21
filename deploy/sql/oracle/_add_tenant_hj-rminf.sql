
    alter table rminf_jg_vehicleinfo add NOP_TENANT_ID VARCHAR2(32) DEFAULT '0' NOT NULL;

alter table rminf_jg_vehicleinfo drop constraint PK_rminf_jg_vehicleinfo;
alter table rminf_jg_vehicleinfo add constraint PK_rminf_jg_vehicleinfo primary key (NOP_TENANT_ID, ID);


