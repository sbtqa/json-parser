package ru.sbtqa.tag.parsers;

import com.jayway.jsonpath.JsonPath;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.parsers.core.exceptions.ParserException;
import ru.sbtqa.tag.parsers.core.Parser;
import ru.sbtqa.tag.parsers.core.ParserCallback;
import ru.sbtqa.tag.parsers.core.ParserItem;
import ru.sbtqa.tag.qautils.properties.Props;

/**
 * Json parser
 */
public class JsonParser implements Parser, ParserCallback {

    private static final Logger LOG = LoggerFactory.getLogger(JsonParser.class);

    @Override
    public Object call(ParserItem item) {
        Object result = "";
        try {
            result = read(item.getSource(), item.getPath());
            if (Props.get("jsonparser.toJSONString") != null
                    && Boolean.valueOf(Props.get("jsonparser.toJSONString"))) {
                if (result instanceof JSONArray) {
                    return ((JSONArray) result).toJSONString();
                }
                if (result instanceof JSONObject) {
                    return ((JSONObject) result).toJSONString();
                }
                if (result instanceof LinkedHashMap) {
                    return new JSONObject((LinkedHashMap) result).toJSONString();
                }
            }
            if (result instanceof JSONArray) {
                List<String> list = new ArrayList<>();
                for (Object object : (JSONArray) result) {
                    list.add(JSONObject.toJSONString((Map<String, ? extends Object>) object));
                }
                return list;
            }
        } catch (ParserException e) {
            LOG.error("Error to get value by path {} in source {}", item.getPath(), item.getSource(), e);
        }

        return result;
    }

    @Override
    public <T extends Object> T read(String source, String jpath) throws ParserException {
        return JsonPath.read(source, jpath);
    }
}
