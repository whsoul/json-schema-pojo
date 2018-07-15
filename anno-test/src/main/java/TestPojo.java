import com.whsoul.pojogen.anno.BuilderProperty;
import com.whsoul.pojogen.anno.POJOGEN;

public class TestPojo {

    @POJOGEN(schemeUrl = "test.json")
    public String test = "abcd";


    @BuilderProperty
    public void setA(String x){
        System.out.println(1234);
    }

}
