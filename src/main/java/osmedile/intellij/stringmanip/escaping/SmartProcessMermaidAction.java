package osmedile.intellij.stringmanip.escaping;

import osmedile.intellij.stringmanip.AbstractStringManipAction;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zhnkc9
 * 对 () 进行处理
 */
public class SmartProcessMermaidAction extends AbstractStringManipAction<Object> {

    private static final Map<Character, String> MAPPING = new HashMap<>();
    private static final Pattern REG;

    static {
        MAPPING.put('(', "&#40");
        MAPPING.put(')', "&#41");
        MAPPING.put('{', "&#123");
        MAPPING.put('}', "&#125");
        MAPPING.put('"', "&#34");
        StringBuilder sb = new StringBuilder();
        for (String value : MAPPING.values()) {
            sb.append(value).append("|");
        }
        sb.append("[");
        for (var value : MAPPING.keySet()) {
            sb.append(value);
        }
        sb.append("]");
        REG = Pattern.compile(sb.toString());
    }

    int escape = -1;
    int content_id = 0x00;

    @Override
    public String transformByLine(Map<String, Object> actionContext, String s) {
        smartToggle(System.identityHashCode(actionContext), s);
        if (escape == -1)
            return s;
        else if (escape == 0)
            return unEscape(s);
        else
            return escape(s);
    }

    public void smartToggle(int id, String str) {
        if (this.content_id != id) {
            this.escape = -1;
            var m = REG.matcher(str);
            if (m.find()) {
                this.content_id = id;
                for (var value : MAPPING.keySet()) {
                    if (str.contains(Character.toString(value))) {
                        this.escape = 1;
                        return;
                    }
                }
                this.escape = 0;
            }
        }
    }

    public String escape(String str) {
        StringBuilder sb = new StringBuilder();
        for (var c : str.toCharArray())
            sb.append(MAPPING.getOrDefault(c, String.valueOf(c)));
        return sb.toString();
    }

    public String unEscape(String str) {
        for (Map.Entry<Character, String> e : MAPPING.entrySet())
            str = str.replace(e.getValue(), Character.toString(e.getKey()));
        return str;
    }

}
