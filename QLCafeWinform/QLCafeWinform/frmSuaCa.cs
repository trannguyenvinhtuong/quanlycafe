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

    public partial class frmSuaCa : DevExpress.XtraBars.Ribbon.RibbonForm
    {
       
        private string maca1;
        
        public frmSuaCa()
        {
            InitializeComponent();
        }
        void navBarControl_ActiveGroupChanged(object sender, DevExpress.XtraNavBar.NavBarGroupEventArgs e)
        {
            
        }
        void barButtonNavigation_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
           
        }

        public frmSuaCa(string maca1)
        {
            // TODO: Complete member initialization
            this.maca1 = maca1;
            InitializeComponent();
        }


        private void btn_sua_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            int maca = int.Parse(maca1);
            DialogResult r;
            r = MessageBox.Show("Bạn có muốn lưu?", "Lưu thông tin", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (r == DialogResult.Yes)
            {
                MySqlConnection conn = getDB.getData();
                conn.Open();
                string tenca = txtTenCa.Text.Trim();
                string sql = "update ca set TenCa ='" + tenca + "' where MaCa =" + maca ;
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
            int maca = int.Parse(maca1);
            MySqlConnection conn = getDB.getData();
            conn.Open();

            string sql = "select * from ca where MaCa =" + maca;
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
                            int idindex = reader.GetOrdinal("MaCa");
                            string ML = reader.GetString(idindex);
                            cbMaCa.Text = ML;
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
            cbMaCa.Enabled = false;
        }
        private void textBoxchange()
        {
            int maca = int.Parse(maca1);
            MySqlConnection conn = getDB.getData();
            conn.Open();
            string sql = "select * from ca where MaCa =" + maca;
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
                            int idindex = reader.GetOrdinal("TenCa");
                            string TM = reader.GetString(idindex);
                            txtTenCa.Text = TM;                            
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
            QLCa ban = new QLCa();
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