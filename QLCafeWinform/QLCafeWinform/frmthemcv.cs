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
    public partial class frmthemcv : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public frmthemcv()
        {
            InitializeComponent();            
            
        }
        

        private void bbiDelete_ItemClick(object sender, ItemClickEventArgs e)
        {
            txtcv.Clear();
           
        }

        private void bbiNew_ItemClick(object sender, ItemClickEventArgs e)
        {
            DialogResult r;
            r=MessageBox.Show("Bạn có muốn lưu?","Lưu thông tin",MessageBoxButtons.YesNo,MessageBoxIcon.Warning);
            if (r==DialogResult.Yes)
            {
                MySqlConnection conn = getDB.getData();
                conn.Open();
                string tencv = txtcv.Text.Trim();
                string macv = txtmacv.Text.Trim();
                if (tencv != "" && macv != "")
                {
                    try
                    {

                        string sql = "insert into chucvu(MaCV,TenCV) values ('" + tencv + "','" + macv + "')";
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
                        QLChucVu cv = new QLChucVu();
                        cv.Show();
                        this.Hide();
                    }     
                }
                else
                {
                    MessageBox.Show("Tên hoặc mã chức vụ không được null", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                
            }         

            
        }
       

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLChucVu cv = new QLChucVu();
            cv.Show();
            this.Hide();
        }

        private void bbiRefresh_ItemClick(object sender, ItemClickEventArgs e)
        {
            txtcv.Clear();
        }

        private void label4_Click(object sender, EventArgs e)
        {

        }        
    }
}