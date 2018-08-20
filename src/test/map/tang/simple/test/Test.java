package tang.simple.test;

public class Test {
    @org.junit.Test
    public void test() {
        int i = 0xb4;
        i = (i & 0xff) << 1 + 0xba;
        byte[] chi = "春节".getBytes();
        String s = new String();
        for (byte b : chi) {
            System.out.printf("%x", b);
        }
    }
}
