
<div class="row">
	<?php foreach ($sanpham as $key) {?>
		<div class="col-3">
			<br/>
			<br/>			
			<img class="img-sp" src="<?php echo base_url()?>public/img/<?php echo $key['IMG']?>">
			<br/>
			<br/>
			<h1 class="tensp"><?php echo $key['TENSP']?></h1>
			<div>
				<h1 class="giasp"><?php echo $key['GIA']?> VND</h1>
				<h1 class="phanloai"><?php echo $key['TENPL']?></h1>
			</div>	
			<br/>
			<button class="btn-mua">Mua ngay</button>				
		</div>

	<?php }?>
	
</div>