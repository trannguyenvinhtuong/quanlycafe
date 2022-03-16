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

    public partial class frmSuaCV : DevExpress.XtraBars.Ribbon.RibbonForm
    {
       
        private string macv1;
        
        public frmSuaCV()
        {
            InitializeComponent();
        }
        void navBarControl_ActiveGroupChanged(object sender, DevExpress.XtraNavBar.NavBarGroupEventArgs e)
        {
            
        }
        void barButtonNavigation_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
           
        }

        public frmSuaCV(string macv1)
        {
            // TODO: Complete member initialization
            this.macv1 = macv1;
            InitializeComponent();
        }


        private void btn_sua_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            string macv = macv1;
            DialogResult r;
            r = MessageBox.Show("Bạn có muốn lưu?", "Lưu thông tin", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (r == DialogResult.Yes)
            {
                MySqlConnection conn = getDB.getData();
                conn.Open();
                string tencv = txtTencv.Text.Trim();
                string sql = "update chucvu set TenCV ='" + tencv + "' where MaChucVu = '" + macv + "'";
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
                    QLChucVu ban = new QLChucVu();
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
            string macv = macv1;
            MySqlConnection conn = getDB.getData();
            conn.Open();

            string sql = "select * from chucvu where MaCV = '" + macv + "'";
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
                            cbMaCV.Text = ML;
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
            cbMaCV.Enabled = false;
        }
        private void textBoxchange()
        {
            string macv = macv1;
            MySqlConnection conn = getDB.getData();
            conn.Open();
            string sql = "select * from chucvu where MaCV = '" + macv + "'";
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
                            int idindex = reader.GetOrdinal("TenCV");
                            string TM = reader.GetString(idindex);
                            txtTencv.Text = TM;                            
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
            QLChucVu ban = new QLChucVu();
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