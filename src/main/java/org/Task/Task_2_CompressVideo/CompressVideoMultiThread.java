package org.Task.Task_2_CompressVideo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.zip.GZIPOutputStream;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompressVideoMultiThread {
    // 380 MB сжимается в 4 потока за 12сек
    // 380 MB сжимается в 8 потока за 12сек

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        String inputFile = "/Users/fisher/Desktop/inputVideo.avi";
        String outputFile = "/Users/fisher/Desktop/compressedVideo.gz";

        Instant startTime = Instant.now();
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);

            ExecutorService executorService = Executors.newFixedThreadPool(8); // Количество потоков

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            int totalBytesRead = 0;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byte[] data = new byte[bytesRead];
                System.arraycopy(buffer, 0, data, 0, bytesRead);
                int finalTotalBytesRead = totalBytesRead;

                executorService.submit(() -> {
                    try {
                        synchronized (gzipOutputStream) {
                            gzipOutputStream.write(data, 0, data.length);
                            System.out.println("Поток " + Thread.currentThread().getName() +
                                    " обработал " + data.length + " байт (с " + finalTotalBytesRead + " по " +
                                    (finalTotalBytesRead + data.length) + ")");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                totalBytesRead += bytesRead;
            }

            executorService.shutdown();
            while (!executorService.isTerminated()) {
                // Ждем завершения всех потоков
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


