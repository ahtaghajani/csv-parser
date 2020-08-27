package com.rapidminer.service;

import java.io.IOException;
import java.nio.file.Path;

public interface MedianFileGenerator {
    void generateMedianFiles(Path inputDirectory, Path outputDirectory) throws IOException;
}
