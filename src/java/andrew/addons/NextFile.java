/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package andrew.addons;

import java.io.File;

/**
 *
 * @author Andrew
 */
import org.apache.commons.io.FilenameUtils;

public class NextFile {
    public static String nextFile(String filename) {
        int a = 1;
        File file = new File(filename);
        while (file.exists() || file.isFile()) {
            file = new File(FilenameUtils.removeExtension(filename) + "(" + Integer.toString(a) + ")." + FilenameUtils.getExtension(filename));
            a++;
        }
        return file.getName();
    }
    public static String findExt(String folder,String file) {
        File dir = new File(folder);
        File[] list = dir.listFiles();
        if (list != null) {
            for (File fil : list) {
                if (file.equals(FilenameUtils.removeExtension(fil.getName()))) return fil.getName();
            }
        }
        return "";
    }
}