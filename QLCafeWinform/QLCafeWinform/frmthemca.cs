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
    public partial class frmthemca : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public frmthemca()
        {
            InitializeComponent();            
            
        }
        

        private void bbiDelete_ItemClick(object sender, ItemClickEventArgs e)
        {
            txtTenCa.Clear();
           
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
                    string tenca = txtTenCa.Text.Trim();                   

                    string sql = "insert into ca(TenCa) values ('" + tenca + "')";
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
                    QLCa themca = new QLCa();
                    themca.Show();
                    this.Hide();
                }     
            }         

            
        }
       

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLCa themca = new QLCa();
            themca.Show();
            this.Hide();
        }

        private void bbiRefresh_ItemClick(object sender, ItemClickEventArgs e)
        {
            txtTenCa.Clear();
        }        
    }
}