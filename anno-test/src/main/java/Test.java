public class Test {

    public static void main(String[] args){
        TestPojo t = new TestPojo();
        t.setA("abc");

        new TestPojoBuilder().build();
    }
}
