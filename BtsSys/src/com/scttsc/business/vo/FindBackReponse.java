package com.scttsc.business.vo;

/**
 * 数据找回返回业务对象
 * Created by _think on 2014/10/12.
 */
public class FindBackReponse {
    private String result;//操作结果
    private int sucessNum;//成功找回数量

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSucessNum() {
        return sucessNum;
    }

    public void setSucessNum(int sucessNum) {
        this.sucessNum = sucessNum;
    }
}
