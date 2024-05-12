
create database source1;
use source1;
CREATE TABLE `sys_user`
(
    `user_id`   bigint      NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
    `user_name` varchar(32) NOT NULL COMMENT '账号',
    `password`  varchar(64) DEFAULT '' COMMENT '密码',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb3 COMMENT ='用户表';
INSERT INTO source1.sys_user (user_id, user_name, password) VALUES (1, 'admin', '12345678');
INSERT INTO source1.sys_user (user_id, user_name, password) VALUES (2, 'root', '123456');
INSERT INTO source1.sys_user (user_id, user_name, password) VALUES (3, 'test', '12345');


create database source2;
use source2;
CREATE TABLE `biz_order`
(
    `order_id`    bigint      NOT NULL AUTO_INCREMENT COMMENT '订单主键id',
    `goods_name`  varchar(32) NOT NULL COMMENT '商品名称',
    `destination` varchar(64) NOT NULL COMMENT '目的地',
    `order_time`  timestamp   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    PRIMARY KEY (`order_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb3 COMMENT ='订单表';
INSERT INTO source2.biz_order (order_id, goods_name, destination, order_time) VALUES (1, '商品名称1', '地点1', '2024-05-12 18:38:58');
INSERT INTO source2.biz_order (order_id, goods_name, destination, order_time) VALUES (2, '商品名称2', '地点2', '2024-05-12 18:38:59');
INSERT INTO source2.biz_order (order_id, goods_name, destination, order_time) VALUES (3, '商品名称3', '地点3', '2024-05-12 18:39:00');

