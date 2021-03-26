package org.themis.check.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author YangZhou
 */
@Data
public class BaseEntity {

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String  updateUser;

}
