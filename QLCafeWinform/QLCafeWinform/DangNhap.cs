using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using DevExpress.XtraEditors;
using MySql.Data.MySqlClient;
using System.Data.Common;


namespace QLCafeWinform
{
    public partial class DangNhap : DevExpress.XtraEditors.XtraForm
    {
        public DangNhap()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            validation();
        }

        private void validation()
        {
            string tendn = txttendn.Text.Trim();
            string mk = txtmk.Text.Trim();
            string macv = "";
            string matkhau = "";
            MySqlConnection conn = getDB.getData();
            conn.Open();
            string sql = "select * from taikhoan where TK = '" + tendn + "'";
            try
            {
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = sql;
                using (DbDataReader reader = cmd.ExecuteReader())
                {
                    if (reader.HasRows)
                    {
                        while (reader.Read())
                        {
                            int idindex = reader.GetOrdinal("MaCV");
                            string ML = reader.GetString(idindex);
                            macv = ML;
                            int idindex2 = reader.GetOrdinal("MK");
                            string ML2 = reader.GetString(idindex2);
                            matkhau = ML2;
                        }
                    }
                }
                if(mk == matkhau)
                {
                    FrmMain n = new FrmMain(macv,tendn);
                    n.Show();
                    this.Hide();
                }
                else
                {
                    MessageBox.Show("Sai tên đăng nhập hoặc mật khẩu", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Error);
    
                }
            }
            catch
            {
                MessageBox.Show("Lỗi");
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }
        }

        private void txttendn_KeyPress(object sender, KeyPressEventArgs e)
        {
            
        }
    }
}