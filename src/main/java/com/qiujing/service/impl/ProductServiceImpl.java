package com.qiujing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiujing.entity.Product;
import com.qiujing.mapper.ProductMapper;
import com.qiujing.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}