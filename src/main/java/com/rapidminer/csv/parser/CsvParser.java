package com.rapidminer.csv.parser;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class CsvParser {

    private static Logger logger = LoggerFactory.getLogger(CsvParser.class);
    private final String NUMBER_REGEX = "^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$";

    public List<Map<String, Object>> read(File file) throws IOException {
        List<Map<String, Object>> records = new LinkedList<>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
        MappingIterator<Map<String, Object>> iterator = null;
        try {
            //if the size of file is big, we should call the web service with several batches of records with a limited batch size
            // otherwise will cause out of memory error;
            iterator = mapper.readerFor(Map.class)
                    .with(schema)
                    .readValues(file);
        } catch (IOException e) {
            logger.error("could not parse file ", e);
            throw e;
        }

        while (iterator.hasNext()) {
            final Map<String, Object> record = iterator.next();
            parseNumbers(record);
            records.add(record);
        }
        return records;
    }

    private void parseNumbers(Map<String, Object> record) {
        record.entrySet().forEach(entry -> {
            if (entry.getValue() instanceof String && entry.getValue().toString().matches(NUMBER_REGEX)) {
                entry.setValue(Double.valueOf(entry.getValue().toString()));
            }
        });
    }

    public void write(String path, List<Map<String, Object>> list) throws IOException {
        try (Writer writer = new FileWriter(new File(path))) {
            CsvSchema schema = null;
            CsvSchema.Builder schemaBuilder = CsvSchema.builder();
            if (list != null && !list.isEmpty()) {
                for (String col : list.get(0).keySet()) {
                    schemaBuilder.addColumn(col);
                }
                schema = schemaBuilder.build().withLineSeparator(System.lineSeparator()).withHeader().withColumnSeparator(';');
            }
            CsvMapper mapper = new CsvMapper();
            mapper.writer(schema).writeValues(writer).writeAll(list);
            writer.flush();
        } catch (IOException e) {
            logger.error("could not write file" + e);
            throw e;
        }
    }
}
