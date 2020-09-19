package com.modec.vessel_engine.utils;

import io.micrometer.core.instrument.util.IOUtils;

import java.io.InputStream;

public class Json {

    static public String loadJson(String jsonPath) {
        String filePath = String.format("/json/requests/%s.json", jsonPath);
        return loadFileContent(filePath);
    }

    static protected String loadFileContent(String filePath) {
        InputStream stream = Json.class.getResourceAsStream(filePath);
        return IOUtils.toString(stream);
    }

}
