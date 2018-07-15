public class TestPojoBuilder {

    private TestPojo object = new TestPojo();

    public TestPojo build() {
        return object;
    }

    public TestPojoBuilder setA(java.lang.String value) {
        object.setA(value);
        return this;
    }

}
