package com.liu.studentmanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统操作日志表
 * </p>
 *
 * @author Generate
 * @since 2023-10-xx
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) // 开启链式调用 setModule().setType()...
@TableName("sys_operation_log")
@Schema(description = "系统操作日志实体")
public class SysOperationLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     * 数据库类型: bigint (auto increment)
     * 注意：数据库是 bigint，Java 对应 Long，防止精度丢失
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模块名称
     * 数据库类型: varchar(50)
     */
    @Schema(description = "模块名称")
    private String module;

    /**
     * 操作类型
     * 数据库类型: varchar(20)
     */
    @Schema(description = "操作类型")
    private String type;

    /**
     * 操作人ID
     * 数据库类型: int
     */
    @Schema(description = "操作人ID")
    private Integer operatorId;

    /**
     * 操作人姓名
     * 数据库类型: varchar(50)
     */
    @Schema(description = "操作人姓名")
    private String operatorName;

    /**
     * 操作时间
     * 数据库类型: datetime
     * 建议使用 LocalDateTime 而不是 Date
     */
    @Schema(description = "操作时间")
    private LocalDateTime operationTime;

    /**
     * 方法名称
     * 数据库类型: varchar(100)
     */
    @Schema(description = "方法名称")
    private String methodName;

    /**
     * 请求参数
     * 数据库类型: text
     */
    @Schema(description = "请求参数")
    private String params;

    /**
     * 返回结果
     * 数据库类型: text
     */
    @Schema(description = "返回结果")
    private String result;

    /**
     * 错误信息
     * 数据库类型: text
     */
    @Schema(description = "错误信息")
    private String errorMsg;

    /**
     * 消耗时间(ms)
     * 数据库类型: bigint
     */
    @Schema(description = "消耗时间(毫秒)")
    private Long costTime;
}