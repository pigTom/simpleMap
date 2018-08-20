package tang.simple;

import com.tang.simple.dataModel.DataHelper;
import com.tang.simple.dataModel.DataShpReader;
import com.tang.simple.mapModel.RecordList;
import com.tang.simple.mapModel.RecordShape;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class TestShp {

    @Test
    public void testShp(){
        DataShpReader reader = new DataShpReader("ChinaMap\\地级行政界线\\diquJie_polyline.shp", 1.0, 1.0);
        reader.readHeader();
        Map<String, Object> header = reader.getHeader();
        System.out.println(header);
    }

    @Test
    public void readCapital() throws IOException{
        DataHelper helper = new DataHelper("ChinaMap\\一级河流\\hyd1_4l.shp", 1.0, 1.0);
        RecordList list = helper.getRecords();
        for (RecordShape shape : list) {
            System.out.println(shape);
        }
    }

    @Test
    public void readStandardProvince() throws IOException{
        DataHelper helper = new DataHelper("mapData/CHN_adm1", 1.0, 1.0);
        RecordList list = helper.getRecords();
        Map<String, List<String>> name = helper.getDbfData("NAME_1");
        list.setNames(name.get("NAME_1"));
        for (RecordShape shape : list) {
            System.out.println(shape.getName() + "---" +shape.getRecordNum());
        }
    }
    @Test
    public void DataShpReader() throws IOException{
        DataShpReader reader = new DataShpReader("ChinaMap\\国界与省界\\bou2_4p.shp", 1.0, 1.0);
        reader.readHeader();
        Map<String, Object> header = reader.getHeader();
        System.out.println(header);

    }

    @Test
    public void readStandardNationAndProvince() throws IOException{
        DataHelper helper = new DataHelper("ChinaMap/国界与省界/bou2_4p.shx", 1.0, 1.0);
        RecordList list = helper.getRecords();
        Map<String, List<String>> name = helper.getDbfData("NAME_1");
        list.setNames(name.get("NAME_1"));
        for (RecordShape shape : list) {
            System.out.println(shape.getName() + "---" +shape.getRecordNum());
        }
    }
}
