package UI;

import sqlconnect.ConnectSqlsever;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import static run.SwingConsole.*;


public class Main extends JFrame {
    private JTextField jTextField1=new JTextField(15);//账户名
    private JTextField jTextField2=new JTextField(15);//密码
    JLabel label1=new JLabel("用户名");
    JLabel label2=new JLabel("密码");
    JButton b=new JButton("登录");
    JButton c=new JButton("退出");
    JButton d=new JButton("注册");
    String user,password;
    ConnectSqlsever connect=new ConnectSqlsever();


    public Main() {
        ActionB actionB=new ActionB();//登录事件实例
        ActionC actionC=new ActionC();//退出事件实例
        ActionD actionD=new ActionD();//注册事件实例
        b.addActionListener(actionB);//与组件关联
        c.addActionListener(actionC);
        d.addActionListener(actionD);
        setLayout(new FlowLayout());
        add(label1);//用户名
        add(jTextField1);
        add(label2);//密码
        add(jTextField2);
        add(b);//登录
        add(c);//退出
        add(d);//注册
    }
    boolean check(String sql){
        boolean sucess=false;
        String strings[][]=connect.select(sql);
        int i;
        for(i=0;i<strings.length;i++)
        {
            if(user.equals(strings[i][0].trim())) {
                if (password.equals(strings[i][1].trim())) {
                    sucess = true;
                    break;
                }
                break;
            }
        }
        return sucess;
    }
    class ActionB implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //读取账号，密码
            user=jTextField1.getText().trim();
            password=jTextField2.getText().trim();
            boolean ck=check("select ozh,omm from Homeowners");
            if(ck==true)//房主
            {
                Main.this.dispose();
                run(new House(user),500,400);
            }
            else {
                ck=check("select Uzh,uma from TenantUser");
                if(ck==false)//租户
                    JOptionPane.showMessageDialog(null,"账号或密码错误！");
                else
                {
                    Main.this.dispose();
                    run(new Tenant(user),500,400);
                }
            }
        }
    }
    class ActionC implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            dispatchEvent(new WindowEvent(Main.this,WindowEvent.WINDOW_CLOSING) );
        }
    }
    class ActionD implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            run(new Regit(),700,200);
        }
    }

    public static void main(String[] args){
        run(new Main(),200,300);
    }
}
