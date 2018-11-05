package com.fhh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhh.base.BaseService;
import com.fhh.constant.BillConstant;
import com.fhh.dao.BillDao;
import com.fhh.dao.GoodsDao;
import com.fhh.entity.BillModel;
import com.fhh.entity.GoodsModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.bill.AddBillModel;
import com.fhh.model.bill.GetBillByUserModel;
import com.fhh.model.bill.PayForBillModel;
import com.fhh.model.bill.UpdateBillModel;
import com.fhh.service.BillService;
import com.fhh.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-11 19:37
 */
@Service
public class BillServiceImpl extends BaseService implements BillService {
    @Autowired
    private BillDao billDao;
    @Autowired
    private GoodsDao goodsDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBillByAdmin(AddBillModel model, User user) throws BMSException {
        //首先判断当前用户是否有权限添加账单
        if (!this.judgeHasPower(user)) {
            throw new BMSException("无权限用户操作！");
        }
        //生成uuid
        model.setUuId(DataUtil.getUUid());
        //账单创建人id（既当前操作用户）
        model.setCreateManId(user.getId());
        model.setCreateManName(user.getRealName());
        User borrowerMan = this.getUserInfo(model.getBorrowerManId());
        model.setBorrowerMan(borrowerMan.getRealName());
        model.setBorrowerNikeName(borrowerMan.getNickName());
        model.setYearMonth(new SimpleDateFormat("YYYY-MM").format(new Date()).toString());
        //如果借款类型为商品，那么只传商品id
        if (BillConstant.Btype.GOODS.equals(model.getBtype())) {
            if (this.isNil(model.getGoodsId())) {
                throw new BMSException("缺少必要参数！");
            }
            List<GoodsModel> goodsInfo = goodsDao.getGoods(model.getGoodsId(), "0");
            if (goodsInfo.isEmpty()) {
                throw new BMSException("错误的参数！");
            }
            model.setGoodsName(goodsInfo.get(0).getGoodsName());
            model.setLoanAmount(goodsInfo.get(0).getGoodsPrice());
        } else {
            //如果为现金支付，则需要对金额进行校验
            boolean matches = Pattern.matches("^([1-9]\\d\\d\\d|[1-9]\\d\\d|[1-9]\\d|\\d)$", model.getLoanAmount());
            if (!matches) {
                throw new BMSException("请输入1-9999的金额！");
            }
        }
        int addBill = billDao.addBill(model);
        if (addBill < 1) {
            throw new BMSException("新建账单失败，请联系管理员处理！");
        }
        return true;
    }

    @Override
    public Map<String, Object> getBillByUser(GetBillByUserModel model, User user) throws BMSException {
        Map<String, Object> data = new HashMap<>(16);
        List<BillModel> billModelList = billDao.getBillByUser(model);
//        Map<String, String> totalMoney = getTotalMoney(billModelList);
//        data.put("totalMoney", totalMoney.get("totalMoney"));
//        data.put("totalPaymoney", totalMoney.get("totalPaymoney"));
//        data.put("totalUnpayMoney", totalMoney.get("totalUnpayMoney"));
        data.put("billList", JSONArray.parseArray(JSON.toJSONString(billModelList)));
        return data;
    }

    /**
     * 获取总金额
     *
     * @param billModelList 账单列表
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/16 11:30
     **/
    private Map<String, String> getTotalMoney(List<BillModel> billModelList) throws BMSException {
        //总借款金额
        int totalMoney = 0;
        //已还款总金额
        int totalPaymoney = 0;
        //未还款总金额
        int totalUnpayMoney = 0;
        if (!billModelList.isEmpty()) {
            for (BillModel billModel : billModelList) {
                billModel.setCreate_time(billModel.getCreate_time().substring(0, billModel.getCreate_time().length() - 2));
                //已还款
                if (BillConstant.Bstatus.HASPAY.equals(billModel.getBstatus())) {
                    try {
                        totalPaymoney += Integer.parseInt(billModel.getLoanAmount());
                    } catch (Exception e) {
                        throw new BMSException("借款金额中存在错误的数据，请联系管理员处理!");
                    }
                } else {
                    try {
                        totalUnpayMoney += Integer.parseInt(billModel.getLoanAmount());
                    } catch (Exception e) {
                        throw new BMSException("借款金额中存在错误的数据，请联系管理员处理!");
                    }
                }
            }
        }
        totalMoney = totalPaymoney + totalUnpayMoney;
        Map<String, String> moneyMap = new HashMap<>(16);
        moneyMap.put("totalMoney", totalMoney + "");
        moneyMap.put("totalPaymoney", totalPaymoney + "");
        moneyMap.put("totalUnpayMoney", totalUnpayMoney + "");
        return moneyMap;
    }

