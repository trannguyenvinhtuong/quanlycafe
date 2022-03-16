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

    public partial class frmSuaNV : DevExpress.XtraBars.Ribbon.RibbonForm
    {
       
        private string manv1;
        
        public frmSuaNV()
        {
            InitializeComponent();
        }
        void navBarControl_ActiveGroupChanged(object sender, DevExpress.XtraNavBar.NavBarGroupEventArgs e)
        {
            
        }
        void barButtonNavigation_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
           
        }

        public frmSuaNV(string manv1)
        {
            // TODO: Complete member initialization
            this.manv1 = manv1;
            InitializeComponent();
        }


        private void btn_sua_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            int manv = int.Parse(manv1);
            DialogResult r;
            r = MessageBox.Show("Bạn có muốn lưu?", "Lưu thông tin", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (r == DialogResult.Yes)
            {
                MySqlConnection conn = getDB.getData();
                conn.Open();
                string tennv = txtTenNV.Text.Trim();
                string sdt = txtSDT.Text.Trim();
                string namsinh = txtNS.Text.Trim();
                string email = txtEmail.Text.Trim();
                string diachi = txtDC.Text.Trim();


                string sql = "update nhanvien set TenNV ='" + tennv + "', SDT = '" + sdt + "', NamSinh = '" + namsinh + "', Email = '" + email + "', DiaChi = '" + diachi + "' where MaNV =" + manv;
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
                    QLNhanVien nv = new QLNhanVien();
                    nv.Show();
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
            int manv = int.Parse(manv1);
            MySqlConnection conn = getDB.getData();
            conn.Open();

            string sql = "select * from nhanvien where MaNV =" + manv;
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
                            int idindex = reader.GetOrdinal("MaNV");
                            string ML = reader.GetString(idindex);
                            cbMaNV.Text = ML;
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
            cbMaNV.Enabled = false;
        }
        private void textBoxchange()
        {
            int manv = int.Parse(manv1);
            MySqlConnection conn = getDB.getData();
            conn.Open();
            string sql = "select * from nhanvien where MaNV =" + manv;
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
                            int idindex = reader.GetOrdinal("TenNV");
                            string TM = reader.GetString(idindex);
                            txtTenNV.Text = TM;
                            int idindex2 = reader.GetOrdinal("SDT");
                            string TM2 = reader.GetString(idindex2);
                            txtSDT.Text = TM2;
                            int idindex3 = reader.GetOrdinal("NamSinh");
                            string TM3 = reader.GetString(idindex3);
                            txtNS.Text = TM3;
                            int idindex4 = reader.GetOrdinal("Email");
                            string TM4 = reader.GetString(idindex4);
                            txtEmail.Text = TM4;
                            int idindex5 = reader.GetOrdinal("DiaChi");
                            string TM5 = reader.GetString(idindex5);
                            txtDC.Text = TM5;
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
            QLNhanVien nv = new QLNhanVien();
            nv.Show();
            this.Hide();
        }

        private void btn_deleteall_ItemClick(object sender, DevExpress.XtraBars.ItemClickEventArgs e)
        {
            textBoxchange();
            loadMaLoai();
           
        }
    }
}