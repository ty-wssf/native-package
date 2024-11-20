package com.bcht.rminf.modules.foglamp.model;

import lombok.Data;

/**
 * @author yll
 * @date 2022/12/15 18:11
 */
@Data
public class ResultVo<T> {

    /**
     * status
     * 状态码
     */
    private String code;

    /**
     * message
     * 消息
     */
    private String msg;

    /**
     * data
     */
    private T data;


    public static ResultVo OK(Object t){
        ResultVo result = new ResultVo();
        result.code = "0";
        result.data = t;
        result.msg = "SUCCESS";
        return result;
    }

    public static ResultVo OK(){
        ResultVo result = new ResultVo();
        result.code = "0";
        result.msg = "SUCCESS";
        return result;
    }

    public static ResultVo OK(String msg){
        ResultVo result = new ResultVo();
        result.code = "0";
        result.msg = msg;
        return result;
    }

    public static ResultVo OK(String msg,Object data){
        ResultVo result = new ResultVo();
        result.code = "0";
        result.msg = msg;
        result.data=data;
        return result;
    }

    public static ResultVo FAIL(String msg){
        ResultVo result = new ResultVo();
        result.code ="-1";
        result.msg = msg;
        return result;
    }
}
