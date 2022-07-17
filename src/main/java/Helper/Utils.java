package Helper;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

public class Utils {
    public static Map<String, Object> parseJson(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        JSONObject obj = new JSONObject(getBody(request));

        Iterator<String> it = obj.keys();
        while(it.hasNext()) {
            String key = it.next(); // get key
            Object o = (Object) obj.get(key); // get value
            map.put(key, o);
        }
        return map;
    }
    private static String getBody(HttpServletRequest request) {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            // throw ex;
            return "";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {

                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
