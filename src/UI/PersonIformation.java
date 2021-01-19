package UI;

import sqlconnect.ConnectSqlsever;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static run.SwingConsole.run;

public class PersonIformation extends JFrame {
    ConnectSqlsever connect = new ConnectSqlsever();
    JButton button=new JButton("返回");
    JLabel label;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JLabel label5;
    int sel=-1;
    String[][] iformation=null;
    String name[][]={{"Ozh","Omm","Oxm","Oxb","Odh","Odz"},{"Uzh","Uma","Uxm","Uxb","Uld","Ulz"}};
    String msg=null;
    PersonIformation(String id) {
        String sql = "select * from Homeowners where ozh = \'" + id + "\'";
        iformation=connect.select(sql);
        if(iformation!=null&&iformation.length!=0) {
            sel = 0;
            //JOptionPane.showMessageDialog(null,"error");
        }else {
            sql = "select * from TenantUser where uzh = \'" + id + "\'";
            iformation=connect.select(sql);
            sel=1;}
        String database=sel==0?"Homeowners":"TenantUser";
        label=new JLabel("账号 : "+iformation[0][0].trim());
        label1=new JLabel("密码 : "+iformation[0][1].trim());
        label2=new JLabel("姓名 : "+iformation[0][2].trim());
        label3=new JLabel("性别 : "+iformation[0][3].trim());
        label4=new JLabel("联系电话 : "+iformation[0][4].trim());
        label5=new JLabel("联系地址 : "+iformation[0][5].trim());
        setLayout(new FlowLayout());
        add(label);
        add(label1);
        add(label2);
        add(label3);
        add(label4);
        add(label5);
        add(button);
        label1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msg=JOptionPane.showInputDialog(null,"要修改为 ?");
                if(msg==null||msg.trim().equals(""))
                    return;
                else{
                    if(connect.update("update "+database+" set "+name[sel][1]+" =\'"+msg+"\'"+"  where "+name[sel][0]+" ="+'\''+id+'\''))
                        JOptionPane.showMessageDialog(null,"修改成功");
                    else
                        JOptionPane.showMessageDialog(null,"修改失败！");

                }
            }
        });
        label2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msg=JOptionPane.showInputDialog(null,"要修改为 ?");
                if(msg==null||msg.trim().equals(""))
                    return;
                else{
                    if(connect.update("update "+database+" set "+name[sel][2]+" =\'"+msg+"\'"+"  where "+name[sel][0]+" ="+'\''+id+'\''))
                        JOptionPane.showMessageDialog(null,"修改成功");
                    else
                        JOptionPane.showMessageDialog(null,"修改失败！");

                }
            }
        });
        label3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msg=JOptionPane.showInputDialog(null,"要修改为 ?");
                if(msg==null||msg.trim().equals(""))
                    return;
                else{
                    if(connect.update("update "+database+" set "+name[sel][3]+" =\'"+msg+"\'"+"  where "+name[sel][0]+" ="+'\''+id+'\''))
                        JOptionPane.showMessageDialog(null,"修改成功");
                    else
                        JOptionPane.showMessageDialog(null,"修改失败！");

                }
            }
        });
        label4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msg=JOptionPane.showInputDialog(null,"要修改为 ?");
                if(msg==null||msg.trim().equals(""))
                    return;
                else{
                    if(connect.update("update "+database+" set "+name[sel][4]+" =\'"+msg+"\'"+"  where "+name[sel][0]+" ="+'\''+id+'\''))
                        JOptionPane.showMessageDialog(null,"修改成功");
                    else
                        JOptionPane.showMessageDialog(null,"修改失败！");

                }
            }
        });
        label5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msg=JOptionPane.showInputDialog(null,"要修改为 ?");
                if(msg==null||msg.trim().equals(""))
                    return;
                else{
                    if(connect.update("update "+database+" set "+name[sel][5]+" =\'"+msg+"\'"+"  where "+name[sel][0]+" ="+'\''+id+'\''))
                        JOptionPane.showMessageDialog(null,"修改成功");
                    else
                        JOptionPane.showMessageDialog(null,"修改失败！");

                }
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PersonIformation.this.dispose();
            }
        });

    }
    public static void main(String args[]){
        run(new PersonIformation("Liv"),400,100);
    }
}
