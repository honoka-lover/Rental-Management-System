package sqlconnect;


public class ConnectMysql extends Connect {
    void init(){
        connection = null;
        driverName = "com.mysql.cj.jdbc.Driver";//SQL数据库引擎
        dbURL = "jdbc:mysql://localhost:3306/honoka?serverTimezone=UTC";//数据源  ！！！注意若出现加载或者连接数据库失败一般是这里出现问题
        Name = "root";
        Pwd = "123456";
        stmt = null;
    }
    public static void main(String args[]){
        ConnectMysql mysql=new ConnectMysql();
        //String string[][]=mysql.select("select ozh,omm,orz from Homeowners");
        System.out.println(mysql.create());
        //System.out.println(string.length);
        System.out.println(mysql.addData());
    }
}

