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
    public partial class QLCTHD : DevExpress.XtraBars.Ribbon.RibbonForm
    {

        string maHD;
        public QLCTHD()
        {
            InitializeComponent();           
        }

        public QLCTHD(string ma)
        {
            maHD = ma;
            InitializeComponent();
        }

        private void QLCTHD_Load(object sender, EventArgs e)
        {
            MySqlConnection conn = getDB.getData();
            conn.Open();

            MySqlDataAdapter myda = new MySqlDataAdapter();
            string query = "select * from chitiethd where MaHD = '"+maHD+"' ";
            myda.SelectCommand = new MySqlCommand(query, conn);
            DataTable da = new DataTable();
            myda.Fill(da);

            BindingSource bs = new BindingSource();
            bs.DataSource = da;

            dgvcthd.DataSource = bs;

            conn.Close();
            conn.Dispose();
        }

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLHD hd = new QLHD();
            this.Hide();
            hd.Show();
        }
    }
}