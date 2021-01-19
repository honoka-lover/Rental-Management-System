package UI;

import sqlconnect.Connect;
import sqlconnect.ConnectSqlsever;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

import static javax.swing.JOptionPane.showConfirmDialog;
import static run.SwingConsole.run;

public class Tenant extends JFrame {
    Object[] name={"房产号","房主","面积","地址","简介","是否已租","价格（元/月）"};
    JLabel lable=new JLabel("管理模块(点击表格选择租房)");
    JButton button3=new JButton("退出");
    JButton button4=new JButton("个人信息");
    JPanel panel =new JPanel();
    JTable table;
    Container con;
    JButton button5=new JButton("合同");


    ConnectSqlsever connect=new ConnectSqlsever();

    String[][] strings;



    Tenant(String id){//id:房主号

        strings=connect.select("select * from House ");
        table=new JTable(strings,name);
        panel.setBorder(new TitledBorder("功能"));
        panel.add(lable);
        panel.add(button4);
        panel.add(button5);
        panel.add(button3);

        //panel.add(text);
        con=getContentPane();
        getContentPane().add(new JScrollPane(table),BorderLayout.CENTER);

        con.add(panel,BorderLayout.SOUTH);
        table.getTableHeader().setReorderingAllowed(false);

        table.addMouseListener(new MouseAdapter(){//修改表格事件
            public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
                //得到选中的行列的索引值
                int r= table.getSelectedRow();//行数
                int c= table.getSelectedColumn();//列数
                String msg;
                int opt = JOptionPane.showConfirmDialog(null,
                        "是否要租该房？", "确认租房",
                        JOptionPane.YES_NO_OPTION);
                if (opt == JOptionPane.YES_OPTION) {
                    if(Integer.parseInt(strings[r][5])==1)
                        JOptionPane.showMessageDialog(null,"该房已租，无法选择");
                    else {
                        String mouth;
                        mouth=JOptionPane.showInputDialog(null,"请问要租几个月？");
                        if(mouth==null||mouth.trim().equals("")||Integer.parseInt(mouth)<=0)
                        {
                            JOptionPane.showMessageDialog(null,"获取月数失败");
                            return;
                        }
                        else{
                            Calendar calendar=Calendar.getInstance();
                            Date date=new Date();
                            int year=calendar.get(Calendar.YEAR);
                            int month=calendar.get(Calendar.MONTH);
                            int day=calendar.get(Calendar.DATE);
                            //year=month=day=0;
                            int year1=year+(month+Integer.parseInt(mouth))/12;
                            int month1=(month+Integer.parseInt(mouth))%12;
                            if(connect.update("insert into contract values (\'"+date.getTime()+"\',\'"+strings[r][0]
                                    +"\',\'"+Integer.parseInt(mouth)+"\',\'"+Integer.parseInt(mouth)*Double.parseDouble(strings[r][6])
                                    +"\',\'"+strings[r][1]+"\',\'"+id+"\',\'"+0+"\',\'"+
                                    year+"-"+(month+1)+"-"+day+ "\',\'"+year1+"-"+(month1+1)+"-"+day+"\')"))
                                JOptionPane.showMessageDialog(null,"预约成功");
                            else
                                JOptionPane.showMessageDialog(null,"预约失败");
                        }
                    }
                }

                table.repaint();
            }
        });


        button3.addActionListener(new ActionListener() {//退出
            @Override
            public void actionPerformed(ActionEvent e) {
                Tenant.this.dispose();
            }
        });
        button4.addActionListener(new ActionListener() {//个人信息
            @Override
            public void actionPerformed(ActionEvent e) {
                run(new PersonIformation(id),400,100);
            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run(new Contract(id,1),700,400);
            }
        });
    }

    public static void main(String[] args){
        run(new Tenant("琴"),700,400);
    }
}
