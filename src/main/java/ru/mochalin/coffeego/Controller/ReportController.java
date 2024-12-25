package ru.mochalin.coffeego.Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mochalin.coffeego.Model.Bill;
import ru.mochalin.coffeego.Repository.BillRepository;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ReportController {

    @Autowired
    private BillRepository billRepository;

    @GetMapping("/report")
    public String reportPage() {
        return "report";
    }
    

    @GetMapping("/generate-report")
    public void generateReport(
            HttpServletResponse response,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
    ) throws IOException, DocumentException {
        List<Bill> bills = billRepository.findByOrderDateBetween(startDate, endDate);

        // Установка типа ответа
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");

        // Создание документа
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Загрузка шрифта
        String fontPath = new File("src/main/resources/static/fonts/ofont.ru_Roboto.ttf").getAbsolutePath();
        BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12, Font.NORMAL);

        // Заголовок
        Paragraph title = new Paragraph("Отчёт по заказам", new Font(baseFont, 18, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("Период: " + startDate + " - " + endDate, font));
        document.add(new Paragraph(" "));

        // Создание таблицы
        PdfPTable table = new PdfPTable(3); // 3 колонки: ID, Сумма, Дата
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Заголовки столбцов
        addTableHeader(table, font, "ID Заказа", "Сумма", "Дата");

        // Заполнение таблицы данными
        double totalRevenue = 0;
        for (Bill bill : bills) {
            addTableRow(table, font, String.valueOf(bill.getId()), 
                        String.format("%.2f ₽", bill.getTotalPrice()), 
                        bill.getOrderDate().toString());
            totalRevenue += bill.getTotalPrice();
        }

        // Добавляем таблицу в документ
        document.add(table);

        // Итого
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Общая выручка: " + String.format("%.2f ₽", totalRevenue), font));

        document.close();
    }

    private void addTableHeader(PdfPTable table, Font font, String... headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }
    }

    private void addTableRow(PdfPTable table, Font font, String... values) {
        for (String value : values) {
            PdfPCell cell = new PdfPCell(new Phrase(value, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }
}
