package chatbot.api.domain.zsxq.model.aggregates;

import chatbot.api.domain.zsxq.model.res.RespData;


//未回答问题的聚合信息

public class UnAnsweredQuestionsAggregates {
    private  boolean succeeded;//接口是否请求成功
    private RespData resp_data;

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public RespData getResp_data() {
        return resp_data;
    }

    public void setResp_data(RespData resp_data) {
        this.resp_data = resp_data;
    }
}
