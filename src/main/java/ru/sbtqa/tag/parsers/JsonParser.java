package ru.sbtqa.tag.parsers;

import com.jayway.jsonpath.JsonPath;
import java.util.ArrayList;
import java.util.List;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.parsers.core.exceptions.ParserException;
import ru.sbtqa.tag.parsers.core.Parser;
import ru.sbtqa.tag.parsers.core.ParserCallback;
import ru.sbtqa.tag.parsers.core.ParserItem;

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
            if (result instanceof JSONArray) {
                return ((JSONArray) result).toJSONString();
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
