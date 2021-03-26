package org.themis.check.dao;

import org.apache.ibatis.annotations.*;
import org.themis.check.entity.CheckParameterConfig;
import org.themis.check.utils.check.RuleConfigModel;

import java.util.Date;
import java.util.List;

/**
 * 参数校验配置
 * @author YangZhou
 */
@Mapper
public interface CheckParameterConfigMapper {

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    @Insert(value = "insert into check_parameter_config (config_id, route_config_id, param_name," +
            "check_rule, pattern, create_time,update_time, create_user, update_user,message)" +
            "values (#{configId,jdbcType=BIGINT}, #{routeConfigId,jdbcType=BIGINT}, #{paramName,jdbcType=VARCHAR}," +
            "#{checkRule,jdbcType=VARCHAR}, #{pattern,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, " +
            "#{updateTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=VARCHAR}, #{updateUser,jdbcType=VARCHAR}," +
            "#{message,jdbcType=VARCHAR})")
    int insert(CheckParameterConfig record);


    /**
     * select by primary key
     *
     * @param configId primary key
     * @return object by primary key
     */
    @Select(value = "select config_id, route_config_id, param_name, check_rule, pattern, create_time, update_time," +
            "create_user, update_user,message from check_parameter_config where config_id = #{configId,jdbcType=BIGINT}")
    @Results(
            {
               @Result(id = true, property = "configId", column = "config_id", javaType = Long.class),
               @Result(property = "routeConfigId", column = "route_config_id", javaType = Long.class),
               @Result(property = "paramName", column = "param_name", javaType = String.class),
               @Result(property = "checkRule", column = "check_rule", javaType = String.class),
               @Result(property = "pattern", column = "pattern", javaType = String.class),
               @Result(property = "message", column = "message", javaType = String.class),
               @Result(property = "createTime", column = "create_time", javaType = Date.class),
               @Result(property = "updateTime", column = "update_time", javaType = Date.class),
               @Result(property = "createUser", column = "create_user", javaType = String.class),
               @Result(property = "updateUser", column = "update_user", javaType = String.class)
            }
    )
    CheckParameterConfig selectByPrimaryKey(Long configId);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    @Update(value = "update check_parameter_config set route_config_id = #{routeConfigId,jdbcType=BIGINT}," +
            "param_name = #{paramName,jdbcType=VARCHAR}, check_rule = #{checkRule,jdbcType=VARCHAR}," +
            "pattern = #{pattern,jdbcType=VARCHAR}, create_time = #{createTime,jdbcType=TIMESTAMP}," +
            "update_time = #{updateTime,jdbcType=TIMESTAMP},create_user = #{createUser,jdbcType=VARCHAR}," +
            "update_user = #{updateUser,jdbcType=VARCHAR}," +
            "message = #{message,jdbcType=VARCHAR} where config_id = #{configId,jdbcType=BIGINT}")
    int updateByPrimaryKey(CheckParameterConfig record);

    /**
     * 根据规则id查询规则
     * @param configId 拦截规则id
     * @return
     */
    @Select(value = "select * from check_parameter_config where route_config_id = #{configId} ")
    @Results({
            @Result(id = true, property = "configId", column = "config_id", javaType = Long.class),
            @Result(property = "routeConfigId", column = "route_config_id", javaType = Long.class),
            @Result(property = "paramName", column = "param_name", javaType = String.class),
            @Result(property = "checkRule", column = "check_rule", javaType = String.class),
            @Result(property = "pattern", column = "pattern", javaType = String.class),
            @Result(property = "message", column = "message", javaType = String.class),
    })
    List<RuleConfigModel> listRulesByConfigId(Long configId);

}