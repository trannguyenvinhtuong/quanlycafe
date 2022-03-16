<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Handling extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see https://codeigniter.com/user_guide/general/urls.html
	 */
	public function __construct()
	{
		parent::__construct();
		$this->load->helper('url');	
		$this->load->model('M_data');
	}

	public function index()
	{
		$data['sanpham']=$this->M_data->loadData('*,TENPL','sanpham,phanloai','sanpham.MAPL=phanloai.MAPL');

		$danhdau['trangchu']='active';
		$giaodien['header']=$this->load->view('home/header',$danhdau,TRUE);
		$giaodien['body']=$this->load->view('page/trangchu',$data, TRUE);
		$giaodien['footer']=$this->load->view('home/footer',NULL,TRUE);
		$this->load->view('home/master',$giaodien);
	}
}
