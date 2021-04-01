package cn.myiml.theims.core.verify;

import cn.myiml.theims.core.model.CheckRulesConfigModel;
import com.google.common.cache.Cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 校验缓存方法接口
 * @author yangzhou
 */
public interface VerifyCache<E> {

    /**
     * 获取校验缓存
     * @return
     */
    Cache<String,List<E>> getVerifyCache();


    /**
     * 批量存放校验规则
     * @param rules 规则集合
     */
    void setRules(ConcurrentHashMap<String, List<E>> rules);


    /**
     * 根据key获取规则
     * @param key 缓存中的键
     * @return E
     */
    List<E> getVal(String key);

    /**
     * 根据路径添加规则
     * @param route
     */
    void addCache(String route);

    /**
     * 添加规则
     * @param route 路径
     * @param rules 规则集合
     * @return Cache
     */
    Cache<String, List<E>> addCache(String route, List<E> rules);
}
