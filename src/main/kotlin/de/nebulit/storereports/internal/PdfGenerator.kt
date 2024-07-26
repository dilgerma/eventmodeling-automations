package de.nebulit.storereports.internal

import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import org.springframework.stereotype.Component

@Component
class PdfGenerator {
  fun generate(filename: String, country: String, breweries: List<Brewery>): String {

    var file = File.createTempFile(filename, ".pdf")

    // Initialize PDF document
    val pdf = Document()
    val writer = PdfWriter.getInstance(pdf, FileOutputStream(file.absolutePath))

    // Initialize document
    pdf.open()
    // Add each string as a new line in the PDF
    for (str in breweries.map { it.name.trim() }) {
      pdf.add(Paragraph(str))
    }

    // Close the document
    pdf.close()
    writer.flush()

    println(file.absolutePath)
    return file.absolutePath
  }
}
