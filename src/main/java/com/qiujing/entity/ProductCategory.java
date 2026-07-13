package com.qiujing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("product_category")
public class ProductCategory implements Serializable {

    private final static long serialVersionUID = 1061522244930821097L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 预览图路径
     */
    private String previewImageUrl;

    /**
     * 类别描述
     */
    private String description;

    /**
     * 排序值，越小越靠前
     */
    private Integer sortOrder;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}