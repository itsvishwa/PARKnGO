<?php require APPROOT . '/views/inc/header.php'; ?>
<h1><?php echo $data['title'] ?></h1>
<ul>
  <?php foreach ($data['admin'] as $admin) : ?>
    <li><?php echo $admin->email; ?></li>
  <?php endforeach; ?>
</ul>
<?php require APPROOT . '/views/inc/footer.php'; ?>