    @Override
    public JSONObject getIndexPage(GetBillByUserModel model) throws BMSException {
        List<BillModel> billModelList = billDao.getBillByUser(model);
        Map<String, String> totalMoney = getTotalMoney(billModelList);
        JSONObject result = new JSONObject();
        result.put("totalMoney", totalMoney.get("totalMoney"));
        result.put("totalPaymoney", totalMoney.get("totalPaymoney"));
        result.put("totalUnpayMoney", totalMoney.get("totalUnpayMoney"));
        //查询所有用户，查询这些用户下是否有账单，有账单显示出来，没有账单的不显示在主页
        //用于记录用户信息
        List<Map<String, Object>> userBillInfoListMap = new ArrayList<>();
        List<User> userList = userDao.getUserList();
        for (User userModel : userList) {
            Map<String, Object> userBillInfoMap = new HashMap<>(16);
            GetBillByUserModel getBillModel = new GetBillByUserModel();
            getBillModel.setUserId(userModel.getId());
            getBillModel.setBstatus(model.getBstatus());
            getBillModel.setYearMonth(model.getYearMonth());
            List<BillModel> billList = billDao.getBillByUser(getBillModel);
            if (!billList.isEmpty()) {
                //获取每个人的账单总金额
                Map<String, String> userTotalMoney = getTotalMoney(billList);
                userBillInfoMap.put("totalUserMoney", userTotalMoney.get("totalMoney"));
                userBillInfoMap.put("totalUserPaymoney", userTotalMoney.get("totalPaymoney"));
                userBillInfoMap.put("totalUserUnpayMoney", userTotalMoney.get("totalUnpayMoney"));
                userBillInfoMap.put("userRealName", userModel.getRealName());
                userBillInfoMap.put("userNickName", userModel.getNickName());
                userBillInfoMap.put("userId", userModel.getId());
                userBillInfoMap.put("countNumber", billList.size() + "");
                //放入每个人的账单详情
                userBillInfoMap.put("detailList", billList);
                userBillInfoListMap.add(userBillInfoMap);
            }
        }
        result.put("userBillList", userBillInfoListMap);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delBill(User user, String billIds) throws BMSException {
        boolean flag = judgeHasPower(user);
        if (!flag) {
            throw new BMSException("无权限用户操作！");
        }
        String[] billArray = billIds.split(",");
        for (String billId : billArray) {
            //查询是否存在这个账单,并且已经结账的账单是不允许删除的
            BillModel billById = billDao.getBillById(billId);
            if (ObjectUtils.isEmpty(billById)) {
                throw new BMSException("错误的参数！");
            }
            if (BillConstant.Bstatus.HASPAY.equals(billById.getBstatus())) {
                throw new BMSException("已完成的账单不允许删除！");
            }
            int delBill = billDao.delBill(user.getId(), billId);
            if (delBill < 1) {
                throw new BMSException("删除账单失败，请联系管理员处理！");
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payForBill(User user, String billIds) throws BMSException {
        boolean flag = judgeHasPower(user);
        if (!flag) {
            throw new BMSException("无权限用户操作！");
        }
        //如果当前账单已经还款了则不允许重复操作
        String[] billIdsArray = billIds.split(",");
        for (String billId : billIdsArray) {
            BillModel billModel = billDao.getBillById(billId);
            if (ObjectUtils.isEmpty(billModel)) {
                throw new BMSException("存在错误参数");
            }
            if (BillConstant.Bstatus.HASPAY.equals(billModel.getBstatus())) {
                throw new BMSException("该账单已经结账了，请勿重复操作！");
            }
            PayForBillModel payForBillModel = new PayForBillModel();
            payForBillModel.setBillId(billId);
            payForBillModel.setPayDate(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
            //目前写死为现金还款  后期有需求再做优化
            //TODO
            payForBillModel.setPaySource("0");
            payForBillModel.setUserId(user.getId());
            int payForBill = billDao.payForBill(payForBillModel);
            if (payForBill < 1) {
                throw new BMSException("还款失败，请联系管理员处理！");
            }
        }
        return true;
    }

    @Override
    public JSONObject getBillById(String billId) throws BMSException {
        BillModel billInfoModel = billDao.getBillById(billId);
        if (ObjectUtils.isEmpty(billInfoModel)) {
            throw new BMSException("错误的参数！");
        }
        JSONObject billInfo = JSONObject.parseObject(JSON.toJSONString(billInfoModel));
        if (this.isNil(billInfoModel.getPayOpeManId())) {
            billInfo.put("payOpeMan", "无");
        } else {
            billInfo.put("payOpeMan", this.getUserInfo(billInfoModel.getPayOpeManId()).getRealName());
        }
        billInfo.put("create_time", billInfoModel.getCreate_time().substring(0, billInfoModel.getCreate_time().length() - 2));
        //查询商品所属类别id和类别名称
        GoodsModel goodsModel = goodsDao.getGoodsById(billInfoModel.getGoodsId());
        if ("0".equals(billInfoModel.getBtype())) {
            billInfo.put("goodsTypeId", goodsModel.getGoodsTypeId());
            billInfo.put("goodsTypeName", goodsModel.getGoodsTypeName());
        }
        return billInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBill(@Valid UpdateBillModel updateBillModel) throws BMSException {
        boolean flag = judgeHasPower(updateBillModel.getUserModel());
        if (!flag) {
            throw new BMSException("无权限用户操作！");
        }
        int updateBill = billDao.updateBill(updateBillModel);
        if (updateBill < 1) {
            throw new BMSException("更新账单失败，请联系管理员处理！");
        }
        return true;
    }
}