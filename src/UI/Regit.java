package UI;

import sqlconnect.ConnectSqlsever;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static run.SwingConsole.run;

public class Regit extends JFrame {
    JRadioButton r1=new JRadioButton("房主");
    JRadioButton r2=new JRadioButton("租户");
    JPanel panel=new JPanel();
    int slt=-1;
    ConnectSqlsever connect=new ConnectSqlsever();
    String name[][]={{"Ozh","Omm","Oxm","Oxb","Odh","Odz"},{"Uzh","Uma","Uxm","Uxb","Uld","Ulz"}};
    String labelName[]={"账户","密码","姓名","性别","联系电话","联系地址"};
    String []database= {"Homeowners","TenantUser"};
    JLabel label[]=new JLabel[6];
    JTextField text[]=new JTextField[6];
    JButton button=new JButton("确认");
    JButton button1=new JButton("取消");
    Regit(){
        for(int i=0;i<6;i++)
        {
            label[i]=new JLabel(labelName[i]);
        }
        text[0]=new JTextField(10);
        text[1]=new JTextField(10);
        text[2]=new JTextField(10);
        text[3]=new JTextField(2);
        text[4]=new JTextField(11);
        text[5]=new JTextField(50);

        setLayout(new FlowLayout());

        add(label[0]);add(text[0]);
        add(label[1]);add(text[1]);
        add(label[2]);add(text[2]);
        add(label[3]);add(text[3]);
        add(label[4]);add(text[4]);
        add(label[5]);add(text[5]);
        panel.add(r1);
        panel.add(r2);
        add(panel);
        add(button);
        add(button1);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg[]=new String[6];
                for(int i=0;i<6;i++)
                    msg[i]=new String(text[i].getText());
                if(connect.update("insert into "+database[slt]+" Values(\'"+msg[0]+"\',\'"+msg[1]+"\',\'"
                        +msg[2]+"\',\'"+msg[3]+"\',\'"+msg[4]+"\',\'"+msg[5]+"\')"))
                    JOptionPane.showMessageDialog(null,"注册成功");
                else
                    JOptionPane.showMessageDialog(null,"注册失败");
                Regit.this.dispose();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Regit.this.dispose();
            }
        });
        r1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slt=0;
                r2.setSelected(false);
            }
        });
        r2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slt=1;
                r1.setSelected(false);
            }
        });
    }
    public static void main(String[] args){ run(new Regit(),700,400);}
}
