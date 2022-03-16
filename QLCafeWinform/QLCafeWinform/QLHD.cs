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
using System.Data.Common;

namespace QLCafeWinform
{
    public partial class QLHD : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public QLHD()
        {
            InitializeComponent();

           
        }
        void bbiPrintPreview_ItemClick(object sender, ItemClickEventArgs e)
        {
           
        }

        private void bbiRefresh_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLHD hd = new QLHD();
            hd.Show();
            this.Hide();
        }

        string ma = "";

        private void bbiNew_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLCTHD ct = new QLCTHD(ma);
            ct.Show();
            this.Hide();
        }

        private void QLHD_Load(object sender, EventArgs e)
        {
            loadgrid();
        }

        private void loadgrid()
        {
            MySqlConnection conn = getDB.getData();
            conn.Open();

            MySqlDataAdapter myda = new MySqlDataAdapter();
            string query = "select * from hoadon";
            myda.SelectCommand = new MySqlCommand(query, conn);
            DataTable da = new DataTable();
            myda.Fill(da);

            BindingSource bs = new BindingSource();
            bs.DataSource = da;

            dgvhd.DataSource = bs;

            conn.Close();
            conn.Dispose();
        }

        private void dgvhd_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            int nr;
            nr = e.RowIndex;
            ma = dgvhd.Rows[nr].Cells[0].Value.ToString();
        }

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            DialogResult r;
            r = MessageBox.Show("Bạn có muốn xác nhận thanh toán cho hóa đơn này?", "Xác nhận thanh toán", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (r == DialogResult.Yes)
            {
                MySqlConnection conn = getDB.getData();
                conn.Open();
                string trangthai = "1";
                string sql = "update hoadon set TrangThai ='" + trangthai + "' where MaHD =" + ma;
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
                    loadgrid();
                }
            }
        }
    }
}