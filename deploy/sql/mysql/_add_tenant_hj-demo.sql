
    alter table nop_demo add column NOP_TENANT_ID VARCHAR(32) DEFAULT '0' NOT NULL;

alter table nop_demo drop primary key;
alter table nop_demo add primary key (NOP_TENANT_ID, ID);


