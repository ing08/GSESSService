<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Refresh" content="120">
<style type="text/css">
<!--
.table3 {
border-collapse: collapse;
border: 1px solid #ccc;
line-height: 1.5;
}
.table3 th {
padding: 10px;
font-weight: bold;
vertical-align: top;
background: #0099e3;
color: #ffffff;
}
.table3 td {
padding: 10px;
vertical-align: top;
}
.table3 tr:nth-child(even) {
background: #cee7ff;
}
-->
</style>
</head>
<body>
<h1>現在院試勉強中の野郎ども</h1>

<p>2分毎に更新されます</p>
<p>
<?php
echo "最終更新日時:",  date("Y/m/d H:i:s")
?>
</p>
<table class="table3">

<tr>
<th>ニックネーム</th><th>勉強中の科目</th><th>直近の勉強科目</th><th>時刻</th><th>時間</th>
</tr>
<?php

$db = mysqli_connect("192.168.0.21","ingHost","aoijapan","GSESSService");
$result = mysqli_query($db, "select nickname, studying, studied, newest, second from current_state left join last_studied using(uid)");

while ($row = mysqli_fetch_array($result)) {
    echo "<tr>";
    $hour = intval($row[4] / 3600);
    $min = intval(($row[4] % 3600) / 60);
    $sec = intval($row[4] % 60);
    echo "<td>", $row[0], "</td> <td>", ($row[1])?$row[1]:"何もしていない", "</td> <td>", ($row[2])?$row[2]:"---", "</td> <td>", ($row[3])?$row[3]:"---", "</td> <td>", ($row[4])?"{$hour}時間{$min}分{$sec}秒":"---", "</td>";
    echo "</tr>\n";
}
?>
</table>
</body>
</html>
