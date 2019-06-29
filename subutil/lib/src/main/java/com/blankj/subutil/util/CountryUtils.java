package com.blankj.subutil.util;

import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;

import com.blankj.utilcode.util.Utils;

import java.util.HashMap;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/06/11
 *     desc  : utils about country code
 * </pre>
 */
public class CountryUtils {

    private static HashMap<String, String> countryCodeMap;

    /**
     * Return the country code by sim card.
     *
     * @param defaultValue The default value.
     * @return the country code
     */
    public static String getCountryCodeBySim(String defaultValue) {
        String code = getCountryCodeFromMap().get(getCountryBySim());
        if (code == null) {
            return defaultValue;
        }
        return code;
    }

    /**
     * Return the country code by system language.
     *
     * @param defaultValue The default value.
     * @return the country code
     */
    public static String getCountryCodeByLanguage(String defaultValue) {
        String code = getCountryCodeFromMap().get(getCountryByLanguage());
        if (code == null) {
            return defaultValue;
        }
        return code;
    }

    /**
     * Return the country by sim card.
     *
     * @return the country
     */
    public static String getCountryBySim() {
        TelephonyManager manager = (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        if (manager != null) {
            return manager.getSimCountryIso().toUpperCase();
        }
        return "";
    }

    /**
     * Return the country by system language.
     *
     * @return the country
     */
    public static String getCountryByLanguage() {
        return Resources.getSystem().getConfiguration().locale.getCountry();
    }

    private static HashMap<String, String> getCountryCodeFromMap() {
        if (countryCodeMap == null) {
            countryCodeMap = new HashMap<>(256);
            countryCodeMap.put("AL", "+355");
            countryCodeMap.put("DZ", "+213");
            countryCodeMap.put("AF", "+93");
            countryCodeMap.put("AR", "+54");
            countryCodeMap.put("AE", "+971");
            countryCodeMap.put("AW", "+297");
            countryCodeMap.put("OM", "+968");
            countryCodeMap.put("AZ", "+994");
            countryCodeMap.put("AC", "+247");
            countryCodeMap.put("EG", "+20");
            countryCodeMap.put("ET", "+251");
            countryCodeMap.put("IE", "+353");
            countryCodeMap.put("EE", "+372");
            countryCodeMap.put("AD", "+376");
            countryCodeMap.put("AO", "+244");
            countryCodeMap.put("AI", "+1");
            countryCodeMap.put("AG", "+1");
            countryCodeMap.put("AT", "+43");
            countryCodeMap.put("AX", "+358");
            countryCodeMap.put("AU", "+61");
            countryCodeMap.put("BB", "+1");
            countryCodeMap.put("PG", "+675");
            countryCodeMap.put("BS", "+1");
            countryCodeMap.put("PK", "+92");
            countryCodeMap.put("PY", "+595");
            countryCodeMap.put("PS", "+970");
            countryCodeMap.put("BH", "+973");
            countryCodeMap.put("PA", "+507");
            countryCodeMap.put("BR", "+55");
            countryCodeMap.put("BY", "+375");
            countryCodeMap.put("BM", "+1");
            countryCodeMap.put("BG", "+359");
            countryCodeMap.put("MP", "+1");
            countryCodeMap.put("BJ", "+229");
            countryCodeMap.put("BE", "+32");
            countryCodeMap.put("IS", "+354");
            countryCodeMap.put("PR", "+1");
            countryCodeMap.put("PL", "+48");
            countryCodeMap.put("BA", "+387");
            countryCodeMap.put("BO", "+591");
            countryCodeMap.put("BZ", "+501");
            countryCodeMap.put("BW", "+267");
            countryCodeMap.put("BT", "+975");
            countryCodeMap.put("BF", "+226");
            countryCodeMap.put("BI", "+257");
            countryCodeMap.put("KP", "+850");
            countryCodeMap.put("GQ", "+240");
            countryCodeMap.put("DK", "+45");
            countryCodeMap.put("DE", "+49");
            countryCodeMap.put("TL", "+670");
            countryCodeMap.put("TG", "+228");
            countryCodeMap.put("DO", "+1");
            countryCodeMap.put("DM", "+1");
            countryCodeMap.put("RU", "+7");
            countryCodeMap.put("EC", "+593");
            countryCodeMap.put("ER", "+291");
            countryCodeMap.put("FR", "+33");
            countryCodeMap.put("FO", "+298");
            countryCodeMap.put("PF", "+689");
            countryCodeMap.put("GF", "+594");
            countryCodeMap.put("VA", "+39");
            countryCodeMap.put("PH", "+63");
            countryCodeMap.put("FJ", "+679");
            countryCodeMap.put("FI", "+358");
            countryCodeMap.put("CV", "+238");
            countryCodeMap.put("FK", "+500");
            countryCodeMap.put("GM", "+220");
            countryCodeMap.put("CG", "+242");
            countryCodeMap.put("CD", "+243");
            countryCodeMap.put("CO", "+57");
            countryCodeMap.put("CR", "+506");
            countryCodeMap.put("GG", "+44");
            countryCodeMap.put("GD", "+1");
            countryCodeMap.put("GL", "+299");
            countryCodeMap.put("GE", "+995");
            countryCodeMap.put("CU", "+53");
            countryCodeMap.put("GP", "+590");
            countryCodeMap.put("GU", "+1");
            countryCodeMap.put("GY", "+592");
            countryCodeMap.put("KZ", "+7");
            countryCodeMap.put("HT", "+509");
            countryCodeMap.put("KR", "+82");
            countryCodeMap.put("NL", "+31");
            countryCodeMap.put("BQ", "+599");
            countryCodeMap.put("SX", "+1");
            countryCodeMap.put("ME", "+382");
            countryCodeMap.put("HN", "+504");
            countryCodeMap.put("KI", "+686");
            countryCodeMap.put("DJ", "+253");
            countryCodeMap.put("KG", "+996");
            countryCodeMap.put("GN", "+224");
            countryCodeMap.put("GW", "+245");
            countryCodeMap.put("CA", "+1");
            countryCodeMap.put("GH", "+233");
            countryCodeMap.put("GA", "+241");
            countryCodeMap.put("KH", "+855");
            countryCodeMap.put("CZ", "+420");
            countryCodeMap.put("ZW", "+263");
            countryCodeMap.put("CM", "+237");
            countryCodeMap.put("QA", "+974");
            countryCodeMap.put("KY", "+1");
            countryCodeMap.put("CC", "+61");
            countryCodeMap.put("KM", "+269");
            countryCodeMap.put("XK", "+383");
            countryCodeMap.put("CI", "+225");
            countryCodeMap.put("KW", "+965");
            countryCodeMap.put("HR", "+385");
            countryCodeMap.put("KE", "+254");
            countryCodeMap.put("CK", "+682");
            countryCodeMap.put("CW", "+599");
            countryCodeMap.put("LV", "+371");
            countryCodeMap.put("LS", "+266");
            countryCodeMap.put("LA", "+856");
            countryCodeMap.put("LB", "+961");
            countryCodeMap.put("LT", "+370");
            countryCodeMap.put("LR", "+231");
            countryCodeMap.put("LY", "+218");
            countryCodeMap.put("LI", "+423");
            countryCodeMap.put("RE", "+262");
            countryCodeMap.put("LU", "+352");
            countryCodeMap.put("RW", "+250");
            countryCodeMap.put("RO", "+40");
            countryCodeMap.put("MG", "+261");
            countryCodeMap.put("IM", "+44");
            countryCodeMap.put("MV", "+960");
            countryCodeMap.put("MT", "+356");
            countryCodeMap.put("MW", "+265");
            countryCodeMap.put("MY", "+60");
            countryCodeMap.put("ML", "+223");
            countryCodeMap.put("MK", "+389");
            countryCodeMap.put("MH", "+692");
            countryCodeMap.put("MQ", "+596");
            countryCodeMap.put("YT", "+262");
            countryCodeMap.put("MU", "+230");
            countryCodeMap.put("MR", "+222");
            countryCodeMap.put("US", "+1");
            countryCodeMap.put("AS", "+1");
            countryCodeMap.put("VI", "+1");
            countryCodeMap.put("MN", "+976");
            countryCodeMap.put("MS", "+1");
            countryCodeMap.put("BD", "+880");
            countryCodeMap.put("PE", "+51");
            countryCodeMap.put("FM", "+691");
            countryCodeMap.put("MM", "+95");
            countryCodeMap.put("MD", "+373");
            countryCodeMap.put("MA", "+212");
            countryCodeMap.put("MC", "+377");
            countryCodeMap.put("MZ", "+258");
            countryCodeMap.put("MX", "+52");
            countryCodeMap.put("NA", "+264");
            countryCodeMap.put("ZA", "+27");
            countryCodeMap.put("SS", "+211");
            countryCodeMap.put("NR", "+674");
            countryCodeMap.put("NI", "+505");
            countryCodeMap.put("NP", "+977");
            countryCodeMap.put("NE", "+227");
            countryCodeMap.put("NG", "+234");
            countryCodeMap.put("NU", "+683");
            countryCodeMap.put("NO", "+47");
            countryCodeMap.put("NF", "+672");
            countryCodeMap.put("PW", "+680");
            countryCodeMap.put("PT", "+351");
            countryCodeMap.put("JP", "+81");
            countryCodeMap.put("SE", "+46");
            countryCodeMap.put("CH", "+41");
            countryCodeMap.put("SV", "+503");
            countryCodeMap.put("WS", "+685");
            countryCodeMap.put("RS", "+381");
            countryCodeMap.put("SL", "+232");
            countryCodeMap.put("SN", "+221");
            countryCodeMap.put("CY", "+357");
            countryCodeMap.put("SC", "+248");
            countryCodeMap.put("SA", "+966");
            countryCodeMap.put("BL", "+590");
            countryCodeMap.put("CX", "+61");
            countryCodeMap.put("ST", "+239");
            countryCodeMap.put("SH", "+290");
            countryCodeMap.put("PN", "+870");
            countryCodeMap.put("KN", "+1");
            countryCodeMap.put("LC", "+1");
            countryCodeMap.put("MF", "+590");
            countryCodeMap.put("SM", "+378");
            countryCodeMap.put("PM", "+508");
            countryCodeMap.put("VC", "+1");
            countryCodeMap.put("LK", "+94");
            countryCodeMap.put("SK", "+421");
            countryCodeMap.put("SI", "+386");
            countryCodeMap.put("SJ", "+47");
            countryCodeMap.put("SZ", "+268");
            countryCodeMap.put("SD", "+249");
            countryCodeMap.put("SR", "+597");
            countryCodeMap.put("SB", "+677");
            countryCodeMap.put("SO", "+252");
            countryCodeMap.put("TJ", "+992");
            countryCodeMap.put("TH", "+66");
            countryCodeMap.put("TZ", "+255");
            countryCodeMap.put("TO", "+676");
            countryCodeMap.put("TC", "+1");
            countryCodeMap.put("TA", "+290");
            countryCodeMap.put("TT", "+1");
            countryCodeMap.put("TN", "+216");
            countryCodeMap.put("TV", "+688");
            countryCodeMap.put("TR", "+90");
            countryCodeMap.put("TM", "+993");
            countryCodeMap.put("TK", "+690");
            countryCodeMap.put("WF", "+681");
            countryCodeMap.put("VU", "+678");
            countryCodeMap.put("GT", "+502");
            countryCodeMap.put("VE", "+58");
            countryCodeMap.put("BN", "+673");
            countryCodeMap.put("UG", "+256");
            countryCodeMap.put("UA", "+380");
            countryCodeMap.put("UY", "+598");
            countryCodeMap.put("UZ", "+998");
            countryCodeMap.put("GR", "+30");
            countryCodeMap.put("ES", "+34");
            countryCodeMap.put("EH", "+212");
            countryCodeMap.put("SG", "+65");
            countryCodeMap.put("NC", "+687");
            countryCodeMap.put("NZ", "+64");
            countryCodeMap.put("HU", "+36");
            countryCodeMap.put("SY", "+963");
            countryCodeMap.put("JM", "+1");
            countryCodeMap.put("AM", "+374");
            countryCodeMap.put("YE", "+967");
            countryCodeMap.put("IQ", "+964");
            countryCodeMap.put("UM", "+1");
            countryCodeMap.put("IR", "+98");
            countryCodeMap.put("IL", "+972");
            countryCodeMap.put("IT", "+39");
            countryCodeMap.put("IN", "+91");
            countryCodeMap.put("ID", "+62");
            countryCodeMap.put("GB", "+44");
            countryCodeMap.put("VG", "+1");
            countryCodeMap.put("IO", "+246");
            countryCodeMap.put("JO", "+962");
            countryCodeMap.put("VN", "+84");
            countryCodeMap.put("ZM", "+260");
            countryCodeMap.put("JE", "+44");
            countryCodeMap.put("TD", "+235");
            countryCodeMap.put("GI", "+350");
            countryCodeMap.put("CL", "+56");
            countryCodeMap.put("CF", "+236");
            countryCodeMap.put("CN", "+86");
            countryCodeMap.put("MO", "+853");
            countryCodeMap.put("TW", "+886");
            countryCodeMap.put("HK", "+852");
        }
        return countryCodeMap;
    }
}
