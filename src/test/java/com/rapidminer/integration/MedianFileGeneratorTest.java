package com.rapidminer.integration;

import com.rapidminer.service.impl.MedianFileGeneratorImpl;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.rapidminer.integration.MedianFileGeneratorTest.outputDirectoryToBeCreated;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest(args = {"src/test/resources/empty", outputDirectoryToBeCreated})
class MedianFileGeneratorTest {

    public static final String outputDirectoryToBeCreated = "src/test/resources/output";

    private final Path sampleInputDirectory = Paths.get("src/test/resources/sample/input");
    private final Path generatedMediansDirectory = Paths.get("src/test/resources/generatedMedianFiles");

    @Autowired
    private MedianFileGeneratorImpl medianFileGenerator;

    @Test
    void filesGeneratedTest() throws IOException {
        FileUtils.cleanDirectory(generatedMediansDirectory.toFile());

        medianFileGenerator.generateMedianFiles(sampleInputDirectory, generatedMediansDirectory);

        String[] sampleInputNames = new String[]{"iris1.csv", "iris2.csv"};

        String[] sampleOutputNames = new String[2];
        for (int i = 0; i < generatedMediansDirectory.toFile().list().length; i++) {
            sampleOutputNames[i] = (generatedMediansDirectory.toFile().list())[i];
        }

        assertArrayEquals(sampleInputNames, sampleOutputNames);
    }

}
