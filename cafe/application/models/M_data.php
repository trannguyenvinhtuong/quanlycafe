<?php
	defined('BASEPATH') OR exit('No direct script access allowed');
	/**
	 * 
	 */
	class M_data extends CI_Model
	{
		
		function __construct()
		{
			parent::__construct();
			$this->load->database();
		}

		public function loadData($select,$table,$where){
			$result=$this->db->select($select)->from($table)->where($where)->get();
			return $result->result_array();
		}
	}
?>