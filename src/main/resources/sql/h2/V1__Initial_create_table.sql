create table if not exists sys_config
(
    id identity(100, 1) not null,
    config_key    varchar(50) not null,
    config_value  varchar(50),
    config_category  varchar(50),
    created_time  timestamp   not null,
    modified_time timestamp   not null
);
alter table sys_config
    add constraint business_key_sys_config
        unique (config_key)
;