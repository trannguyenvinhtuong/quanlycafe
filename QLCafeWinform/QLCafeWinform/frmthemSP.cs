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
    public partial class frmthemSP : DevExpress.XtraBars.Ribbon.RibbonForm
    {
        public frmthemSP()
        {
            InitializeComponent();            
            datacbTenLoai();
        }

        public void datacbMaLoai()
        {
            string tl = (cbTenLoai.SelectedIndex + 1).ToString();
            MySqlConnection conn = getDB.getData();
            conn.Open();
            List<string> maloai=new List<string>();
            try
            {
                string sql = "select MaLoai from loai where MaLoai="+tl;
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
                            maloai.Add(ML);
                        }
                    }
                }

                cbMaLoai.DataSource = maloai;
            }
            catch(Exception e){
                MessageBox.Show("error"+e);
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }
            cbMaLoai.Enabled = false;
        }

        public void datacbTenLoai()
        {
            MySqlConnection conn = getDB.getData();
            conn.Open();
            List<string> TenLoai = new List<string>();
            

            try
            {
                string sql = "select TenLoai from loai";
                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = conn;
                cmd.CommandText = sql;

                using (DbDataReader reader = cmd.ExecuteReader())
                {
                    if (reader.HasRows)
                    {
                        while (reader.Read())
                        {
                            int idindex = reader.GetOrdinal("TenLoai");
                            string TL = reader.GetString(idindex);
                            TenLoai.Add(TL);
                        }
                    }
                  
                }

                cbTenLoai.DataSource = TenLoai;
                
                
            }
            catch (Exception e)
            {
                MessageBox.Show("error" + e);
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }

        }

        private void bbiDelete_ItemClick(object sender, ItemClickEventArgs e)
        {
            txtTenSp.Clear();
            txtGia.Clear();
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
                    int Maloai = int.Parse(cbMaLoai.Text.Trim());
                    string tenSP = txtTenSp.Text.Trim();
                    int gIa = int.Parse(txtGia.Text.Trim());

                    string sql = "insert into thucdon(TenMon,Gia,MaLoai) values ('" + tenSP + "','" + gIa + "','" + Maloai + "')";
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
                }     
            }         

            
        }

        private void cbTenLoai_SelectedIndexChanged(object sender, EventArgs e)
        {
            datacbMaLoai();
        }

        private void barButtonItem1_ItemClick(object sender, ItemClickEventArgs e)
        {
            QLBan themsp = new QLBan();
            themsp.Show();
            this.Hide();
        }        
    }
}