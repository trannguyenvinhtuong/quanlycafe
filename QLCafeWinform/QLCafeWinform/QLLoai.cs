﻿using System;
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
    public partial class QLLoai : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public QLLoai()
        {
            InitializeComponent();

            // This line of code is generated by Data Source Configuration Wizard
            // Fill a SqlDataSource asynchronously
            sqlDataSource1.FillAsync();
        }

        private void bbiRefresh_ItemClick(object sender, ItemClickEventArgs e)
        {
            this.Hide();
            QLLoai loai = new QLLoai();
            loai.Show();
        }

        private void bbiNew_ItemClick(object sender, ItemClickEventArgs e)
        {
            frmthemloai them = new frmthemloai();
            this.Hide();
            them.Show();
        }

        private void bbiEdit_ItemClick(object sender, ItemClickEventArgs e)
        {
            string ma = gridViewLoai.GetFocusedDisplayText();

            MySqlConnection conn = getDB.getData();
            conn.Open();
            string sql = "select MaLoai from loai where MaLoai=" + ma;
            string maloai = "";
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
                            int idindex = reader.GetOrdinal("MaLoai");
                            string ML = reader.GetString(idindex);
                            maloai = ML;
                        }
                    }
                }
                if (maloai != "")
                {
                    frmSuaLoai sua = new frmSuaLoai(maloai);
                    sua.Show();
                    this.Hide();
                }
                else
                {
                    MessageBox.Show("Mời chọn vào ô mã món", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }
            }
            catch
            {
                MessageBox.Show("Mời chọn vào ô mã món", "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }
        }

        private void barButtonItem2_ItemClick(object sender, ItemClickEventArgs e)
        {

        }
    }
}