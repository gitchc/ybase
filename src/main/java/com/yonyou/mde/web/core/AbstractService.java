package com.yonyou.mde.web.core;


import com.yonyou.mde.web.dao.SqlMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;
    @Autowired
    protected SqlMapper sqlMapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public void insert(T model) {
        mapper.insertSelective(model);
    }

    public void insert(List<T> models) {
        mapper.insertList(models);
    }

    public void deleteById(String id) {
        mapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    public T findById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return mapper.selectAll();
    }

    public String getSingle(String sql) {
        List<LinkedHashMap<String, Object>> selectResult = sqlMapper.select(sql);
        if (selectResult.isEmpty())
            return "";
        else {
            return selectResult.get(0).values().iterator().next().toString();
        }
    }

    public LinkedHashMap<String, Object> getOne(String sql) {
        return sqlMapper.select(sql).get(0);
    }

    public List<LinkedHashMap<String, Object>> query(String sql) {
        return sqlMapper.select(sql);
    }

    public boolean execute(String sql) {
        try {
            sqlMapper.select(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean executeBatch(List<String> sql) {
        return this.execute(StringUtils.join(sql, ';'));
    }
}
