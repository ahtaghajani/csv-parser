package com.rapidminer.service.impl;

import com.rapidminer.csv.parser.CsvParser;
import com.rapidminer.service.MedianApiClient;
import com.rapidminer.service.MedianFileGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
public class MedianFileGeneratorImpl implements MedianFileGenerator {

    private final CsvParser csvParser;
    private final MedianApiClientImpl medianApiClient;

    public MedianFileGeneratorImpl(CsvParser csvParser, MedianApiClientImpl medianApiClient) {
        this.csvParser = csvParser;
        this.medianApiClient = medianApiClient;
    }

    @Override
    public void generateMedianFiles(Path inputDirectory, Path outputDirectory) throws IOException {
        for (String file : inputDirectory.toFile().list()) {
            List<Map<String, Object>> records = csvParser.read(inputDirectory.resolve(file).toFile());

            final List<Map<String, Object>> resultColumntToRecord = medianApiClient.getMedian(records);

            csvParser.write(String.valueOf(outputDirectory.resolve(file)), resultColumntToRecord);
        }
    }
}
