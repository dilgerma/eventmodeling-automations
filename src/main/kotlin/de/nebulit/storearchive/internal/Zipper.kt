package de.nebulit.storearchive.internal

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream as ZipOutputStream1
import org.springframework.stereotype.Component

@Component
class Zipper {

  fun zipFiles(files: List<String>, zipFilePath: String): String {
    val filePath = File.createTempFile(zipFilePath, ".zip")
    val buffer = ByteArray(1024)
    ZipOutputStream1(/* out = */ BufferedOutputStream(FileOutputStream(filePath))).use { zos ->
      files.forEach { file ->
        FileInputStream(file).use { fis ->
          BufferedInputStream(fis).use { bis ->
            val zipEntry = ZipEntry(File(file).name)
            zos.putNextEntry(zipEntry)
            var length: Int
            while (bis.read(buffer).also { length = it } >= 0) {
              zos.write(buffer, 0, length)
            }
          }
        }
      }
    }
    return filePath.absolutePath
  }
}
