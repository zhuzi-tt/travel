package cn.ynmz.travel.dao;

import cn.ynmz.travel.domain.Seller;

/**
 * 查询商家信息
 */
public interface SellerDao {
    public Seller findBySeller(int sid);
}
