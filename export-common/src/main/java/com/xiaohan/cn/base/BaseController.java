//package com.xiaohan.cn.base;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
//import top.legendscloud.common.base.ComReq;
//import top.legendscloud.common.base.ComResp;
//import top.legendscloud.common.base.ReqPage;
//
//import java.util.List;
//
///**
// * 基础控制
// *
// * @author by teddy
// * @date 2022/12/28 18:25
// */
//@RestController
//public class BaseController<T> {
//
//    @Autowired
//    private BaseService<T> baseService;
//
//    public List<T> list() {
//        return baseService.list();
//    }
//
//    // 分页
//    public ComResp<IPage<T>> page(ComReq<ReqPage<T>> comReq) {
//        return new ComResp.Builder<IPage<T>>().fromReq(comReq)
//                .data(baseService.page(comReq.getData()))
//                .success().build();
//    }
//
//    // 列表
//
//    // 新增
//
//    // 修改
//
//    // 删除
//
//    // 批量删除
//}
