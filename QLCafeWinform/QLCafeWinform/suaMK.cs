using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Data;
using System.Drawing;
using System.Text;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using DevExpress.XtraEditors;
using DevExpress.XtraBars;
using MySql.Data.MySqlClient;

namespace QLCafeWinform
{
    public partial class suaMK : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        string tendn = "";
        public suaMK()
        {
            InitializeComponent();            
        }

        public suaMK(string ten)
        {
            tendn = ten;
            InitializeComponent();
        }

        private void suaMK_Load(object sender, EventArgs e)
        {
            txtuser.Text = tendn;
            txtuser.Enabled = false;
        }

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            DialogResult r;
            r = MessageBox.Show("Bạn có muốn lưu?", "Lưu thông tin", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (r == DialogResult.Yes)
            {
                string pass = txtpass.Text.Trim();
                string repass = txtrepass.Text.Trim();
                if(pass == "")
                {
                    MessageBox.Show("Không được để trống mật khẩu");
                }
                else if(repass == "")
                {
                    MessageBox.Show("Không được để trống xác nhận mật khẩu");
                }
                else if(pass != repass)
                {
                    MessageBox.Show("Xác nhận mật khẩu không trùng khớp");
                }
                else
                {
                    MySqlConnection conn = getDB.getData();
                    conn.Open();
                    try
                    {

                        string sql = "update taikhoan set MK = '" + pass + "' where TK = '" + tendn + "'";
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
                        QLTK themca = new QLTK();
                        themca.Show();
                        this.Hide();
                    }
                }
            }
        }

        private void barButtonItem2_ItemClick(object sender, ItemClickEventArgs e)
        {
            txtpass.Clear();
            txtrepass.Clear();
        }

        private void barButtonItem3_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLTK themca = new QLTK();
            themca.Show();
            this.Hide();
        }
    }
}