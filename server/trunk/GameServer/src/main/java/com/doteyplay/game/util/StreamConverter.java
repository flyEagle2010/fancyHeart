package com.doteyplay.game.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * 字符流转化为字节数组.
 */

public class StreamConverter{
    /** 字符流转化为字节数组 */
    public static byte[] toByteArray(InputStream input) throws IOException{
        int status = 0;
        final int blockSize = 5000;
        int totalBytesRead = 0;
        int blockCount = 1;
        byte[] dynamicBuffer = new byte[blockSize * blockCount];
        final byte[] buffer = new byte[blockSize];

        boolean endOfStream = false;
        while(!endOfStream){
            int bytesRead = 0;
            if(input.available() != 0){
                // data is waiting so read as much as is available
                status = input.read(buffer);
                endOfStream = (status == -1);
                if(!endOfStream){
                    bytesRead = status;
                }
            }
            else{
                // no data waiting so use the
                //one character read to block until
                // data is available or the end of the input stream is reached
                status = input.read();
                endOfStream = (status == -1);
                buffer[0] = (byte) status;
                if(!endOfStream){
                    bytesRead = 1;
                }
            }

            if(!endOfStream){
                if(totalBytesRead + bytesRead > blockSize * blockCount){
                    // expand the size of the buffer
                    blockCount++;
                    final byte[] newBuffer = new byte[blockSize * blockCount];
                    System.arraycopy(dynamicBuffer,0,
                                     newBuffer,0,totalBytesRead);
                    dynamicBuffer = newBuffer;
                }
                System.arraycopy(buffer,0,
                                 dynamicBuffer,totalBytesRead,bytesRead);
                totalBytesRead += bytesRead;
            }
        }

        // make a copy of the array of the exact length
        final byte[] result = new byte[totalBytesRead];
        if(totalBytesRead != 0){
            System.arraycopy(dynamicBuffer,0,result,0,totalBytesRead);

        }
        return result;
    }
}
