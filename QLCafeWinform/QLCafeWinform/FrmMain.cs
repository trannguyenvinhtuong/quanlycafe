using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using DevExpress.XtraBars;
using DevExpress.XtraEditors;

namespace QLCafeWinform
{
    public partial class FrmMain : DevExpress.XtraEditors.XtraForm
    {
        
        public FrmMain()
        {
            InitializeComponent();
        }
        static string chucVu;
        string tendn;


        public static string ChucVu1 { get => chucVu; set => chucVu = value; }

        public FrmMain(string chucvu, string ten)
        {
            InitializeComponent();
            ChucVu1 = chucvu;
            tendn = ten;
            if (ChucVu1 == "NVPV")
            {
                menu_employee.Visible = false;
                //menu_Employees.Visible = false;
            }

        }


        // Nhúng form con vào pannel chính
        public void nhung(Form frm)
        {
            panelContent.Controls.Clear();//Xóa các control trong form cũ
            frm.FormBorderStyle = FormBorderStyle.None;
            frm.TopLevel = false;
            frm.Visible = true;
            frm.Dock = DockStyle.Fill;
            panelContent.Controls.Add(frm);//thêm form mới vào panel
            panelContent.Show();
        }



        public void skin()
        {
            DevExpress.LookAndFeel.DefaultLookAndFeel themes = new DevExpress.LookAndFeel.DefaultLookAndFeel();
            themes.LookAndFeel.SkinName = "Office 2007 Blue";
        }

        private void FrmMain_FormClosing(object sender, FormClosingEventArgs e)
        {
            DialogResult dia = XtraMessageBox.Show("Bạn có muốn thoát không ?", "Thông báo!", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            if (dia == DialogResult.No)
                e.Cancel = true;
        }

        private void FrmMain_Load(object sender, EventArgs e)
        {
            skin();
           
        }

        private void btnThoatNick_MouseClick(object sender, MouseEventArgs e)
        {
            
        }

        private void đặtPhòngToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //FrmDatPhong dp = new FrmDatPhong();
            //nhung(dp);
        }

        private void trảPhòngToolStripMenuItem_Click(object sender, EventArgs e)
        {
           
        }

        private void nhânViênToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //if (quyen == "True")
            //{
            QLNhanVien nv = new QLNhanVien();
            nhung(nv);
            //}
            //else
            //{
            //    XtraMessageBox.Show("Admin mới có thể sử dụng chức năng này !", "Thông báo!", MessageBoxButtons.OK, MessageBoxIcon.Error);
            //}                          
        }

        private void menu_employee_Click(object sender, EventArgs e)
        {



        }

        private void kháchToolStripMenuItem_Click(object sender, EventArgs e)
        {
            QLTK tk = new QLTK();
            nhung(tk);
        }

        private void phòngToolStripMenuItem_Click(object sender, EventArgs e)
        {
            QLChucVu cv = new QLChucVu();
            nhung(cv);
        }

        private void dịchVụToolStripMenuItem_Click(object sender, EventArgs e)
        {
            QLLoai loai = new QLLoai();
            nhung(loai);
        }

        private void hóaĐơnToolStripMenuItem_Click(object sender, EventArgs e)
        {
            QLKH kh = new QLKH();
            nhung(kh);
        }

        private void kSTiệnÍchToolStripMenuItem_Click(object sender, EventArgs e)
        {
            QLCa ca = new QLCa();
            nhung(ca);
        }

        private void loạiPhòngToolStripMenuItem_Click(object sender, EventArgs e)
        {
            
        }

        private void nhàCungCấpToolStripMenuItem_Click(object sender, EventArgs e)
        {
           
        }

        private void tiệnÍchToolStripMenuItem_Click(object sender, EventArgs e)
        {
           
        }

        private void menu_invoice_Click(object sender, EventArgs e)
        {
            QLHD hd = new QLHD();
            nhung(hd);
        }

        private void menu_Rooms_Click(object sender, EventArgs e)
        {
            QLB ban = new QLB();
            nhung(ban);
        }

        private void menu_Hotels_Click(object sender, EventArgs e)
        {
           
        }

        private void menu_Employees_Click(object sender, EventArgs e)
        {
            QLBan td = new QLBan();
            nhung(td);
        }

        private void btnDoiMK_MouseClick(object sender, MouseEventArgs e)
        {

        }

        private void btnDoiMK_Click(object sender, EventArgs e)
        {
            suaMK sua = new suaMK(tendn);
            nhung(sua);
        }

        private void pictureBox1_MouseClick(object sender, MouseEventArgs e)
        {
            FrmBackground bg = new FrmBackground();
            nhung(bg);
        }

        private void btnThoatNick_Click(object sender, EventArgs e)
        {
            
            DialogResult r;
            r = MessageBox.Show("Bạn có muốn đăng xuất?", "Đăng xuất", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
            if (r == DialogResult.Yes)
            {
                DangNhap dn = new DangNhap();
                dn.Show();
                this.Hide();
            }
                
        }
    }
}
