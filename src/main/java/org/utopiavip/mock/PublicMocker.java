package org.utopiavip.mock;

import com.hscf.common.secure.SimpleRandomUtil;
import org.utopiavip.util.RandomUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PublicMocker {

    // 金额
    private static final int MAX_NUMBER = 100000000;
    private static final int MAX_DECIMAL = 99;

    // 日期
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 单据
    private static String[] DOC_PREFIX = {"FK", "BF", "LA", "S"};
    private static int DOC_PREFIX_SIZE = 0;


    private static final String[] userNames = {"孙悟空","柯南","路飞","基督山伯爵","开心麻花","布丁","冰淇淋","王者",
            "村上","码农","白骨精","佳佳","蒜头","胖胖"};
    private static int userNameSize = 0;

    static {
        DOC_PREFIX_SIZE = DOC_PREFIX.length - 1;
        userNameSize = userNames.length;
    }

    // Mock 金额
    public static BigDecimal mockAmount() {
        return new BigDecimal(RandomUtil.getRandom(MAX_NUMBER) + "." + RandomUtil.getRandom(MAX_DECIMAL));
    }

    // Mock 时间
    public static String mockTime() {
        return df.format(new Date());
    }

    // Mock Id
    public static String mockUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    // Mock 单据
    public static String mockDocNumber() {
        return DOC_PREFIX[RandomUtil.getRandom(DOC_PREFIX_SIZE)] + "00000" + RandomUtil.getRandom(100) + RandomUtil.getRandom(10000);
    }

    // Mock 用户名
    public static String mockUserName() {
        return userNames[RandomUtil.getRandom(userNameSize)];
    }

    // Mock 利率
    public static BigDecimal mockRate() {
        return new BigDecimal("0." + RandomUtil.getRandom(10000));
    }

    // Mock code, status, type等的描述
    public static String MockCodeDesc() {
        return SimpleRandomUtil.generator(6);
    }

    //Mock boolean
    public static Boolean mockBoolean() {
        return RandomUtil.getRandom(2) == 1;
    }

    // Mock flag
    public static String mockFlag() {
        return RandomUtil.getRandom(2) == 1 ? "Y" : "N";
    }

    public static int mockInt() {
        return RandomUtil.getRandom(10000);
    }

    public static String mockVarchar() {
        return SimpleRandomUtil.generator(6);
    }
}
