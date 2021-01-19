package sqlconnect;

import java.sql.*;

public abstract class Connect extends SQLException {
    Connection connection = null;
    String driverName = null;//SQL数据库引擎
    String dbURL = null;//数据源  ！！！注意若出现加载或者连接数据库失败一般是这里出现问题
    String Name = null;
    String Pwd = null;
    Statement stmt = null;
    abstract  void init();
    Connect() {
        init();
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbURL, Name, Pwd);
            //System.out.println("连接数据库成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接失败");
        }
    }

public boolean addData(){
        String sql="INSERT\n" +
                "INTO Homeowners\n" +
                "VALUES('白岂','qweasd','祖明朗','男','13934535869','离川市扶苏区'),\n" +
                "('青卓','qw8354','黎云姿','女 ','13608046587','离川市祁荷区'),\n" +
                "('岩王','isklee','钟离','男','15908052371','璃月市穷奇区'),\n" +
                "('西风骑士','666666','凯瑟琳','女','18280578945','蒙德市埃沙迪区'),\n" +
                "('EVE','sadhjk','何艺','女','13608046587','离川市祁荷区'),\n" +
                "('青菜白玉汤','wqesad','丁舒升','男','13678992254','天津市高新区'),\n" +
                "('落尘','dfgewrq','陆离','男','13689780456','南京市建邺区'),\n" +
                "('今夕何夕','zxcjlk','丁牧宸','男','13980854567','扬州市广陵区')";
        update(sql);
        sql="INSERT\n" +
                "INTO House\n" +
                "VALUES('1','白岂','132平米','扶苏区天华小区','','0','2600'),\n" +
                "      ('2','岩王','98平米','祁荷区华侨城','','0','4000'),\n" +
                "      ('3','青卓','120平米','祁荷区华侨城','','0','5500'),\n" +
                "      ('4','西风骑士','60平米','穷奇区不归庐','','0','1200'),\n" +
                "      ('5','今夕何夕','56平米','埃沙迪区南街小区','','0','2000'),\n" +
                "      ('6','落尘','72平米','新都区月台','','0','1800'),\n" +
                "      ('7','EVE','80平米','高新区','','0','2200'),\n" +
                "      ('8','落尘','92平米','建邺区科技园','','0','3600'),\n" +
                "      ('9','青菜白玉汤','110平米','广陵区文昌阁','','0','4500')\n";
        update(sql);
        sql="INSERT\n" +
                "INTO TenantUser\n" +
                "                VALUES('夜未明','qwe4568','藏星河','男','19802086572','南华市未央区'),\n" +
                "                ('绯樱','fgdf865','白展暨','男','13596278512','成都市武侯区'),\n" +
                "                ('Liv','fkhg658','穆念慈','女','13602845726','运城市'),\n" +
                "                ('琴','cvbdfq','秦南琴','女','15952133972','太原市杏花岭区'),\n" +
                "                ('园臻','dfgfd9','陈昆','男','19802855755','晋中市'),\n" +
                "                ('铧','23548tr','徐瑞华','女','18382063597','西安市')";
        update(sql);
        return true;
}
public boolean create() {
    String homeOwners = "CREATE TABLE Homeowners( \n" +
            "Ozh CHAR(10) primary key not null,\n" +
            "Omm CHAR(10) not null,\n" +
            "Oxm CHAR(10) not null,\n" +
            "Oxb CHAR(2) CHECK(Oxb in('男','女')),\n" +
            "Odh CHAR(11) not null,\n" +
            "Odz CHAR(50))\n"
            + "alter table Homeowners\tadd constraint Omm check( len(Omm)>=6)\n"
            ;
    String house = "Create TABLE House(\n" +
            "Hfc CHAR(8) primary key not null,\n" +
            "Hfz CHAR(10) not null ,\n" +
            "Hmj CHAR(13) not null,\n" +
            "Hdz CHAR(20) not null,\n" +
            "Hjj CHAR(50) not null,\n" +
            "Hsz bit not null,\n" +
            "Hjg int not null )";
    String tenantUser = "CREATE TABLE TenantUser(\n" +
            "Uzh CHAR(10) primary key not null ,\n" +
            "Uma CHAR(10) not null,\n" +
            "Uxm CHAR(10) not null,\n" +
            "Uxb CHAR(2) CHECK(Uxb in('男','女')) ,\n" +
            "Uld CHAR(11) not null,\n" +
            "Ulz CHAR(50)) \n"
            + "alter table TenantUser add constraint Uma check( len(Uma)>=6)"
            ;
    String contract = "CREATE TABLE Contract(\n" +
            "            Cht CHAR(14) primary key not null ,\n" +
            "            Cfc CHAR(8) not null,\n" +
            "            Czq INTEGER not null,\n" +
            "            Czj char (10)not null,\n" +
            "            Czh CHAR(10) not null,\n" +
            "            Czm CHAR(10) not null,\n" +
            "            Chs SMALLINT not NULL ,\n" +
            "            Cbt DATE not NULL,\n" +
            "            Cdt DATE not NULL,\n" +
            "            FOREIGN KEY(Cfc) REFERENCES House(Hfc),\n" +
            "            FOREIGN KEY(Czh) REFERENCES Homeowners(Ozh),\n" +
            "            FOREIGN KEY(Czm) REFERENCES TenantUser(Uzh))";
    try {
        stmt = connection.createStatement();
        stmt.executeUpdate(homeOwners);
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
        //return false;
    }
    try {
        stmt = connection.createStatement();
        stmt.executeUpdate(house);
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
        //return false;
    }
    try {
        stmt = connection.createStatement();
        stmt.executeUpdate(tenantUser);
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
        //return false;
    }
    try {
        stmt = connection.createStatement();
        stmt.executeUpdate(contract);
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    return true;
}
public String[][] select(String sql)  {

        try {
        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int numberOfColumns = metaData.getColumnCount();//获得查询结果的列的个数。

        int num=0;
        while (rs.next()) num++;
        String[][] strings=new String[num][numberOfColumns];

        rs.beforeFirst();
        for(int j=0;rs.next();j++)
        for (int i = 0; i < numberOfColumns; i++) {
        strings[j][i] = rs.getString(i + 1);
        //System.out.println(strings[j][i]);
        }

        return strings;
        }catch (SQLException e)
        {
        e.printStackTrace();
        }
        return null;

        }
public boolean update(String sql)
        {
        try {
        stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        return true;
        }
        catch (SQLException e)
        {
        e.printStackTrace();
        return false;
        }
        }
}
