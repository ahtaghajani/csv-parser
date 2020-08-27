package com.rapidminer;

import com.rapidminer.service.impl.MedianFileGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class CsvParserApplication implements CommandLineRunner {
    private final MedianFileGeneratorImpl medianFileGenerator;
    private static Logger logger = LoggerFactory.getLogger(CsvParserApplication.class);

    public CsvParserApplication(MedianFileGeneratorImpl medianFileGenerator) {
        this.medianFileGenerator = medianFileGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(CsvParserApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length != 2) {
            usage();
        }

        Path inputDirectory = Paths.get(args[0]);
        Path outputDirectory = Paths.get(args[1]);

        validateDirectories(inputDirectory, outputDirectory);
        medianFileGenerator.generateMedianFiles(inputDirectory, outputDirectory);
    }

    private static void validateDirectories(Path inputDirectory, Path outputDirectory) {
        if (!Files.exists(inputDirectory) || !Files.isDirectory(inputDirectory)) {
            String message = String.format("'%s' is not a valid directory", inputDirectory);
            logger.error(message);
            System.exit(1);
        }

        if (!Files.exists(outputDirectory)) {
            outputDirectory.toFile().mkdirs();
        } else if (!Files.isDirectory(outputDirectory)) {
            String message = String.format("'%s' is not a directory", outputDirectory);
            logger.error(message);
            System.exit(1);
        }
    }

    public static void usage() {
        System.err.println("usage: java -jar csv-parser.jar inputDirectory outputDirectory");
        System.exit(-1);
    }
}
