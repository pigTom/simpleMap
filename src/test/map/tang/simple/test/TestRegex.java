package tang.simple.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/5/30.
 */
public class TestRegex {
    @org.junit.Test
    public void testRe() {
        String str = "file:/aaaa/file/aaa/aa.jar!\\aaa/a.x";
        Pattern pattern = Pattern.compile("\\w+(/|\\\\)\\w+\\.jar!(/|\\\\).+");
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.find());
        str = str.replaceAll("(/|\\\\)\\w+\\.jar!(/|\\\\)", "/");
        str = str.replaceAll("^file:/", "");
        System.out.println(str);
    }
}
