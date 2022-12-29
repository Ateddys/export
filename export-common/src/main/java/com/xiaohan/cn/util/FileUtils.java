package com.xiaohan.cn.util;

/**
 * 导出数据写入响应
 *
 * @author teddy
 * @since 2022/12/29
 */
public class FileUtils {
//    public static void export(Response serviceResponse, HttpServletResponse response) {
//        InputStream inputStream = null;
//        try {
//            Response.Body body = serviceResponse.body();
//            inputStream = body.asInputStream();
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//            // 响应头
//            serviceResponse.headers().forEach((k,v)-> response.setHeader(k, v.toArray()[0].toString()));
//            response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
//            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
//            int length = 0;
//            byte[] temp = new byte[1024 * 10];
//            while ((length = bufferedInputStream.read(temp)) != -1) {
//                bufferedOutputStream.write(temp, 0, length);
//            }
//            bufferedOutputStream.flush();
//            bufferedOutputStream.close();
//            bufferedInputStream.close();
//            inputStream.close();
//        } catch (IOException e) {
//            throw new BaseException("EXPORT_EXCEL_FAIL", e);
//        }
//    }
}
