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

    public partial class frmSuaLoai : DevExpress.XtraBars.Ribbon.RibbonForm
    {
       
        private string maloai1;
        
        public frmSuaLoai()
        {
            InitializeComponent();
        }
        void navBarControl_ActiveGroupChanged(object sender, DevExpress.XtraNavBar.NavBarGroupEventArgs e)
        {
            
        }
        void barButtonNavigation_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
           
        }

        public frmSuaLoai(string maloai1)
        {
            // TODO: Complete member initialization
            this.maloai1 = maloai1;
            InitializeComponent();
        }


        private void btn_sua_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            int maloai = int.Parse(maloai1);
            DialogResult r;
            r = MessageBox.Show("Bạn có muốn lưu?", "Lưu thông tin", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (r == DialogResult.Yes)
            {
                MySqlConnection conn = getDB.getData();
                conn.Open();
                string tenloai = txtTenLoai.Text.Trim();
                string sql = "update loai set TenLoai ='" + tenloai + "' where MaLoai =" + maloai ;
                try
                {
                    MySqlCommand cmd = new MySqlCommand(sql, conn);
                    if (cmd.ExecuteNonQuery() == 1)
                    {
                        MessageBox.Show("Thành công", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                    else
                    {
                        MessageBox.Show("Fails", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
                catch
                {
                    MessageBox.Show("Lỗi", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                finally
                {
                    conn.Close();
                    conn.Dispose();
                    QLLoai ban = new QLLoai();
                    ban.Show();
                    this.Hide();
                }
                
            }

        }

        private void frmSuasp_Load(object sender, EventArgs e)
        {
           
            textBoxchange();
            loadMaLoai();
            
        }

        private void loadMaLoai()
        {
            int maloai = int.Parse(maloai1);
            MySqlConnection conn = getDB.getData();
            conn.Open();

            string sql = "select * from loai where MaLoai =" + maloai;
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
                MessageBox.Show("error", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }
            cbMaLoai.Enabled = false;
        }
        private void textBoxchange()
        {
            int maloai = int.Parse(maloai1);
            MySqlConnection conn = getDB.getData();
            conn.Open();
            string sql = "select * from loai where MaLoai =" + maloai;
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
                            string TM = reader.GetString(idindex);
                            txtTenLoai.Text = TM;                            
                        }
                    }

                }

            }
            catch
            {
                MessageBox.Show("Lỗi", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }
            

        }

        private void btn_return_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            QLLoai ban = new QLLoai();
            ban.Show();
            this.Hide();
        }

        private void btn_deleteall_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            textBoxchange();
            loadMaLoai();
           
        }
    }
}