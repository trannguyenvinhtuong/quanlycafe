using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MySql.Data.MySqlClient;

namespace QLCafeWinform
{
    public class Connect
    {   
        public static MySqlConnection mysqlConnect(string host, string dbname, int port,string username, string password){
            String connS="Server="+host+";Database="+dbname+";port="+port+";User Id="+username+";password="+password;
            MySqlConnection conn=new MySqlConnection(connS);
            
            return conn;
            
        }
    }
}
