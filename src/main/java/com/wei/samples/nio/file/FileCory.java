package com.wei.samples.nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * copy file with nio
 * Created by wei on 15/5/13.
 */
public class FileCory {
    /**
     * copy with Transfer
     * @param originPath    originPath
     * @param targetPath    targetPath
     * @throws IOException
     */
    public static void copyWithTransfer(String originPath, String targetPath) throws IOException {
        FileInputStream inputStream = new FileInputStream(originPath);
        FileOutputStream outputStream = new FileOutputStream(targetPath);

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        inputChannel.transferTo(0, inputChannel.size(), outputChannel);
        //or
        //outputChannel.transferFrom(inputChannel,0,inputChannel.size());

        //FileInputStream，FileChannel关闭其中一个就可以
        inputChannel.close();
        outputChannel.close();
    }

    /**
     * copy with buffer
     * @param originPath    originPath
     * @param targetPath    targetPath
     * @throws IOException
     */
    public static void copyWithBuffer(String originPath, String targetPath) throws IOException {
        FileInputStream inputStream = new FileInputStream(originPath);
        FileOutputStream outputStream = new FileOutputStream(targetPath);

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer=ByteBuffer.allocate(200);

        while (inputChannel.read(buffer)>0){
            buffer.flip();
            outputChannel.write(buffer);
            buffer.clear();
        }
        inputChannel.close();
        outputChannel.close();
    }
}
