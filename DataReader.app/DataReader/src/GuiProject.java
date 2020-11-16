import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class GuiProject extends JFrame implements ActionListener {
    //Define Constant
    private JFrame frame;
    private JPanel plotPanle;
    private JPanel textPanle;
    private JPanel plot;
    private JPanel buttonPanle;
    private JButton readButton, closeButton, clearButton;
    private JTextArea ta_showText;
    private JTextArea ta_showProperty;    //定义显示文件内容的文本域
    private JScrollPane scroller;
    private TimeSeries my_timeSeries; // 需要画出来的 timeseries
    private String my_plotName; // The name of the CSV File
    private ArrayList<TimeSeries> mydataset = new ArrayList<TimeSeries>();
    private ArrayList<String> mynameset = new ArrayList<String>();
    private TimeSeriesCollection dataset = new TimeSeriesCollection();

    public void start() {
        frame = new JFrame("Please download a historical Data CSV file from Yahoo Finance");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1) declair the objects in the frame
        textPanle = new JPanel();
        plotPanle = new JPanel();
        buttonPanle = new JPanel();
        frame.add(textPanle, BorderLayout.NORTH);
        frame.add(plotPanle, BorderLayout.CENTER);
        frame.add(buttonPanle, BorderLayout.SOUTH);


        //2) declair the objects in each panel
        // 先是 文本框
        ta_showText = new JTextArea(15, 50);
        scroller = new JScrollPane(ta_showText);
        //设置滚动条
        scroller.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textPanle.add(scroller);

        // 然后是图片框
        JLabel label = new JLabel("Current Working Directory = " + System.getProperty("user.dir") + "\n");
        plotPanle.setLayout(new BorderLayout());
        plotPanle.add(label, BorderLayout.NORTH);
        plot = TimeSeriesDemo1.createDemoPanel();
        plotPanle.add(plot, BorderLayout.CENTER);


        // 最后是按钮框
        buttonPanle.setLayout(new GridLayout(1, 3));
        JButton mybuttonzhu = new JButton();
        JButton mybuttonniu = new JButton();
        JButton mybuttonxiong = new JButton();

        try {
            // 不知道为什么，给 button load 图片必须要用local variable, 否则找不到图片路径
            Image iconxiong = ImageIO.read(getClass().getResource("/picture/xiong.png"));
            Image iconzhu = ImageIO.read(getClass().getResource("/picture/zhu.png"));
            Image iconniu = ImageIO.read(getClass().getResource("/picture/niu.png"));

            mybuttonxiong.setIcon(new ImageIcon(iconxiong));
            mybuttonxiong.setText("Close");
            mybuttonzhu.setIcon(new ImageIcon(iconzhu));
            mybuttonzhu.setText("Clear");
            mybuttonniu.setIcon(new ImageIcon(iconniu));
            mybuttonniu.setText("Read");


            buttonPanle.add(mybuttonxiong);
            buttonPanle.add(mybuttonzhu);
            buttonPanle.add(mybuttonniu);

            readButton = mybuttonniu;
            closeButton = mybuttonxiong;
            clearButton = mybuttonzhu;

            readButton.addActionListener(this);
            closeButton.addActionListener(this);
            clearButton.addActionListener(this);
        } catch (Exception ex) {
            System.out.println("Can not find a picture");
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println(ex);
        }

        // 3) End, now pack and show everyting
        frame.pack();
        frame.setVisible(true);

    }


    // 设计按钮的动作
    public void actionPerformed(ActionEvent e) {
        Object buttonclicked = e.getSource();
        if (buttonclicked == closeButton) {
            System.exit(0);
        } else if (buttonclicked == clearButton) {
            System.out.println("clearbutton");
            ta_showText.setText("");
            //Clear the plot
            plotPanle.remove(plot);
            dataset = new TimeSeriesCollection();
            JFreeChart chart = PlotTimeSeries.createChart(dataset);
            plot = new ChartPanel(chart);


            plot.setVisible(true);
            plotPanle.add(plot, BorderLayout.CENTER);
            plotPanle.repaint();
            plotPanle.revalidate();

        } else if (buttonclicked == readButton) {

            File file = openTextFile.start();
            if (file != null) {
                //获得文件的绝对路径
                ta_showText.append("The Absolute Path of the File is：" + file.getAbsolutePath() + "\n");
                //是否为隐藏文件
                ta_showText.append("Is this File a Hidden File？" + file.isHidden() + "\n");
                String filename = file.getName();
                TimeSeries s1 = new TimeSeries(filename, Day.class); //声明一个TimeSeries Class
                addTimeSeries ats = new addTimeSeries(s1); // 声明一个 addTimeSeries 来编辑 TS 变量

                FileReader reader;    //声明字符流
                BufferedReader in;    //声明字符缓冲流
                try {
                    reader = new FileReader(file);    //创建字符流
                    in = new BufferedReader(reader);    //创建字符缓冲流
                    String info = in.readLine();    //从文件中读取一行信息
//                    info = in.readLine();    //从文件中读取一行信息
                    while (info != null) {
                        //判断是否读到内容
                        ta_showText.append(info + "\n");    //把读到的信息追加到文本域中
                        s1 = ats.append(info); //把读到的信息 addTimeSeires 中去[如果是 股票数据，则导入，否则，则掠过]
                        info = in.readLine();    //继续读下一行信息
                    }
                    in.close();    //关闭字符缓冲流
                    reader.close();    //关闭字符流
//                    my_timeSeries = s1;
//                    my_plotName = filename;
//                    PlotTimeSeries newplot = new PlotTimeSeries(my_plotName, my_timeSeries);
//                    plot = newplot.createPanel();

//                    mydataset.add(s1);
//                    mynameset.add(filename);

                    dataset.addSeries(s1);

                    plotPanle.remove(plot);
                    // 这个DataSet 是画图的关键，add 过几个 Timeseries，就可以画出几条线
                    JFreeChart chart = PlotTimeSeries.createChart(dataset);
                    plot = new ChartPanel(chart);

                    // 更新 Plot-Panel 的内容
                    plot.setVisible(true);
                    plotPanle.add(plot, BorderLayout.CENTER);
                    plotPanle.repaint();
                    plotPanle.revalidate();
//                    newplot.pack();
//                    RefineryUtilities.centerFrameOnScreen(newplot);
//                    newplot.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();    //输出栈踪迹
                }
            }
        }
    }

    public static void main(String[] args) {
        GuiProject gui = new GuiProject();
        gui.start();
    }

}
