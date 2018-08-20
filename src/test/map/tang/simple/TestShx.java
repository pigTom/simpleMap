package tang.simple;

import com.tang.simple.dataModel.DataShxReader;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestShx {
    @Test
    public void testShx() throws IOException{
        DataShxReader reader = new DataShxReader
                ("G:\\simplemap\\src\\main\\resourses\\mapData\\TWN_adm0.shx");
        Map<String, Object> header = reader.getHeader();
        System.out.println(header);
        List list = reader.readOffsetList();
        System.out.println(list);
    }
}
