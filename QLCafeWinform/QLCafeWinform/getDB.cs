using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MySql.Data.MySqlClient;

namespace QLCafeWinform
{
    public class getDB
    {
        public static MySqlConnection getData()
        {
            string host = "tnvinhtuong0299.xyz";
            string dbname = "tnvinhtu_quanlyquancafe";
            int port = 3306;
            string username = "tnvinhtu_vinhtuong01";
            string password = "0299vinhTUONG";

            return Connect.mysqlConnect(host, dbname, port, username, password);
        }
    }
}
