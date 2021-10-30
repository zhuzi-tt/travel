package cn.ynmz.travel.service.impl;

import cn.ynmz.travel.dao.CategoryDao;
import cn.ynmz.travel.dao.impl.CategroyDaoImpl;
import cn.ynmz.travel.domain.Category;
import cn.ynmz.travel.service.CategoryService;
import cn.ynmz.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
public class CategroyServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategroyDaoImpl();

    @Override
    public List<Category> findAll() {
        /**
         * redis缓存
         */
        //从redis中获取数据
        //获取jedis客户端
        List<Category> ca = null;
        Jedis jedis = JedisUtil.getJedis();
        //使用sortedest排序查询
        Set<String> categroys = jedis.zrange("categroy", 0, -1);
        if (categroys == null || categroys.size() == 0) {
            ca = categoryDao.findAll();
            //将集合数据存入redis
            for (int i = 0; i < ca.size(); i++) {
                int cid = ca.get(i).getCid();
                jedis.zadd("category",cid, ca.get(i).getCname());
            }
        } else {
            //如果不为空，将set数据存入list
            ca = new ArrayList<Category>();
            for (String name : categroys) {
                Category category = new Category();
                category.setCname(name);
                ca.add(category);
            }
        }

        return ca;
    }

}
