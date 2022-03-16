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
using DevExpress.XtraBars;
using System.ComponentModel.DataAnnotations;
using MySql.Data.MySqlClient;
using System.Data.Common;

namespace QLCafeWinform
{
    public partial class frmthemnv : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public frmthemnv()
        {
            InitializeComponent();            
            
        }
        

        private void bbiDelete_ItemClick(object sender, ItemClickEventArgs e)
        {
            txtTenNV.Clear();
            txtSDT.Clear();
            txtNS.Clear();
            txtEmail.Clear();
            txtDC.Clear();
           
        }

        private void bbiNew_ItemClick(object sender, ItemClickEventArgs e)
        {
            DialogResult r;
            r=MessageBox.Show("Bạn có muốn lưu?","Lưu thông tin",MessageBoxButtons.YesNo,MessageBoxIcon.Warning);
            if (r==DialogResult.Yes)
            {
                MySqlConnection conn = getDB.getData();
                conn.Open();
                try
                {
                    string tennv = txtTenNV.Text.Trim();
                    string sdt = txtSDT.Text.Trim();
                    string namsinh = txtNS.Text.Trim();
                    string email = txtEmail.Text.Trim();
                    string diachi = txtDC.Text.Trim();

                    string sql = "insert into nhanvien(TenNV,SDT,NamSinh,Email,DiaChi) values ('" + tennv + "','" + sdt + "','" + namsinh + "','" + email + "','" + diachi + "')";
                    MySqlCommand cmd = new MySqlCommand(sql, conn);
                    if (cmd.ExecuteNonQuery() == 1)
                    {
                        MessageBox.Show("Thành công","Thông báo",MessageBoxButtons.OK,MessageBoxIcon.Information);
                    }
                    else
                    {
                        MessageBox.Show("Fails","Lỗi",MessageBoxButtons.OK,MessageBoxIcon.Error);
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
                    QLNhanVien them = new QLNhanVien();
                    them.Show();
                    this.Hide();
                }     
            }         

            
        }
       

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLNhanVien them = new QLNhanVien();
            them.Show();
            this.Hide();
        }

        private void bbiRefresh_ItemClick(object sender, ItemClickEventArgs e)
        {
            txtTenNV.Clear();
            txtSDT.Clear();
            txtNS.Clear();
            txtEmail.Clear();
            txtDC.Clear();
        }        
    }
}