package com.ihrm.common.exception;

import com.ihrm.common.entity.ResultCode;
import lombok.Getter;

/**
 * com.ihrm.common.exception
 *
 * @author zhaopj
 * 2020/8/19
 */

@Getter
public class CommonException extends Exception {
    private ResultCode resultCode;


    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
