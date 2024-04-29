package osmedile.intellij.stringmanip.escaping;

import osmedile.intellij.stringmanip.AbstractStringManipAction;

import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author 张英俊
 */
public class ConvertGBKSymbol extends AbstractStringManipAction<Object> {

    private static final Map<Character, Character> MAPPING = new HashMap<>();

    static {
        MAPPING.put('（', '(');
        MAPPING.put('）', ')');
        MAPPING.put('，', ',');
        MAPPING.put('。', '.');
        MAPPING.put('：', ':');
        MAPPING.put('“', '\"');
        MAPPING.put('”', '\"');
    }

    public String transformByLine(Map<String, Object> actionContext, String s) {
        return escape(s);
    }

    public String escape(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray())
            sb.append(MAPPING.getOrDefault(c, c));
        if (str.endsWith("。"))
            return sb.substring(0, str.length() - 1) + "。";
        else
            return sb.toString();
    }

}
