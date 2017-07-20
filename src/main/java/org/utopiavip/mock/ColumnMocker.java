package org.utopiavip.mock;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock特殊表字段
 */
public class ColumnMocker {

    private static final Map<String, Map> columns = new HashMap<>();
    private static final Map<String, String> fund_request_map = new HashMap<>();

    static {
        fund_request_map.put("APPROVAL", "审批中");
        fund_request_map.put("CANCEL", "已撤销");
        fund_request_map.put("LEND", "已放款");
        fund_request_map.put("LIQUIDATION", "已清算");
        fund_request_map.put("REFUSED", "已拒绝");
        fund_request_map.put("REIMBURSEMENT", "还款中");
        fund_request_map.put("SUBMIT", "已申请");
    }

    static {
        columns.put("fund_request_status", fund_request_map);
    }

}
