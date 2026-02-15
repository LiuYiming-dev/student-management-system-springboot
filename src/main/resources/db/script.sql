create table clazz
(
    id           int auto_increment
        primary key,
    class_name   varchar(50)   not null comment '班级名称',
    teacher_name varchar(50)   null comment '班主任',
    create_time  datetime      null,
    update_time  datetime      null,
    phone_number varchar(20)   null,
    deleted      int default 0 null
);

create table student
(
    id                int auto_increment comment '主键ID'
        primary key,
    student_no        varchar(20)   null comment '学号',
    name              varchar(50)   null comment '姓名',
    age               int           null comment '年龄',
    create_time       datetime      null,
    update_time       datetime      null,
    clazz_id          int           null comment '所属班级ID',
    deleted           int default 0 null comment '0:未删除, 1:已删除',
    gender            int default 1 null comment '1:男, 0:女',
    update_by_user_id int           null comment '更新管理员',
    create_by_user_id int default 5 null comment '创建管理员',
    phone_number      varchar(20)   null,
    avatar            varchar(255)  null comment '头像地址',
    constraint uk_student_no
        unique (student_no),
    constraint fk_student_clazz
        foreign key (clazz_id) references clazz (id)
);

create table sys_operation_log
(
    id             bigint auto_increment
        primary key,
    module         varchar(50)  null comment '操作模块',
    type           varchar(20)  null comment '操作类型(INSERT/UPDATE/DELETE)',
    operator_id    int          null comment '操作人ID',
    operator_name  varchar(50)  null comment '操作人姓名',
    operation_time datetime     null comment '操作时间',
    method_name    varchar(100) null comment '调用的Java方法',
    params         text         null comment '请求参数',
    result         text         null comment '返回结果',
    error_msg      text         null comment '错误信息',
    cost_time      bigint       null comment '耗时(ms)'
);

create table sys_user
(
    id          int auto_increment
        primary key,
    username    varchar(50)                   not null comment '账号',
    password    varchar(100)                  not null comment '加密后的密码',
    nickname    varchar(50)                   null comment '昵称',
    create_time datetime                      null,
    update_time datetime                      null,
    role        varchar(20) default 'STUDENT' not null,
    avatar      varchar(255)                  null,
    constraint uk_sys_user_username
        unique (username),
    constraint uk_username
        unique (username)
);


