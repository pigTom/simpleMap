package com.tang.simple.Utils;

import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2018/5/30.
 */
public class PathHelper {
    public static String getPath(String relatePath) {
        URL url = PathHelper.class.getClassLoader().getResource(relatePath);
        String path = relatePath;
        if (url != null) {
            path = url.getFile();
        }
        if (path.contains(".jar!")) {
            path = path.replaceAll("(/|\\\\)\\w+\\.jar!(/|\\\\)", "/");
            path = path.replaceAll("^file:(/|\\\\)", "");
        }
        try {

            path = URLDecoder.decode(path, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }
}
