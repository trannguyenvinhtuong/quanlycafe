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
    public partial class QLB : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public QLB()
        {
            InitializeComponent();
        }
       
        private void QLB_Load(object sender, EventArgs e)
        {
            loadgrid();
        }

        private void loadgrid()
        {
            MySqlConnection conn = getDB.getData();
            conn.Open();

            MySqlDataAdapter myda = new MySqlDataAdapter();
            string query = "select * from ban";
            myda.SelectCommand = new MySqlCommand(query, conn);
            DataTable da = new DataTable();
            myda.Fill(da);

            BindingSource bs = new BindingSource();
            bs.DataSource = da;

            dgvban.DataSource = bs;

            conn.Close();
            conn.Dispose();
        }

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            loadgrid();
        }
    }
}