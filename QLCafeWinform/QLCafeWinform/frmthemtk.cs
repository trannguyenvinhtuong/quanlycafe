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
    public partial class frmthemtk : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public frmthemtk()
        {
            InitializeComponent();
        }      
        


        private void frmthemtk_Load(object sender, EventArgs e)
        {
            loadcb();
        }

        private void loadcb()
        {
            MySqlConnection conn = getDB.getData();
            conn.Open();

            MySqlDataAdapter myda = new MySqlDataAdapter();
            string query = "select * from nhanvien";
            myda.SelectCommand = new MySqlCommand(query, conn);
            DataTable da = new DataTable();
            myda.Fill(da);

            BindingSource bs = new BindingSource();
            bs.DataSource = da;

            cbtennv.DataSource = bs;
            cbtennv.DisplayMember = "TenNV";
            cbtennv.ValueMember = "MaNV";

            conn.Close();
            conn.Dispose();
        }

        private void bbiDelete_ItemClick(object sender, ItemClickEventArgs e)
        {
            txtpass.Clear();
            txttk.Clear();
            txtxn.Clear();
            loadcb();
        }

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLTK tk = new QLTK();
            this.Hide();
            tk.Show();
        }

        private void bbiNew_ItemClick(object sender, ItemClickEventArgs e)
        {
            string tendn = txttk.Text.Trim();
            string mk = txtpass.Text.Trim();
            string xnmk = txtxn.Text.Trim();
            int manv = int.Parse(cbtennv.SelectedValue.ToString());
            string macv = "NVPV";
            int makh = 0;
            string img = "user.png";

            DialogResult r;
            r = MessageBox.Show("Bạn có muốn lưu?", "Lưu thông tin", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (r == DialogResult.Yes)
            {
                if (tendn == "")
                {
                    MessageBox.Show("Không được để trống tên đăng nhập");
                }
                else if(mk == "")
                {
                    MessageBox.Show("Không được để trống mật khẩu");
                }
                else if (xnmk == "")
                {
                    MessageBox.Show("Không được để trống xác nhận mật khẩu");
                }
                else if (xnmk != mk)
                {
                    MessageBox.Show("Xác nhận mật khẩu không trùng khớp");
                }
                else
                {
                    MySqlConnection conn = getDB.getData();
                    conn.Open();
                    try
                    {

                        string sql = "insert into taikhoan(TK,MK,MaCV,MaNV,MaKH,IMG) values ('" + tendn + "','" + mk + "','" + macv + "','" + manv + "','" + makh + "','" + img + "')";
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
    }
}