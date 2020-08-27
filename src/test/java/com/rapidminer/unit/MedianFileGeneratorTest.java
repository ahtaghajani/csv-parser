package com.rapidminer.unit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class MedianFileGeneratorTest {
    @Test
    void fileContentTest() throws IOException {
        List<Map<String, Object>> expectedValue = new ArrayList<>();
        Map<String, Object> fieldToValue;

        fieldToValue = new HashMap<>();
        fieldToValue.put("a1", 6.3);
        fieldToValue.put("a2", 3.0);
        fieldToValue.put("a3", 5.9);
        fieldToValue.put("a4", 2.1);
        fieldToValue.put("id", "id_102");
        fieldToValue.put("label", "Iris-virginica");
        expectedValue.add(fieldToValue);

        fieldToValue = new HashMap<>();
        fieldToValue.put("a1", 4.9);
        fieldToValue.put("a2", 3.2);
        fieldToValue.put("a3", 1.4);
        fieldToValue.put("a4", 0.2);
        fieldToValue.put("id", "id_3");
        fieldToValue.put("label", "Iris-setosa");
        expectedValue.add(fieldToValue);

//        when(medianApiClient.getMedian(any())).thenReturn(expectedValue);
//        String expectedJson = objectMapper.writeValueAsString(expectedValue);



    }
}
