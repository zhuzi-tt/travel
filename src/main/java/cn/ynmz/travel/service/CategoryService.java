package cn.ynmz.travel.service;

import cn.ynmz.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查询主页面分类数据
     * @return
     */
    public  List<Category> findAll();
}
