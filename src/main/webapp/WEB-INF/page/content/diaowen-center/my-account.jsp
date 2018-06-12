<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
	<script src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js" type="text/javascript" ></script>

	<link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/js/plugs/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctx}/js/plugs/validate/messages_cn.js" type="text/javascript"></script>
	<script src="${ctx }/js/plugs/validate/jquery.metadata.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/common.js" type="text/javascript"></script>
<title>My account</title>
	<style type="text/css>">
		.pwdTd{
			display: none;
		}
	</style>
<script type="text/javascript">
	
$(document).ready(function(){
	var inputFormValidate=$("#input_form").validate({
		rules: {
			email: {required: true, email: true, maxlength: 60},
		},
		messages: {
			email: {
				email: "Incorrect format！",
				remote: "Email already exists"
			}
		},
		errorPlacement: function(error, element) {
			//error.appendTo(element.parent().parent());
			element.parent().append(error);
			//	$(element).css("borderColor","#C40000");
		}
	});

});

	
</script>
</head>
<body>
	<input type="hidden" id="id" name="id" value="${survey.id }">
	
	<div style="margin-top: 15px;">
		<div class="main-tabs-content bodyCenter">
			<div class="tab-content">
				<div class="tab-content-collectTab" style="text-align: left;">
					<a href="" class="nav_a">My account</a>
				</div>
			</div>
		</div>
	</div>
	<div style="clear: both;"></div>
	<div id="dwBody" >
		<div id="dwBodyContent" class="bodyCenter" style="">
		<div class="tab-content" style="background: #D9D9D9;padding: 0px 5px;">
				<div class="tab-content-collectTab icTab" style="text-align: left;">
				
					<!--  myaccount.action  --><!--  myaccount.action  --><!--  myaccount.action  --><!--  myaccount.action  -->
					<a href="${ctx }/ic/user!myaccount.action" class="collectTab tabItem_1 active"><span class="collectTabItemLeft">&nbsp;</span><span>My account</span></a>
				</div>
		</div>
		<div id="dwBodyUser">
			<div class="surveyCollectMiddle">
				
				<div class="surveyCollectMiddleContent">
					<div style="padding: 25px 45px;overflow: auto;padding-top: 35px;">
							<div style="border-bottom: 1px solid #DFDFDF;padding: 5px;color: #666565;">Account information</div>
							<div style="padding: 5px;color:#666565; ">
							<!--  user!save.action  --><!--  user!save.action  --><!--  user!save.action  --><!--  user!save.action  --><!--  user!save.action  -->
								<form id="input_form" action="${ctx }/ic/user!save.action" method="post" >
								<table class="ac-form-table">
									<tr>
										<td width="80" align="right">Account number</td>
										<td class="ac-input-td"><input type="text" name="loginName" value="${user.loginName }" readonly="readonly"  style="background: rgb(240, 240, 240);" > </td>
									</tr>
									<tr>
										<td width="80" align="right">Email</td>
										<td class="ac-input-td"><input type="text" id="email" name="email" value="${user.email }"> </td>
									</tr>
									<tr>
										<td width="80" align="right">Phone number</td>
										<td class="ac-input-td"><input type="text" name="cellphone" value="${user.cellphone }"> </td>
									</tr>
									<tr>
										<td width="80" align="right">Username</td>
										<td class="ac-input-td"><input type="text" name="name" value="${user.name }"> </td>
									</tr>
									<tr>
										<td width="80" align="right">Password</td>
										<!--  user!pwd.action --><!--  user!pwd.action --><!--  user!pwd.action --><!--  user!pwd.action --><!--  user!pwd.action -->
										<td class="ac-input-td"><a href="${ctx}/ic/user!pwd.action" >Change Password</a> </td>
									</tr>
									<tr>
										<td></td>
										<td class="ac-input-td"> <button type="submit" class="sbtn25 sbtn25_1" > Save changes </button></td>
									</tr>
								</table>
								</form>
							</div>

					</div>
					
				</div>
			</div>
			
		</div>
		</div>
	</div>
<script type="text/javascript">

</script>
</body>
</html>