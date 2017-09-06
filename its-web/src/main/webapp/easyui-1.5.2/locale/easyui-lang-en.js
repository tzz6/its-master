if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = 'Page';
	$.fn.pagination.defaults.afterPageText = 'of {pages}';
	$.fn.pagination.defaults.displayMsg = 'Displaying {from} to {to} of {total} items';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = 'Processing, please wait ...';
}
if ($.fn.treegrid && $.fn.datagrid){
	$.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
	$.messager.defaults.ok = 'Ok';
	$.messager.defaults.cancel = 'Cancel';
}
$.map(['validatebox','textbox','passwordbox','filebox','searchbox',
		'combo','combobox','combogrid','combotree',
		'datebox','datetimebox','numberbox',
		'spinner','numberspinner','timespinner','datetimespinner'], function(plugin){
	if ($.fn[plugin]){
		$.fn[plugin].defaults.missingMessage = 'This field is required.';
	}
});
if ($.fn.validatebox){
	$.fn.validatebox.defaults.rules.email.message = 'Please enter a valid email address.';
	$.fn.validatebox.defaults.rules.url.message = 'Please enter a valid URL.';
	$.fn.validatebox.defaults.rules.length.message = 'Please enter a value between {0} and {1}.';
	$.fn.validatebox.defaults.rules.remote.message = 'Please fix this field.';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['S','M','T','W','T','F','S'];
	$.fn.calendar.defaults.months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = 'Today';
	$.fn.datebox.defaults.closeText = 'Close';
	$.fn.datebox.defaults.okText = 'Ok';
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText
	});
}


