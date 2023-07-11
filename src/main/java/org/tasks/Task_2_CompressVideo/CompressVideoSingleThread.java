package org.tasks.Task_2_CompressVideo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.zip.GZIPOutputStream;

public class CompressVideoSingleThread {
    // 380 MB сжимается в 1 поток за 10сек

    public static void main(String[] args) {
        String inputFile = "/Users/fisher/Desktop/inputVideo.avi";
        String outputFile = "/Users/fisher/Desktop/compressedVideo.gz";

        Instant startTime = Instant.now();

        try {
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                gzipOutputStream.write(buffer, 0, bytesRead);
            }

            gzipOutputStream.close();
            fileOutputStream.close();
            fileInputStream.close();

            Duration duration = Duration.between(startTime, Instant.now());
            System.out.printf("Видео успешно сжато в GZIP за %d мс", duration.toMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

