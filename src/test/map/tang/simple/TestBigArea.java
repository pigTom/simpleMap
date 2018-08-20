package tang.simple;

import com.tang.simple.dataModel.BigArea;
import com.tang.simple.mapModel.RecordList;
import com.tang.simple.mapModel.RecordShape;
import org.junit.Test;

import java.io.IOException;

public class TestBigArea {
    @Test
    public void getRiver() {
        try {
            BigArea bigArea = BigArea.newInstance();
            RecordList river = bigArea.getRiverLevel1Line(1.0, 1.0);
            for (RecordShape shape : river) {
                System.out.println(shape.getShapeType() + "---" + shape.getRecordNum());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
