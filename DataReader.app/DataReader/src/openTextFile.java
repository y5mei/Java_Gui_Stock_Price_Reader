import javax.swing.*;
import java.io.File;

public class openTextFile extends JFrame {


    public static File start() {

        String dir = System.getProperty("user.dir");
        File directory = new File(dir);
        final JFileChooser fc = new JFileChooser(directory);//创建文件选择对话框
        fc.setVisible(true);
        int returnValue = fc.showOpenDialog(null);//打开文件选择对话框
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            //判断用户是否选择了文件
            File file = fc.getSelectedFile();    //获得文件对象
            return file;
        } else {
            return null;
        }


    }


    public static void main(String[] args) {
        openTextFile gui = new openTextFile();
        gui.start();
    }

}

