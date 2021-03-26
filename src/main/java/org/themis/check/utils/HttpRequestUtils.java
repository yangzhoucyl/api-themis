package org.themis.check.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * http请求工具类
 * @author YangZhou
 */
@Slf4j
public class HttpRequestUtils {

    public static Map<String, Object> getRequestBodyMap(HttpServletRequest request){
        BufferedReader bufferReader = null;
        StringBuilder sb = null;
        try {
            bufferReader = new BufferedReader(request.getReader());
            sb = new StringBuilder();
            String line = null;
            while ((line = bufferReader.readLine()) != null) {
                sb.append(line);
            }
            return JSON.parseObject(sb.toString(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static JSON getRequestBodyJson(HttpServletRequest request){
        BufferedReader bufferReader = null;
        StringBuilder sb = null;
        try {
            bufferReader = new BufferedReader(request.getReader());
            sb = new StringBuilder();
            String line = null;
            while ((line = bufferReader.readLine()) != null) {
                sb.append(line);
            }
            return (JSON) JSON.parse(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
