package auth.papertrail.app.dto;

import java.util.Collections;
import java.util.Map;

import auth.papertrail.app.constants.MapKeys;

public class Details {

    public static final Map<String, String> NONE = Collections.emptyMap();

    public static Map<String, String> email(String email) {
        return Map.of(MapKeys.EMAIL, email);
    }

}