var Msg = {
		//系统提示
		sys_index:'Index',
		sys_remaind:'系统提示',
		sys_remaind1:'Remaind',
		sys_success:'Operation successful！',
		sys_error: 'System error',
		sys_err:'Operation failure！',
		sys_confirm:'确认',
		sys_deleate:'Are you sure you want to delete record？',
		sys_warn:'警告',
		sys_add_success:'added successfully!',
		sys_opreat:'Operation tips',
		sys_errmeage:'Error message',
		sys_select_one:'Please select one line！',
		sys_confirm1:'确认对话框',
		sys_confirm2:'Confirm Remaind',
		sys_logout:'Are you sure you want to log out?', 
		
		agent_check_date: 'Must be a date format such as 2017-01-01',
		agent_check_date_contract:'The contract start date can not be greater than the end date',
		agent_check_time: 'Must be a time format such as 09:30',
		agent_check_declared: 'Declare value can only enter a positive integer',
		agent_check_volume: 'Volume coefficients can only enter positive integers',
		agent_check_weight_limit: 'Weight limit can only enter positive integers',
		agent_check_weight: 'Preference can only enter positive integers',
		agent_exist: 'The proxy service already exists',
		agent_attribute_exist: 'The proxy service already has special attributes',
		agent_weight_exist: 'This preference already exists in the special attribute',
		agent_product_required: 'Applicable products must be selected',
		agent_goods_required: 'Type of goods must be a type',
		agent_payment_required: 'Payment is required',
		agent_attached_required: 'Additional services must be selected',
		agent_customer_required: 'The customer type must be selected',
		agent_declared_compare: 'The amount of declared value can not be greater than the end of the number',
		
		
		//系统管理
		sys_delete_txt1:'删除成功！',
		sys_delete_txt3:'请选择要删除的记录',
		sys_update_txt:'更新成功！',
		sys_update_txt1:'更新失败，请重试！',
		sys_update_txt2:'配置值为空',
		sys_save_txt:'Save success！',
		sys_save_txt2:'存在重复配置编码，保存失败！',
		sys_delete_txt1:'Delete success！',
		sys_delete_txt2:'Delete failed！',
		sys_no_permissions_txt1:'No Permissions！',
		sys_delete_txt3:'请选择要删除的记录',
		sys_update_txt:'Update success！',
		sys_update_txt1:'Update failed！',
		sys_save_txt3:'Save failed！',
		frequency_10:'Please select at least one row！',
		frequency_11:'Please select one row！',
		//用户管理
		sys_userMgr_04:'Existing in the system the same user login ID (Number)',
		sys_user_code:'Employee Number',
		sys_user_name:'Employee Name',
		sys_user_role:'Role',
		sys_user_create_tm:'Create Time',
		sys_save_success: 'Save Success',
		sys_delete_success: 'Delete Success',
		sys_only_select_one: 'Can only choose a data',
		set_associated_roles: 'Set Associated Roles',
		btn_role_set_menu:'Set Permissions Associated',
		sys_pls_select: 'Please select',

		//重量段模板
		weightTemplate_eq_weight:'Start weight must be less than end weight',
		weightTemplate_status_invalid: 'Disable',
		weightTemplate_status_draft: 'Draft',
		weightTemplate_status_valid: 'Enable',
		weightTemplate_not_exist:'Weight template does not exist.',
		weightTemplate_del_not_draft: 'Only drafts can be deleted.',
		weightTemplate_syserror: 'System error',
		weightTemplate_not_null_id_status: 'ID and status cannot be empty.',
		weightTemplate_updateinvalid: 'Cannot operat invalid data.',
		weightTemplate_status_error: 'Status error',
		weightTemplate_exist: 'Agent service and goods type cannot repeat.',
		weightTemplate_update_draft: 'Only drafts can be update.',
		weightTemplate_id_not_null:'ID does not exist.',
		weightTemplate_start_end_not_null:'Start weight and end weight cannot be empty.',
		weightTemplate_tmplid_not_null: 'Weight template ID does not exist.',
		weightTemplate_detail_exist: 'Weight Period already exists.',
		weightTemplate_valid2invalid:'The status of the draft can only be turned into effect',
		weightTemplate_draft2valid:'The status of the effective can only be turned into invalid',
		weightTemplate_eq_greater_zero: 'Must be greater than 0',
		weightTemplate_eq_date_out_six_month: 'The time interval can not exceed half a year',
		weightTemplate_detail_not_empty: 'Weight Period cannot be empty',
		weightTemplate_must_total: 'Before the unit price must be the total price',
		
		sys_name_code:'System Name',
		//角色管理
		sys_role_name:'Role Name',
		//菜单
		sys_menu_name:'Menu Name',
		sys_menu_type:'Type',
		sys_menu_type_menu:'Menu',
		sys_menu_parent_menu:'Superior Menu',
		sys_menu_type_button:'Button',
		
		//代理服务
		mgr_agent_service_01:'Not set special properties',
		mgr_agent_service_02:'Not set service coverage',
		
		//服务范围
		mgr_channel_29: 'began to zip code can not be empty',
		mgr_channel_30: 'end zip code can not be empty',
		mgr_channel_31: 'scope of zip code can only use English characters and Numbers',
		mgr_channel_32: 'began to zip code is not greater than the end of the zip code',
		mgr_channel_33:'Zip code for the start of the zip code is consistent with the zip code to end character',
		mgr_channel_34:'Start zip code length and the end of the zip code',
		mgr_check_30: 'zip code segment repetitive with existing countries',
		mgr_check_02:'The original send code errors',
		mgr_check_12:'The destination code error',
		//服务范围导入
		title_upload_lineNo: 'line',
		title_upload_failReason: 'the reason for failure',
		upload_success: 'success',
		msg_fail: 'failure',
		msg_uploading: 'in the import, please later...',
		records: 'records',
		msg_upload_selectFileFirst: 'please choose need to upload the Excel file',
		msg_upload_fileFormatWrong: 'please use the correct template!',
		
	    bc_code:"Country Code",
	    country_name:"Country Name",
	    post_start:"Starting Postal Code",
	    post_end:"Ending Postal Code",
		
		
		//服务范围
		mgr_channel_29: 'began to zip code can not be empty',
		mgr_channel_30: 'end zip code can not be empty',
		mgr_channel_31: 'scope of zip code can only use English characters and Numbers',
		mgr_channel_32: 'began to zip code is not greater than the end of the zip code',
		mgr_check_30:'With existing country zip code repetition',
		
		//账单模板
		accountTemplate_setDefaultValue: "Set the default value",
		accountTemplate_cannot_setDefaultValue: "Can not set default values",
		accountTemplate_not_null_defaultValue: "Default value cannot be empty.",
		accountTemplate_not_null_defaultValue_mappingField: "'Field of Mapping' and 'Default value' Can not be empty at the same time",
		accountTemplate_not_null_file: "The file can not be empty",
		accountTemplate_file_max_out: "The file size can not exceed 2MB",
		accountTemplate_invalid_file_type: "Currently only support xlsx, xls, csv type import",
		accountTemplate_id_not_null: "ID cannot be empty",
		accountTemplate_not_exist: "Billing Template does not exist.",
		accountTemplate_exist: "Billing Template already exists",
		accountTemplate_invalidValue: "Invalid Value",
		accountTemplate_repeatValue: " cannot be repeated",
			
		//分区方案
		partition_cannot_empty: "Flow cannot be empty",
		partition_country_empty: "Countries under the flow can not be empty",
		partition_post_empty: "The zip code can not be empty",
		partition_idOrstatus_empty: "ID and status cannot be empty",
		partition_not_exist: "Partition scheme does not exist",
		partition_operate_data: "Fail to operate data",
		partition_abnormal_state: "Abnormal state",
		partition_name_repeated: "Partition scheme name cannot be repeated",
		partition_success:"Success"	,
		partition_schemeName_repeated:"The name of the flow in the partition scheme cannot be repeated"		
	}
