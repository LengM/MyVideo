package com.lovcreate.core.model;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack.Li on 2015/8/21.
 */
public class AjaxResponse {
    private ReturnState returnState = ReturnState.OK;

    private Boolean ok;
    private Boolean error;
    private Boolean warning;

    private String returnMsg;

    private Map<String, Object> returnData = new HashMap<String, Object>();

    public AjaxResponse() {
        this(ReturnState.OK, "");
    }

    public AjaxResponse(String returnMsg) {
        this(ReturnState.OK, returnMsg);
    }

    public AjaxResponse(ReturnState returnState, String returnMsg) {
        this.returnState = returnState;
        this.returnMsg = returnMsg;
    }

    public AjaxResponse(String attributeName, Object attributeValue) {
        this();
        addAttribute(attributeName, attributeValue);
    }

    public boolean isOk() {
        return returnState == ReturnState.OK;
    }

    public boolean isWarning() {
        return returnState == ReturnState.WARNING;
    }

    public boolean isError() {
        return returnState == ReturnState.ERROR;
    }

    public ReturnState getReturnState() {
        return returnState;
    }

    public void setReturnState(ReturnState returnState) {
        this.returnState = returnState;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Map<String, Object> getReturnData() {
        if (returnData == null) {
            returnData = new HashMap<String, Object>();
        }
        return returnData;
    }

    public void setReturnData(Map<String, Object> returnData) {
        this.returnData = returnData;
    }

    public AjaxResponse addAttribute(String attributeName, Object attributeValue) {
        getReturnData().put(attributeName, attributeValue);
        return this;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getWarning() {
        return warning;
    }

    public void setWarning(Boolean warning) {
        this.warning = warning;
    }

}
