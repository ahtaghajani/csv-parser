package com.rapidminer.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface MedianApiClient {
    List<Map<String, Object>> getMedian(List<Map<String, Object>> records) throws IOException;

}
