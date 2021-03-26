create table if not exists `check_parameter_config`  (
                                           `config_id` bigint(20) not null primary key auto_increment,
                                           `route_config_id` bigint(20) not null,
                                           `param_name` varchar(255) character set utf8 collate utf8_general_ci not null comment '参数名',
                                           `check_rule` varchar(255) character set utf8 collate utf8_general_ci default '' comment '校验规则',
                                           `pattern` varchar(255) character set utf8 collate utf8_general_ci not null comment '验证模式 default(非空校验) regular(正则表达式)',
                                           `message` varchar(255) character set utf8 collate utf8_general_ci default null comment '提示消息',
                                           `create_time` datetime(0) default null,
                                           `update_time` datetime(0) default null,
                                           `create_user` varchar(20) character set utf8 collate utf8_general_ci default null,
                                           `update_user` varchar(20) character set utf8 collate utf8_general_ci default null,
                                           primary key (`config_id`) using btree
) engine = innodb character set = utf8 collate = utf8_general_ci row_format = dynamic;

create table if not exists `check_route_config` (
                                      `config_id` bigint(20) not null primary key auto_increment,
                                      `domain` varchar(255) default null,
                                      `route` varchar(255) not null comment '校验路径',
                                      `name` varchar(255) default null comment '配置名',
                                      `status` int(1) default null comment '启用状态',
                                      `create_time` datetime default null on update current_timestamp,
                                      `update_time` datetime default null,
                                      `create_user` varchar(20) default null,
                                      `update_user` varchar(20) default null,
                                      `process_type` varchar(255) default null comment '处理类型',
                                      `type_val` varchar(255) default null comment '处理类型匹配值',
                                      primary key (`config_id`)
) engine=innodb default charset=utf8;