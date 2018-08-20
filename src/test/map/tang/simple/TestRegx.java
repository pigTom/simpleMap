package tang.simple;

import java.util.regex.Pattern;

public class TestRegx {
    public static void main(String[] args) {
        String regx = "\\bthe";
        String pattern = "a the world";
        Pattern pattern1 = Pattern.compile(regx);
        System.out.println(pattern1.matcher(pattern).matches());
    }
}
