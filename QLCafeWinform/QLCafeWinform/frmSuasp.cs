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

    public partial class frmSuasp : DevExpress.XtraBars.Ribbon.RibbonForm
    {
       
        private string mamon1;
        
        public frmSuasp()
        {
            InitializeComponent();
        }
        void navBarControl_ActiveGroupChanged(object sender, DevExpress.XtraNavBar.NavBarGroupEventArgs e)
        {
            
        }
        void barButtonNavigation_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
           
        }

        public frmSuasp(string mamon1)
        {
            // TODO: Complete member initialization
            this.mamon1 = mamon1;
            InitializeComponent();
        }


        private void btn_sua_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            int mamon = int.Parse(mamon1);
            DialogResult r;
            r = MessageBox.Show("Bạn có muốn lưu?", "Lưu thông tin", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (r == DialogResult.Yes)
            {
                MySqlConnection conn = getDB.getData();
                conn.Open();
                int maloai = int.Parse(cbMaLoai.Text.Trim());
                string tenloai = cbTenLoai.Text.Trim();
                string tenmon = txtTenSp.Text.Trim();
                int gia = int.Parse(txtGia.Text.Trim());
                string sql = "update thucdon set TenMon ='" + tenmon + "', Gia ='" + gia + "', MaLoai = '" + maloai + "' where MaMon=" + mamon;
                try
                {
                    MySqlCommand cmd = new MySqlCommand(sql, conn);
                    if (cmd.ExecuteNonQuery() == 1)
                    {
                        MessageBox.Show("Thành công");
                    }
                    else
                    {
                        MessageBox.Show("Fails");
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

        }

        private void frmSuasp_Load(object sender, EventArgs e)
        {
            datacbTenLoai();
            textBoxchange();
            loadMaLoai();
            loadTenLoai();
        }

        private void loadMaLoai()
        {
            int mamon = int.Parse(mamon1);
            MySqlConnection conn = getDB.getData();
            conn.Open();

            string sql = "select * from thucdon where MaMon=" + mamon;
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
                            int idindex = reader.GetOrdinal("MaLoai");
                            string ML = reader.GetString(idindex);
                            cbMaLoai.Text = ML;
                        }
                    }
                }


            }
            catch
            {
                MessageBox.Show("error");
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }
        }

        private void loadTenLoai()
        {
            string ml = cbMaLoai.Text;
            MySqlConnection conn = getDB.getData();
            conn.Open();
            string sql = "select TenLoai from loai where MaLoai =" + ml;
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
                            int idindex = reader.GetOrdinal("TenLoai");
                            string TL = reader.GetString(idindex);
                            cbTenLoai.Text = TL;

                        }
                    }
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

        public void datacbMaLoai()
        {
            string tl = (cbTenLoai.SelectedIndex + 1).ToString();
            MySqlConnection conn = getDB.getData();
            conn.Open();
            List<string> maloai = new List<string>();
            try
            {
                string sql = "select MaLoai from loai where MaLoai=" + tl;
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = sql;

                using (DbDataReader reader = cmd.ExecuteReader())
                {
                    if (reader.HasRows)
                    {
                        while (reader.Read())
                        {
                            int idindex = reader.GetOrdinal("MaLoai");
                            string ML = reader.GetString(idindex);
                            maloai.Add(ML);
                        }
                    }
                }

                cbMaLoai.DataSource = maloai;
            }
            catch (Exception e)
            {
                MessageBox.Show("error" + e);
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }
            cbMaLoai.Enabled = false;
        }


        public void datacbTenLoai()
        {
            MySqlConnection conn = getDB.getData();
            conn.Open();
            List<string> TenLoai = new List<string>();


            try
            {
                string sql = "select TenLoai from loai";
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = sql;

                using (DbDataReader reader = cmd.ExecuteReader())
                {
                    if (reader.HasRows)
                    {
                        while (reader.Read())
                        {
                            int idindex = reader.GetOrdinal("TenLoai");
                            string TL = reader.GetString(idindex);
                            TenLoai.Add(TL);
                        }
                    }

                }

                cbTenLoai.DataSource = TenLoai;


            }
            catch (Exception e)
            {
                MessageBox.Show("error" + e);
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }

        }

        private void cbTenLoai_SelectedIndexChanged(object sender, EventArgs e)
        {
            datacbMaLoai();
        }

        private void textBoxchange()
        {
            int mamon = int.Parse(mamon1);
            MySqlConnection conn = getDB.getData();
            conn.Open();
            string sql = "select * from thucdon where MaMon =" + mamon;
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
                            int idindex = reader.GetOrdinal("TenMon");
                            string TM = reader.GetString(idindex);
                            txtTenSp.Text = TM;
                            int idindex2 = reader.GetOrdinal("Gia");
                            int Gia = reader.GetInt32(idindex2);
                            txtGia.Text = Gia.ToString();
                        }
                    }

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

        private void btn_return_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            QLBan ban = new QLBan();
            ban.Show();
            this.Hide();
        }

        private void btn_deleteall_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            textBoxchange();
            loadMaLoai();
            loadTenLoai();
        }
    }
}