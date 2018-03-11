/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author huabing li
 */
public class MyPath {
 public static String getProjectPath() {
 java.net.URL url = MyPath.class.getProtectionDomain().getCodeSource().getLocation();
 String filePath = null;
 try {
 filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
 } catch (UnsupportedEncodingException e) {
 }
 if (filePath.endsWith(".jar"))
 filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
 java.io.File file = new java.io.File(filePath);
 filePath = file.getAbsolutePath();
 return filePath;
 }

 public static String getRealPath() {
 String realPath = MyPath.class.getClassLoader().getResource("").getFile();
 java.io.File file = new java.io.File(realPath);
 realPath = file.getAbsolutePath();
 try {
 realPath = java.net.URLDecoder.decode(realPath, "utf-8");
 } catch (UnsupportedEncodingException e) {
 }
 return realPath;
 }
}
