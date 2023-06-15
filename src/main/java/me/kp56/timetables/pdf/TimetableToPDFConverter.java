package me.kp56.timetables.pdf;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import me.kp56.timetables.timetable.Group;
import me.kp56.timetables.students.Student;
import me.kp56.timetables.timetable.Subject;
import me.kp56.timetables.timetable.Timetable;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TimetableToPDFConverter {
    public final Timetable timetable;

    public TimetableToPDFConverter(Timetable timetable) {
        this.timetable = timetable;
    }

    public void toPDF(File file, Predicate<? super Subject> filter) throws FileNotFoundException {
        PdfDocument document = new PdfDocument(new PdfWriter(file));
        document.setDefaultPageSize(new PageSize(1000, 600));
        Document doc = new Document(document);

        Table table = new Table(new float[] {1,1,1,1,1,1,1,1,1,1,1,1}, false);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);


        table.addCell(new Cell().add(new Paragraph("Day")));
        for (int i = 1; i <= 11; i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
        }

        for (int i = 0; i < 5; i++) {
            List<IBlockElement> elements = new ArrayList<>();
            elements.add(new Paragraph(day(i)));
            List<Group> groups = timetable.days[i];
            for (int j = 0; j < 11; j++) {
                if (groups.size() > j) {
                    Table subtable = new Table(1);
                    List<String> contents = new ArrayList<>();
                    for (Subject s : groups.get(j).subjects
                            .stream()
                            .filter(filter)
                            .toList()) {
                        contents.add(s.toString().replace("_", " "));
                    }
                    createSubtable(subtable, contents);
                    elements.add(subtable);
                } else {
                    elements.add(new Paragraph(""));
                }
            }

            createRow(table, elements);
        }

        doc.add(table);

        document.close();
    }

    public void toPDF(File file) throws FileNotFoundException {
        toPDF(file, (subject) -> true);
    }

    public void toPDF(File file, Student student) throws FileNotFoundException {
        toPDF(file, (subject) -> student.subjects.contains(subject));
    }

    private static String day(int i) {
        return DayOfWeek.values()[i].toString();
    }

    public void createSubtable(Table table, List<String> subtableContents) {
        for (String s : subtableContents) {
            table.addCell(new Cell()
                    .add(new Paragraph(s)).setBorder(Border.NO_BORDER))
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
        }
    }

    public void createRow(Table table, List<IBlockElement> row) {
        for (IBlockElement s : row) {
            table.addCell(new Cell()
                    .add(s))
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
        }
    }
}
