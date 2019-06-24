package cn.keking.project.cancel.base.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class CommonResponseUtil {

    private CommonResponseUtil(){}
    /**
     * 返回一个info为空对象的成功消息的json
     */
    public static JSONObject successJson() {
        return successJson(new JSONObject());
    }

    /**
     * 返回一个返回码为0的json
     */
    public static JSONObject successJson(Object content) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", "0");
        resultJson.put("msg", "请求成功！");
        resultJson.put("content", content);
        return resultJson;
    }



    /**
     * 返回错误信息JSON
     */
    public static JSONObject errorJson(String errorCode, String errorMsg) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", errorCode);
        resultJson.put("msg", errorMsg);
        return resultJson;
    }
    /**
     * 返回错误信息JSON
     */
    public static JSONObject errorJson( String errorMsg) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", "1");
        resultJson.put("msg", errorMsg);
        return resultJson;
    }
    /**
     * 查询分页结果后的封装工具方法
     *
     * @param pageSize 每页大小
     * @param list     查询分页对象list
     * @param total    查询出记录的总条数
     */
    public static JSONObject successPage(Integer pageSize, JSONArray list, Integer total) {
        //总页数
        int totalPage = getPageCounts(pageSize, total);
        JSONObject info = new JSONObject();
        info.put("list", list);
        info.put("total", total);
        info.put("totalPage", totalPage);
        JSONObject result = successJson(info);
        return result;
    }

    /**
     * 获取总页数
     *
     * @param pageRow   每页行数
     * @param itemCount 结果的总条数
     */
    private static int getPageCounts(int pageRow, int itemCount) {
        if (itemCount == 0) {
            return 1;
        }
        return itemCount % pageRow > 0 ?
                itemCount / pageRow + 1 :
                itemCount / pageRow;
    }
}
