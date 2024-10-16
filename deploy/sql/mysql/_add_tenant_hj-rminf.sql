
    alter table rminf_jg_vehicleinfo add column NOP_TENANT_ID VARCHAR(32) DEFAULT '0' NOT NULL;

alter table rminf_jg_vehicleinfo drop primary key;
alter table rminf_jg_vehicleinfo add primary key (NOP_TENANT_ID, ID);


