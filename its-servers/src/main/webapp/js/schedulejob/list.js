//创建
function create(id){
	var ctx = $("#ctx").val();
	var url = ctx + "/scheduleJob/"+id+"/create";
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : url,
		success : function(data) {
			alert("创建成功");
			location.reload();
		},
		error : function(data) {
			alert("error");
		}
	});
}
//执行
function runJob(id){
	var ctx = $("#ctx").val();
	var url = ctx + "/scheduleJob/"+id+"/runJob";
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : url,
		success : function(data) {
			alert("执行成功");
		},
		error : function(data) {
			alert("error");
		}
	});
}
//暂停
function stop(id){
	var ctx = $("#ctx").val();
	var url = ctx + "/scheduleJob/"+id+"/stop";
	$.ajax({
//		data : {'totalMoney':totalMoney,'subType':subType},
		type : "POST",
		dataType : 'json',
		url : url,
		success : function(data) {
			alert("暂停成功");
			location.reload();
		},
		error : function(data) {
			alert("error");
		}
	});
}
//恢复
function recover(id){
	var ctx = $("#ctx").val();
	var url = ctx + "/scheduleJob/"+id+"/recover";
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : url,
		success : function(data) {
			alert("恢复成功");
			location.reload();
		},
		error : function(data) {
			alert("error");
		}
	});
}
//删除
function deleteJob(id){
	var ctx = $("#ctx").val();
	var url = ctx + "/scheduleJob/"+id+"/remove";
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : url,
		success : function(data) {
			alert("删除成功");
			location.reload();
		},
		error : function(data) {
			alert("error");
		}
	});
}

//全部暂停
function stopAll(){
	var ctx = $("#ctx").val();
	var url = ctx + "/scheduleJob/stopAll";
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : url,
		success : function(data) {
			alert("全部暂停成功");
			location.reload();
		},
		error : function(data) {
			alert("error");
		}
	});
}
//全部恢复
function recoverAll(){
	var ctx = $("#ctx").val();
	var url = ctx + "/scheduleJob/recoverAll";
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : url,
		success : function(data) {
			alert("全部恢复成功");
			location.reload();
		},
		error : function(data) {
			alert("error");
		}
	});
}