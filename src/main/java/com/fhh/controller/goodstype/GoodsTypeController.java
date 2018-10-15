package com.fhh.controller.goodstype;

import com.fhh.base.BaseController;
import com.fhh.entity.GoodsTypeModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.service.GoodsTypeService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Controller
public class GoodsTypeController extends BaseController {
    @Autowired
    private GoodsTypeService goodsTypeService;

    /**
     * 添加商品类别
     *
     * @param request
     * @param response
     * @param goodsTypeName
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/15 13:46
     **/
    @PostMapping(value = "/bms/goodsType/addGoodsType")
    public String addGoodsType(HttpServletRequest request, HttpServletResponse response,
                               @NotBlank(message = "请输入商品类别名称！") @Length(max = 20, message = "请输入20字以内的商品名称！") String goodsTypeName) throws BMSException {
        User user = this.getUserSession(request);
        boolean flag = goodsTypeService.addGoodsType(goodsTypeName, user);
        return this.poClient(response, flag);
    }

    /**
     * 获取商品类别列表
     *
     * @param response
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/15 14:01
     **/
    @GetMapping(value = "/bms/goodsType/getGoodsTypeList")
    public String getGoodsTypeList(HttpServletResponse response) {
        List<GoodsTypeModel> goodsTypeList = goodsTypeService.getGoodsTypeList();
        return this.poClient(response, goodsTypeList);
    }

    /**
     * 更新商品类别
     *
     * @param goodsTypeId
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/15 14:05
     **/
    @PostMapping(value = "/bms/goodsType/updateGoodsTypeById")
    public String updateGoodsTypeById(HttpServletRequest request, HttpServletResponse response,
                                      @NotBlank(message = "商品类别id不能为空！") String goodsTypeId,
                                      @NotBlank(message = "商品类别名称不能为空！") String goodsTypeName) throws BMSException {
        User user = this.getUserSession(request);
        boolean flag = goodsTypeService.updateGoodsType(goodsTypeId, goodsTypeName, user);
        return this.poClient(response, flag);
    }

    /**
     * @param goodsTypeId 商品类别id
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/15 17:49
     **/
    @PostMapping(value = "/bms/goodsType/delGoodsType")
    public String delGoodsTypeById(HttpServletRequest request, HttpServletResponse response,
                                   @NotBlank(message = "商品id不能为空！") String goodsTypeId) throws BMSException {
        User user = this.getUserSession(request);
        boolean flag = goodsTypeService.delGoodsType(goodsTypeId, user);
        return this.poClient(response, flag);
    }
}