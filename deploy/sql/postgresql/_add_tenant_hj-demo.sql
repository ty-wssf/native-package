
    alter table nop_demo add column NOP_TENANT_ID VARCHAR(32) DEFAULT '0' NOT NULL;

alter table nop_demo drop constraint PK_nop_demo;
alter table nop_demo add constraint PK_nop_demo primary key (NOP_TENANT_ID, ID);


