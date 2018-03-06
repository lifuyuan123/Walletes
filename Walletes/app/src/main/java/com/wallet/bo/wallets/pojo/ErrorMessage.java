package com.wallet.bo.wallets.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * author:ggband
 * date:2017/7/18 13:40
 * email:ggband520@163.com
 * desc:网络请求返回失败处理
 */

public class ErrorMessage {

    private Map<Integer, String> mapErrorMessage = new HashMap<>();
    private static final ErrorMessage errorMessage = new ErrorMessage();
    private final String message = "未知错误";

    private ErrorMessage() {
    }

    {
        mapErrorMessage.put(10000, "用户名或密码错误");
    }

    public static String getErrorMeesage(Integer id) {

        if (errorMessage.mapErrorMessage.containsKey(id))

            return errorMessage.mapErrorMessage.get(id);
        else
            return errorMessage.message+" 错误代码："+id;
    }
}
