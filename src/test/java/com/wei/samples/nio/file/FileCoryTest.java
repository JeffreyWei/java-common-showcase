package com.wei.samples.nio.file;

import org.junit.Test;

import static com.wei.samples.nio.file.FileCory.copyWithBuffer;
import static com.wei.samples.nio.file.FileCory.copyWithTransfer;
import static org.junit.Assert.*;

/**
 * Created by wei on 15/5/14.
 */
public class FileCoryTest {

    @Test
    public void testCopyWithTransfer() throws Exception {
        copyWithTransfer("/Users/wei/Desktop/origin.png", "/Users/wei/Desktop/target.png");
    }

    @Test
    public void testCopyWithBuffer() throws Exception {
        copyWithBuffer("/Users/wei/Desktop/origin.png", "/Users/wei/Desktop/target.png");
    }
}