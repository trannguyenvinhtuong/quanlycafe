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
    public partial class QLTK : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public QLTK()
        {
            InitializeComponent();
        }     

        private void bbiRefresh_ItemClick(object sender, ItemClickEventArgs e)
        {
            loadgridview();
        }

        private void QLTK_Load(object sender, EventArgs e)
        {
            loadgridview();
        }

        private void loadgridview()
        {
            MySqlConnection conn = getDB.getData();
            conn.Open();

            MySqlDataAdapter myda = new MySqlDataAdapter();
            string query = "select * from taikhoan";
            myda.SelectCommand = new MySqlCommand(query, conn);
            DataTable da = new DataTable();
            myda.Fill(da);

            BindingSource bs = new BindingSource();
            bs.DataSource = da;

            dgvtk.DataSource = bs;

            conn.Close();
            conn.Dispose();
        }

        private void bbiNew_ItemClick(object sender, ItemClickEventArgs e)
        {
            frmthemtk them = new frmthemtk();
            them.Show();
            this.Hide();
        }

        string ten = "";

        private void bbiEdit_ItemClick(object sender, ItemClickEventArgs e)
        {
            suaMK sua = new suaMK(ten);
            sua.Show();
            this.Hide();
        }

        private void dgvtk_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            int nr;
            nr = e.RowIndex;
            ten = dgvtk.Rows[nr].Cells[0].Value.ToString();
        }
    }
}