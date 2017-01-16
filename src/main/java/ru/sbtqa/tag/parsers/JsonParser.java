package ru.sbtqa.tag.parsers;

import com.jayway.jsonpath.JsonPath;
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

    private static final Logger log = LoggerFactory.getLogger(JsonParser.class);

    @Override
    public String call(ParserItem item) {
        Object result = "";
        try {
            result = read(item.getSource(), item.getPath());
            if (result instanceof JSONArray) {
                result = ((JSONArray) result).get(0);
            }
        } catch (ParserException e) {
            log.error("Error to get value by path {} in source {}", item.getPath(), item.getSource(), e);
        }

        return (String) result;
    }

    @Override
    public <T extends Object> T read(String source, String jpath) throws ParserException {
        return JsonPath.read(source, jpath);
    }
}
