package com.xiaohan.cn.base.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import top.legendscloud.common.base.ReqPage;
import top.legendscloud.common.enums.CommonEnumCode;
import top.legendscloud.common.exception.BizException;
import top.legendscloud.db.page.PageUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基础service实现
 *
 * @author teddy
 * @since 2023/1/3
 */
public abstract class TBaseServiceImpl<T> extends ServiceImpl<BaseMapper<T>, T> implements TBaseService<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Autowired
    private PageUtils pageUtils;

    /**
     * 转换当前泛型类
     * @param data 参数数据
     * @return T
     */
    private T getBean(T data) {
        return JSON.parseObject(JSON.toJSONString(data), clazz);
    }

    /**
     * buildQueryWrapper
     * @param t 实体
     * @return queryWrapper
     */
    protected abstract QueryWrapper<T> buildQueryWrapper(T t);

    @Override
    public List<T> exportList(ReqPage<T> reqPage) {
        return this.page(reqPage).getRecords();
    }

    @Override
    public IPage<T> page(ReqPage<T> req) {
        return page(pageUtils.page(req.getPage(), req.getSize(), req.isSort(), req.getSortName()),
                buildQueryWrapper(getBean(req.getData())));
    }

    @Override
    public List<T> listEntity(T t) {
        return list(buildQueryWrapper(t));
    }

    @Override
    public T loadById(Long id) {
        return getById(id);
    }

    @Override
    public Boolean add(T t) {
        if (!save(t)) {
            throw new BizException(CommonEnumCode.FAIL.getCode(), "新增出现问题!");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean modifyById(T t) {
        if (!updateById(t)) {
            throw new BizException(CommonEnumCode.FAIL.getCode(), String.format("修改异常！ID：%d", 1));
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteById(Long id) {
        if (!removeById(id)) {
            throw new BizException(CommonEnumCode.FAIL.getCode(), String.format("删除异常！ID：%d", id));
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteByIds(List<Long> ids) {
        if (!removeByIds(ids)) {
            throw new BizException(CommonEnumCode.FAIL.getCode(), String.format("批量删除异常！IDS：%s", ids.toArray()));
        }
        return Boolean.TRUE;
    }

}
