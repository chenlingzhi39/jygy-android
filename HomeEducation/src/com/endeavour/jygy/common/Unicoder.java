package com.endeavour.jygy.common;

import android.text.TextUtils;

/**
 * Created by wu on 16/7/21.
 */
public class Unicoder {

    public static String emojiToUnicode(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        return toUnicode(str);
    }

    public static String toUnicode(String zhStr){
        StringBuffer unicode = new StringBuffer();
        for(int i=0; i<zhStr.length();i++){
            char c = zhStr.charAt(i);
            if ((c >= 0x4e00)&&(c <= 0x9fbb)){
                unicode.append("\\u" + Integer.toHexString(c));
            }else{
                unicode.append(c);
            }
        }
        return unicode.toString();
    }

    public static String unicode2Emoji(String myString) {
        if (TextUtils.isEmpty(myString)) {
            return myString;
        }
        String result = tozhCN(myString);
        return TextUtils.isEmpty(result)? myString : result;
    }

    public static String tozhCN(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
}
