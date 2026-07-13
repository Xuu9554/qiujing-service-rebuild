package com.qiujing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("product")
public class Product implements Serializable {

    private final static long serialVersionUID = 5702040675186999612L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品类别ID
     */
    private Long categoryId;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 规格
     */
    private String specification;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 预览图路径
     */
    private String previewImageUrl;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 排序值，越小越靠前
     */
    private Integer sortOrder;

    /**
     * 状态：0-下架，1-上架
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