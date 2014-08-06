package com.doteyplay.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class UrlCatcher {

    public static String urlCatcher(String urlPage) {
        return urlCatcher(urlPage, "utf-8");
    }

    public static String urlCatcher(String urlPage, String charset) {
        if (urlPage == null)
            return null;

        java.net.HttpURLConnection tmpConnection = null;
        try {
            String tmpLine = "";
            StringBuffer tmpStrBuffer = new StringBuffer();
            java.net.URL tmpURL = new java.net.URL(urlPage);
            tmpConnection = (java.net.HttpURLConnection) tmpURL.openConnection();
            tmpConnection.setUseCaches(false);
            tmpConnection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Chinese Simplified) Opera 8.51");
            tmpConnection.connect();
            java.io.InputStream tmpUrlStream = tmpConnection.getInputStream();
            java.io.BufferedReader tmpReader = new java.io.BufferedReader(new java.io.InputStreamReader(tmpUrlStream,
                    (charset != null) ? charset : "utf-8"));
            while ((tmpLine = tmpReader.readLine()) != null) {
                tmpStrBuffer.append(tmpLine);
            }
            return tmpStrBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tmpConnection != null)
                tmpConnection.disconnect();
        }
        return null;
    }

    public static String socketHttpCatcher(String urlPage) {
        if (urlPage == null || urlPage.startsWith("http://") == false)
            return null;

        Socket socket = null;
        try {
            String tmpUrlContent = urlPage.substring(7);
            if (tmpUrlContent.length() == 0)
                return "";
            String tmpHost = "";

            int tmpPos = tmpUrlContent.indexOf('/');
            if (tmpPos > 0)
                tmpHost = tmpUrlContent.substring(0, tmpPos);
            else
                tmpHost = tmpUrlContent;

            StringBuffer tmpStrBuffer = new StringBuffer();
            InetAddress addr = InetAddress.getByName(tmpHost);
            // ����һ��Socket
            socket = new Socket(addr, 80);
            // ��������
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
            wr.write("GET " + urlPage + " HTTP/1.0\r\n");
            wr.write("HOST:" + tmpHost + "\r\n");
            wr.write("\r\n");
            wr.flush();

            // ���շ��صĽ��
            BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tmpLine;
            while ((tmpLine = rd.readLine()) != null) {
                tmpStrBuffer.append(tmpLine + "\r\n");
            }
            wr.close();
            rd.close();
            return tmpStrBuffer.toString();
        } catch (Exception e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {

                }
            }
        }
        return null;
    }

    public static String urlByteCatcher(String refurl, String refCharset) {
        String result = null;
        java.net.HttpURLConnection tmpConnection = null;
        try {
            if (refurl != null && refurl.length() > 0) {
                java.net.URL tmpURL = new java.net.URL(refurl);
                tmpConnection = (java.net.HttpURLConnection) tmpURL.openConnection();
                tmpConnection.setUseCaches(false);
                tmpConnection.setReadTimeout(60000);
                tmpConnection.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Chinese Simplified) Opera 8.51");
                tmpConnection.connect();
                java.io.InputStream tmpUrlStream = tmpConnection.getInputStream();
                ByteArrayOutputStream tmpBypeStream = new ByteArrayOutputStream();
                int b = tmpUrlStream.read();
                while (b >= 0) {
                    tmpBypeStream.write(b);
                    b = tmpUrlStream.read();
                }
                byte[] tmpByte = tmpBypeStream.toByteArray();

                if (refCharset == null)
                    refCharset = "UTF-8";
                result = new String(tmpByte, refCharset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tmpConnection != null)
                tmpConnection.disconnect();
        }
        return result;
    }

    public static void main(String[] args) {
        String CK_ONNET_URL = "http://wap.360lh.cn:12080/gp/r.asp?act=gs&gid=2";
        System.out.println(urlCatcher(CK_ONNET_URL,"gb2312"));
//        String tmpContent = socketHttpCatcher(tmpUrl);
//        System.out.println(tmpContent);
    }
}
