package org.themis.check.dao;

import org.apache.ibatis.annotations.*;
import org.themis.check.entity.CheckRouteConfig;
import org.themis.check.utils.check.CheckRulesConfigModel;

import java.util.Date;
import java.util.List;

/**
 * @author yangzhou
 * 校验路由配置
 */
@Mapper
public interface CheckRouteConfigMapper {

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    @Insert(value = "insert into check_route_config (config_id, `domain`, route,`name`, `status`, create_time, " +
            "update_time, create_user, update_user,process_type, type_val)" +
            "values (#{configId,jdbcType=BIGINT}, #{domain,jdbcType=VARCHAR}, #{route,jdbcType=VARCHAR}, \n" +
            "#{name,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, \n" +
            "#{updateTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=VARCHAR}, #{updateUser,jdbcType=VARCHAR},\n" +
            "#{processType,jdbcType=VARCHAR}, #{typeVal,jdbcType=VARCHAR}\n" +
            ")")
    int insert(CheckRouteConfig record);


    /**
     * select by primary key
     *
     * @param configId primary key
     * @return object by primary key
     */
    @Select(value = "select config_id, `domain`, route, `name`, `status`, create_time, update_time, create_user, " +
            "update_user,process_type, type_val from check_route_config where config_id = #{configId,jdbcType=BIGINT}")
    @Results(
            {
                    @Result(id = true, property = "configId", column = "config_id", javaType = Long.class),
                    @Result(property = "domain", column = "domain", javaType = Long.class),
                    @Result(property = "route", column = "route", javaType = String.class),
                    @Result(property = "name", column = "name", javaType = String.class),
                    @Result(property = "status", column = "status", javaType = String.class),
                    @Result(property = "createTime", column = "create_time", javaType = Date.class),
                    @Result(property = "updateTime", column = "update_time", javaType = Date.class),
                    @Result(property = "createUser", column = "create_user", javaType = String.class),
                    @Result(property = "updateUser", column = "update_user", javaType = String.class),
                    @Result(property = "processType", column = "process_type", javaType = String.class),
                    @Result(property = "typeVal", column = "type_val", javaType = String.class)
            }
    )
    CheckRouteConfig selectByPrimaryKey(Long configId);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    @Update(value = "update check_route_config set `domain` = #{domain,jdbcType=VARCHAR}," +
            "route = #{route,jdbcType=VARCHAR}," +
            "`name` = #{name,jdbcType=VARCHAR}," +
            "`status` = #{status,jdbcType=INTEGER}," +
            "create_time = #{createTime,jdbcType=TIMESTAMP}," +
            "update_time = #{updateTime,jdbcType=TIMESTAMP}," +
            "create_user = #{createUser,jdbcType=VARCHAR}," +
            "update_user = #{updateUser,jdbcType=VARCHAR} where config_id = #{configId,jdbcType=BIGINT}")
    int updateByPrimaryKey(CheckRouteConfig record);

    /**
     * 查询所有校验规则
     *
     * @return
     */
    @Select(value = "select * from check_route_config")
    @Results({
            @Result(column="config_id",property="configId"),
            @Result(column="domain",property="domain"),
            @Result(column="route",property="route"),
            @Result(column="name",property="name"),
            @Result( column="status",property="status"),
            @Result( column="process_type",property="processType"),
            @Result( column="type_val",property="typeVal"),
            @Result(property = "rules", column = "config_id",many = @Many(select = "org.themis.check.dao.CheckParameterConfigMapper.listRulesByConfigId"))
    })
    List<CheckRulesConfigModel> findAllRouteAndRule();


    List<CheckRulesConfigModel> findAllByRoute(@Param("route") String route);

    /**
     * 初始化规则表
     */
    @Select(value = "create table if not exists `check_parameter_config`  (" +
            "            `config_id` bigint(20) not null primary key auto_increment ," +
            "            `route_config_id` bigint(20) not null," +
            "            `param_name` varchar(255) character set utf8 collate utf8_general_ci not null comment '参数名'," +
            "            `check_rule` varchar(255) character set utf8 collate utf8_general_ci default '' comment '校验规则'," +
            "            `pattern` varchar(255) character set utf8 collate utf8_general_ci not null comment '验证模式 default(非空校验) regular(正则表达式)'," +
            "            `message` varchar(255) character set utf8 collate utf8_general_ci default null comment '提示消息'," +
            "            `create_time` datetime(0) default null," +
            "            `update_time` datetime(0) default null," +
            "            `create_user` varchar(20) character set utf8 collate utf8_general_ci default null," +
            "            `update_user` varchar(20) character set utf8 collate utf8_general_ci default null) engine = innodb character set = utf8 collate = utf8_general_ci row_format = dynamic;")
    void updateTableParameter();

    /**
     * 初始化路由表
     */
    @Select(value="create table if not exists `check_route_config` (" +
            "            `config_id` bigint(20) not null primary key auto_increment," +
            "            `domain` varchar(255) default null," +
            "            `route` varchar(255) not null comment '校验路径'," +
            "            `name` varchar(255) default null comment '配置名'," +
            "            `status` int(1) default null comment '启用状态'," +
            "            `create_time` datetime default null on update current_timestamp," +
            "            `update_time` datetime default null," +
            "            `create_user` varchar(20) default null," +
            "            `update_user` varchar(20) default null," +
            "            `process_type` varchar(255) default null comment '处理类型'," +
            "            `type_val` varchar(255) default null comment '处理类型匹配值') engine=innodb default charset=utf8;")
    void updateTableRoute();
}