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

import static run.SwingConsole.run;

public class House extends JFrame {
    Object[] name={"房产号","房主号","面积","地址","简介","是否已租","价格(元/月)"};
    JLabel lable=new JLabel("管理模块(点击表格即可修改内容)");
    JButton button1=new JButton("新增");
    JButton button3=new JButton("退出");
    JButton button4=new JButton("个人信息");
    JButton button5=new JButton("合同");
    JPanel panel =new JPanel();
    //JPanel jp=new JPanel();

    JTextField text=new JTextField(8);
    JTable table;
    Container con;


    ConnectSqlsever connect=new ConnectSqlsever();

    String[][] strings;



    House(String id){//id:房主号

        strings=connect.select("select * from House where Hfz = \'"+id +"\'");
        table=new JTable(strings,name);
        panel.setBorder(new TitledBorder("功能"));
        panel.add(lable);
        panel.add(button4);
        panel.add(button5);
        panel.add(button1);
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
                String[] title={"Hfc","Hfz","Hmj","Hdz","Hjj","Hsz","Hjg"};
                Object[] options = {"修改","删除","取消"};
                int result= JOptionPane.showOptionDialog(null,"是否修改数据","数据修改",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                if(result==0)
                {
                    if(Integer.parseInt(strings[r][5].trim())==0) {
                        msg = JOptionPane.showInputDialog("请输入要改成的值");
                        if (msg == null || msg.trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "修改值不能为空！");
                            return;
                        } else if(c!=1&&!(c==5&&Integer.parseInt(strings[r][5].trim())==1)) {
                            strings[r][c] = msg;
                            if (connect.update(
                                    "update House set " + title[c] + " = \'" + msg + "\' where hfz = \'" + strings[r][1] + "\'"
                            )){
                                JOptionPane.showMessageDialog(null, "修改成功！");
                                strings[r][c] = msg;}
                            else
                                JOptionPane.showMessageDialog(null, "修改失败");
                        }else if(msg.trim()==strings[r][c].trim()) {
                        } else {
                            JOptionPane.showMessageDialog(null,"房主号和已出租房不允许修改！");
                        }

                    }else
                    {
                        javax.swing.JOptionPane.showMessageDialog(null,"已出租房屋无法修改！");
                    }
                }
                else if(result==1)
                {
                    if(Integer.parseInt(strings[r][5].trim())==0) {
                        String st0[][]=connect.select("select * from Contract where cfc = "+'\''+strings[r][0]+'\'');
                        if(st0==null) {
                            if(connect.update("delete from House where Hfc =\'" + strings[r][0]+"\'"))
                                JOptionPane.showMessageDialog(null, "删除成功！(请重新登录查看)");

                        }
                        else
                        {
                            for(int i=0;i<st0.length;i++)
                                if(st0[i][6].trim().equals("2"))
                                    connect.update("delete contract where cht =\'"+st0[i][0]+'\'');
                                else
                                    ;
                            st0=connect.select("select * from Contract where cfc = "+'\''+strings[r][0]+'\'');
                            //JOptionPane.showMessageDialog(null,""+st0);
                            if(st0.length<1)
                            {
                                if(connect.update("delete from House where Hfc =\'" + strings[r][0]+"\'"))
                                    JOptionPane.showMessageDialog(null, "删除成功！(请重新登录查看)");

                            }
                            else
                                JOptionPane.showMessageDialog(null,"请先拒绝该房的合约");

                        }

                    }else if(Integer.parseInt(strings[r][5].trim())==1)
                        JOptionPane.showMessageDialog(null,"已出租房屋无法删除！");
                }

                table.repaint();
            }
        });
        button1.addActionListener(new ActionListener() {//增加数据事件
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] msg=new String[7];
                for(int i=0;i<7;i++)
                    msg[i]=null;
                msg[1]=id;
                msg[5]=0+"";
                msg[0]=JOptionPane.showInputDialog(null,"请输入房产号:");
                if(msg[0]==null||msg[0].trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error");
                    return; }
                msg[2]=JOptionPane.showInputDialog(null,"请输入面积:");
                if(msg[2]==null||msg[2].trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error");
                    return; }
                msg[3]=JOptionPane.showInputDialog(null,"请输入地址:");
                if(msg[3]==null||msg[3].trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error");
                    return; }
                msg[4]=JOptionPane.showInputDialog(null,"请输入简介:");
                msg[6]=JOptionPane.showInputDialog(null,"请输入价格:");
                if(msg[6]==null||msg[6].trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error");
                    return; }
                if(connect.update("Insert into House values (\'"+msg[0]+"\',\'"+msg[1]+"\',\'"+msg[2]+"\',\'"
                        +msg[3]+"\',\'"+msg[4]+"\',\'"+msg[5]+"\',\'"+msg[6]+"\')"))
                    JOptionPane.showMessageDialog(null,"创建成功（请重新登录查看）");
                else
                    JOptionPane.showMessageDialog(null,"创建失败");
            }
        });
        button3.addActionListener(new ActionListener() {//退出
            @Override
            public void actionPerformed(ActionEvent e) {
                House.this.dispose();
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run(new PersonIformation(id),400,100);
            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run(new Contract(id,0),700,400);
            }
        });
    }

    public static void main(String[] args){
        run(new House("白岂"),700,400);
    }
}
