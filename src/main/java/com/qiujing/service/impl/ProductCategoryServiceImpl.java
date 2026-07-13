package com.qiujing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiujing.entity.ProductCategory;
import com.qiujing.mapper.ProductCategoryMapper;
import com.qiujing.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

}