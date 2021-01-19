package sqlconnect;

public class ConnectSqlsever extends Connect {
    @Override
    void init() {
        connection = null;
        driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库引擎
        dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=honoka";//数据源  ！！！注意若出现加载或者连接数据库失败一般是这里出现问题
        Name = "sa";
        Pwd = "123456";
        stmt = null;
    }
    public static void main(String args[]){
        ConnectSqlsever sever=new ConnectSqlsever();
        //String string[][]=sever.select("select ozh,omm,orz from Homeowners");
        System.out.println(sever.create());
        //System.out.println(string.length);
        System.out.println(sever.addData());
    }
}