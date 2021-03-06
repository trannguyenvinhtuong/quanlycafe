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
    public partial class QLNhanVien : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public QLNhanVien()
        {
            InitializeComponent();
            // This line of code is generated by Data Source Configuration Wizard
            // Fill a SqlDataSource asynchronously
            sqlDataSource1.FillAsync();
        }

        private void bbiNew_ItemClick(object sender, ItemClickEventArgs e)
        {
            frmthemnv nv = new frmthemnv();
            this.Hide();
            nv.Show();
        }

        private void bbiRefresh_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLNhanVien nv = new QLNhanVien();
            this.Hide();
            nv.Show();
        }

        private void bbiEdit_ItemClick(object sender, ItemClickEventArgs e)
        {
            string ma = gridViewNV.GetFocusedDisplayText();

            MySqlConnection conn = getDB.getData();
            conn.Open();
            string sql = "select MaNV from nhanvien where MaNV =" + ma;
            string manv = "";
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
                            manv = ML;
                        }
                    }
                }
                if (manv != "")
                {
                    frmSuaNV sua = new frmSuaNV(manv);
                    sua.Show();
                    this.Hide();
                }
                else
                {
                    MessageBox.Show("Mời chọn vào ô mã nhân viên", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }
            }
            catch
            {
                MessageBox.Show("Mời chọn vào ô mã món nhân viên", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }
        }

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            
        }
        
    }
}