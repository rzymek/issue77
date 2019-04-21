package io.github.rzymek.fastexcel;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Issue77Tests {
    public byte[] createXlsx() throws IOException {
        try (ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
            Workbook wb = new Workbook(buf, "issue77", "0.0");
            Worksheet sheet = wb.newWorksheet("sheet1");
            sheet.value(0, 0, false);
            wb.finish();
            return buf.toByteArray();
        }
    }

    @Test
    void test() throws IOException {
        try (InputStream in = new ByteArrayInputStream(createXlsx());
             ReadableWorkbook wb = new ReadableWorkbook(in)) {
            List<Row> sheet1 = wb.getFirstSheet().read();
            assertEquals(
                    false,
                    sheet1.get(0).getCellAsBoolean(0).get()
            );
        }
    }
}
