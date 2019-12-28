<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>用户管理</title>
    <script src="${pageContext.request.contextPath}/js/datepicker/WdatePicker.js" type="text/javascript"></script>
    <script>
        var vResult = false;
        function doVerify(){
            var accountVal = $("#account").val();
            if ($.trim(accountVal) != ""){
                //2.校验
                $.ajax({
                    url:"${pageContext.request.contextPath}/nsfw/user_verifyAccount.action",
                    data:{"user.account":accountVal,"user.id":"${user.id}"},
                    type:"POST",
                    async:false,
                    success:function (msg) {
                        if ("true" != msg){
                            //账号已经存在
                            alert("账号已经存在,请使用其他账号!");
                            //定焦
                            $("#account").focus();
                        }else {
                            vResult = true;
                        }
                    }
                });
            }else {
                alert("账号不能为空!"+$.trim(accountVal));
                $("#account").val("");
                //定焦
                $("#account").focus();
            }
        }
        //提交表单
        function doVerifySubmit() {
            var name = $("#name");
            if($.trim(name.val())==""){
                alert("用户名不能为空！");
                name.val("");
                name.focus();
                return false;
            }
            var password = $("#password");
            if($.trim(password.val()) == ""){
                alert("密码不能为空！");
                password.val("");
                password.focus();
                return false;
            }
            //帐号校验
            doVerify();
            return vResult;
        }

    </script>
</head>
<body class="rightBody">
<form id="form" name="form" action="${pageContext.request.contextPath}/nsfw/user_edit.action" method="post" enctype="multipart/form-data" onsubmit="return doVerifySubmit()">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>用户管理</strong>&nbsp;-&nbsp;编辑用户</div></div>
    <div class="tableH2">编辑用户</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">所属部门：</td>
            <td><s:select name="user.dept" list="#{'部门A':'部门A','部门B':'部门B'}"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">头像：</td>
            <td>
                <s:if test="%{user.headImg != null && user.headImg != ''}">
                    <img src="${pageContext.request.contextPath}/upload/<s:property value='user.headImg'/>" width="100" height="100"/>
                    <s:hidden name="user.headImg"/>
                </s:if>
                <input type="file" name="headImg"/>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">用户名：</td>
            <td><s:textfield name="user.name" id="name"/> </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">帐号：</td>
            <td><s:textfield name="user.account" onchange="doVerify()" id="account"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">密码：</td>
            <td><s:textfield name="user.password" id="password"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">性别：</td>
            <td><s:radio list="#{'true':'男','false':'女'}" name="user.gender"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">角色：</td>
            <td>
                <s:checkboxlist list="#roleList" name="userRoleIds" listKey="roleId" listValue="name"></s:checkboxlist>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">电子邮箱：</td>
            <td><s:textfield name="user.email"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">手机号：</td>
            <td><s:textfield name="user.mobile"/></td>
        </tr>        
        <tr>
            <td class="tdBg" width="200px">生日：</td>
            <td><s:textfield id="birthday" name="user.birthday"
                             readonly="true" onfocus="WdatePicker({skin:'whyGreen', el:'birthday',dateFmt:'yyyy-MM-dd'})">
                <s:param name="value">
                    <s:date name="user.birthday" format="yyyy-MM-dd"/>
                </s:param>
                </s:textfield>
            </td>
        </tr>
		<tr>
            <td class="tdBg" width="200px">状态：</td>
            <td><s:radio list="#{'1':'有效','0':'无效'}" name="user.state"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td><s:textarea name="user.comment" cols="75" rows="3"/></td>
        </tr>
    </table>
                <s:hidden name="user.id"/>
    <div class="tc mt20">
        <input type="submit" class="btnB2" value="保存" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div>
        </div>
    </div>
</form>
</body>
</html>