package UI;

import sqlconnect.ConnectSqlsever;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

import static run.SwingConsole.run;

public class Contract extends JFrame {
    ConnectSqlsever connect=new ConnectSqlsever();
    String[] user={"czh","czm"};
    JTable table;
    Container con;
    //JButton button1=new JButton("确认");
    //JButton button2=new JButton("拒绝");
    JButton button3=new JButton("返回");
    JPanel panel=new JPanel();
    Object[] name={"合同号","房产号","租期（月）","租金","账户名（房主）","账户名（租户）","是否核实","开始日期","截至日期"};
    String [][]n=new String[100][9];

    Contract(String id,int i){
        String[][] strings=connect.select("select * from Contract where "+user[i]+" = "+'\''+id+'\'');
        if(strings.length!=0)
        {
            String[] st = new String[strings.length];
            int numbers = 0;
            for (int j = 0; j < strings.length; j++) {
                st[j] = strings[j][8].replace("-", "");
                Calendar calendar = Calendar.getInstance();
                Date date = new Date();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                String R = "" + year + month + day;
                String h[]=strings[j][8].split("-");
                if (year>Integer.parseInt(h[0]) ||month>Integer.parseInt(h[1]) ||day>Integer.parseInt(h[2])){
                    st[j] = "" + 1;
                    if(connect.update("DELETE  Contract WHERE Cht="+strings[j][0]))
                        connect.update("update house set hsz=0 where hfc=\'"+strings[j][1]+'\'');
                    if(connect.update("DELETE  Contract WHERE Cht="+strings[j][0]))
                    {
                        JOptionPane.showMessageDialog(null,"过期已删除");
                    }

                } else {
                    st[j] = "" + 0;
                    for(int k=0;k<strings[0].length;k++)
                    {
                        n[numbers][k]=new String(strings[j][k]);
                    }
                    numbers++;
                }
            }
            strings=n;
        }
        //strings更改
        table=new JTable(n,name);


        //panel.add(text);
        con=getContentPane();
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        //panel.add(button1);
        //panel.add(button2);
        panel.add(button3);
        con.add(panel,BorderLayout.SOUTH);
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r=table.getSelectedRow();
                if(i==0){
                    if(n[r][6].trim().equals("0")) {

                        int opt = JOptionPane.showConfirmDialog(null, "是否同意",
                                "核实信息", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (opt == JOptionPane.YES_OPTION) {
                            n[r][6] = "" + 1;
                            connect.update("update contract set chs=1 where cht =" + '\'' + n[r][0] + '\'');
                            connect.update("update House set Hsz  = \'" + 1 + '\'' + " where hfz =\'" + id + '\'');


                        } else if (opt == JOptionPane.NO_OPTION) {
                            n[r][6] = "" + 2;
                            connect.update("update contract set chs=2 where cht =" + '\'' + n[r][0] + '\'');
                        } else
                            return;
                    }
                    else
                        JOptionPane.showMessageDialog(null,"已审核");
                }
                else
                {
                    if(n[r][6].trim().equals("1"))
                        JOptionPane.showMessageDialog(null,"审核通过");
                    else if(n[r][6].trim().equals("0"))
                        JOptionPane.showMessageDialog(null,"审核中");
                    else
                        JOptionPane.showMessageDialog(null,"审核失败");
                }
                table.repaint();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Contract.this.dispose();
            }
        });
    }

    public static void main(String args[]) { run(new Contract("琴",1),700,400);}
}
