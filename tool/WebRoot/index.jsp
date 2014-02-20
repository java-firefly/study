<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <h2 align="center">中国标准化研究院 </h2>
<h2 align="center">计划内（已备案）科研委托项目合同审批表 </h2>
<div align="center">
  <table border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td width="94" rowspan="2"><p align="center">委托部门 <br />
        信 息 </p></td>
      <td width="131"><p align="center">部门名称 </p></td>
      <td width="446" colspan="3"><p align="center">&nbsp;</p></td>
    </tr>
    <tr>
      <td width="131"><p align="center">联系人 </p></td>
      <td width="203"><p align="center">&nbsp;</p></td>
      <td width="102"><p align="center">联系电话 </p></td>
      <td width="141"><p align="center">&nbsp;</p></td>
    </tr>
    <tr>
      <td width="94" rowspan="3"><p align="center">委托项目 <br />
        信 息 </p></td>
      <td width="131"><p align="center">项目名称 </p></td>
      <td width="446" colspan="3"><p align="center">&nbsp;</p></td>
    </tr>
    <tr>
      <td width="131"><p>受托方名称 </p></td>
      <td width="446" colspan="3"><p align="center">&nbsp;</p></td>
    </tr>
    <tr>
      <td width="131"><p>项目类型 </p></td>
      <td width="203"><p> 计划内      已备案 </p></td>
      <td width="102"><p align="center">项目总额 </p></td>
      <td width="141"><p align="center">万元 </p></td>
    </tr>
    <tr>
      <td width="94" rowspan="2"><p align="center">委托部门<br />
        审查意见 </p></td>
      <td width="578" colspan="4" valign="bottom"><p> 归属项目负责人（

签字）：<u> </u><br />
        年 月 日 </p></td>
    </tr>
    <tr>
      <td width="578" colspan="4" valign="bottom"><p>负责人（签字）：

<u> </u><br />
        年 月 日 </p></td>
    </tr>
    <tr>
      <td width="94"><p align="center">财务部门 <br />
        审核意见 </p></td>
      <td width="578" colspan="4" valign="bottom"><p>&nbsp;</p>
          <p>负责人（签字)： <br />
            年 月 日 </p></td>
    </tr>
    <tr>
      <td width="94" rowspan="2"><p align="center">科研管理 <br />
        部门 <br />
        审核意见 </p></td>
      <td width="578" colspan="4" valign="bottom"><p>经办人（签字）： 

<br />
        年 月 日 </p></td>
    </tr>
    <tr>
      <td width="578" colspan="4" valign="bottom"><p>负责人（签字）： 

<br />
        年 月 日 </p></td>
    </tr>
    <tr>
      <td width="94"><p align="center">科研主管 <br />
        院领导 <br />
        审批意见 </p></td>
      <td width="578" colspan="4" valign="bottom"><p>主管院领导（签字

）<u> </u><br />
        年 月 日 </p></td>
    </tr>
    <tr>
      <td width="94"><p align="center">院长 <br />
        审批意见 </p></td>
      <td width="578" colspan="4" valign="top"><p>（采购合同金额在20万

元以上，须院长审批） </p>
          <p>院长（签字)：<u> </u><br />
            年 月 日 </p></td>
    </tr>
  </table>
</div>
<p>注：1. 本表适用于计划内或已备案科研委托项目。 <br />
  2. 本表须连同合同文本及归属项目任务书或项目计划备案表提交至院科研管

理部门，其中20万元以上已备案项目须另附受托方选择证明材料。 <br />
  3. 本表原件应在合同签订后交科研管理部门留存。<br />
  4. 请以“ ”代替“ ”表示为选中。</p>
  </body>
</html>
