package com.familyan.smarth.manager.vo;

import com.familyan.smarth.manager.domain.sms.SmsSendLogDO;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/29 0029.
 */
public class SmsSendLogVO extends SmsSendLogDO {

    private Date sendTimeStart;
    private Date sendTimeEnd;

    public Date getSendTimeStart() {
        return sendTimeStart;
    }

    public void setSendTimeStart(Date sendTimeStart) {
        this.sendTimeStart = sendTimeStart;
    }

    public Date getSendTimeEnd() {
        return sendTimeEnd;
    }

    public void setSendTimeEnd(Date sendTimeEnd) {
        this.sendTimeEnd = sendTimeEnd;
    }
}
