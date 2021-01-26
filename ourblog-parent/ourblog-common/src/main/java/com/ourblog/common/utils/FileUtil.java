package com.ourblog.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    /**
     * @Description：获取某个目录下所有直接下级文件，不包括目录下的子目录的下的文件，所以不用递归获取
     */
    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
                //文件名，不包含路径
                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        }
        return files;
    }

    /**
     * 判断指定的文件或文件夹删除是否成功
     *
     * @param FileName 文件或文件夹的路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean deleteAnyone(String FileName) {

        //根据指定的文件名创建File对象
        File file = new File(FileName);

        //要删除的文件不存在
        if (!file.exists()) {
            System.out.println("文件" + FileName + "不存在，删除失败！");
            return false;
        } else { //要删除的文件存在

            //如果目标文件是文件
            if (file.isFile()) {
                return deleteFile(FileName);
            } else {  //如果目标文件是目录
                return deleteDir(FileName);
            }
        }
    }


    /**
     * 判断指定的文件删除是否成功
     *
     * @param fileName 文件路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean deleteFile(String fileName) {

        //根据指定的文件名创建File对象
        File file = new File(fileName);

        //要删除的文件存在且是文件
        if (file.exists() && file.isFile()) {

            if (file.delete()) {
                System.out.println("文件" + fileName + "删除成功！");
                return true;
            } else {
                System.out.println("文件" + fileName + "删除失败！");
                return false;
            }
        } else {

            System.out.println("文件" + fileName + "不存在，删除失败！");
            return false;
        }


    }


    /**
     * 删除指定的目录以及目录下的所有子文件
     *
     * @param dirName is 目录路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean deleteDir(String dirName) {

        //dirName不以分隔符结尾则自动添加分隔符
        if (dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }
        //根据指定的文件名创建File对象
        File file = new File(dirName);

        //目录不存在
        if (!file.exists() || (!file.isDirectory())) {
            System.out.println("目录删除失败" + dirName + "目录不存在！");
            return false;
        }

        //列出源文件下所有文件，包括子目录
        File[] fileArrays = file.listFiles();

        //将源文件下的所有文件逐个删除
        for (int i = 0; i < fileArrays.length; i++) {
            deleteAnyone(fileArrays[i].getAbsolutePath());
        }

        //删除当前目录
        if (file.delete()) {
            System.out.println("目录" + dirName + "删除成功！");
        }

        return true;

    }
}
