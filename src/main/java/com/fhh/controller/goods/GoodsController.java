package com.fhh.controller.goods;

import com.alibaba.fastjson.JSON;
import com.fhh.base.BaseController;
import com.fhh.entity.GoodsModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.goods.AddGoodsModel;
import com.fhh.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 功能描述：（商品控制层）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-13 22:21
 */
@Controller
@Validated
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;

    /**
     * @param request
     * @param response
     * @param model
     * @param result
     * @return
     * @description 添加商品
     * @author biubiubiu小浩
     * @date 2018/10/15 10:51
     **/
    @PostMapping(value = "/bms/goods/addGoods")
    public String addGoods(HttpServletRequest request,
                           HttpServletResponse response, @Valid AddGoodsModel model, BindingResult result) throws BMSException {
        if (result.hasErrors()) {
            return this.poClient(response, result.getFieldError().getDefaultMessage());
        }
        User user = this.getUserSession(request);
        boolean flag = goodsService.addGoods(model, user);
        return this.poClient(response, flag);
    }

    /**
     * @param
     * @return
     * @description 获取商品列表
     * @author biubiubiu小浩
     * @date 2018/10/15 10:52
     **/
    @GetMapping(value = "/bms/goods/getGoodsList")
    public String getGoodsList(HttpServletRequest request,
                               HttpServletResponse response) {
        String goodsId = request.getParameter("goodsId");
        List<GoodsModel> goodsList = goodsService.getGoodsList(goodsId);
        return this.poClient(response, goodsList);
    }

    /**
     * @param request
     * @param response
     * @param model
     * @param result
     * @return
     * @description 更新商品信息
     * @author biubiubiu小浩
     * @date 2018/10/15 11:00
     **/
    @PostMapping(value = "/bms/goods/updateGoods")
    public String updateGoods(HttpServletRequest request,
                              HttpServletResponse response, @Valid AddGoodsModel model, BindingResult result) throws BMSException {
        if (result.hasErrors()) {
            return this.poClient(response, result.getFieldError().getDefaultMessage());
        }
        User user = this.getUserSession(request);
        boolean flag = goodsService.updateGoods(model, user);
        return this.poClient(response, flag);
    }

    /**
     * 删除商品
     *
     * @param request
     * @param response
     * @param goodsIds 商品id 多个id用逗号隔开
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/15 11:28
     **/
    @PostMapping(value = "/bms/goods/delGoods")
    public String delGoods(HttpServletRequest request,
                           HttpServletResponse response, @NotBlank(message = "需要删除的id不能为空！") String goodsIds) throws BMSException {
        User user = this.getUserSession(request);
        boolean flag = goodsService.delGoods(goodsIds, user);
        return this.poClient(response, flag);
    }


    /**
     * 根据商品类别id获取所有商品
     *
     * @param goodsTypeId 商品类别id
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/30 12:38
     **/
    @GetMapping(value = "/bms/goods/getGoodsListByGoodsTypeId")
    public String getGoodsListByGoodsTypeId(HttpServletResponse response, @NotBlank(message = "商品类别id不能为空！") String goodsTypeId) {
        List<GoodsModel> goodsListByGoodsTypeId = goodsService.getGoodsListByGoodsTypeId(goodsTypeId);
        return this.poClient(response, goodsListByGoodsTypeId);
    }

    /**
     * 根据商品id获取商品信息
     *
     * @param goodsId 商品id
     * @return
     * @author biubiubiu小浩
     * @date 2018/11/2 15:18
     **/
    @GetMapping(value = "/bms/goods/getGoodsInfoById")
    public String getGoodsInfoById(HttpServletResponse response, @NotBlank(message = "商品id不能为空！") String goodsId) {
        return this.poClient(response, JSON.parseObject(JSON.toJSONString(goodsService.getGoodsInfoById(goodsId))));
    }
}