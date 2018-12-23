<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.ParseException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.pwk.com" prefix="pwk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${empty pageContext.session.getAttribute('admin')}">
    <c:redirect url="login.jsp"/>
</c:if>
<%
    SimpleDateFormat year = new SimpleDateFormat("yyyy");
    SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();

    request.setAttribute("year",year.format(date));
    request.setAttribute("month",month.format(date));

    try {
        date = day.parse(day.format(date));
    } catch (ParseException e) {
        e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, -1);
    Date yesterday = calendar.getTime();
    request.setAttribute("day", day.format(yesterday));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Order List</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/jquery.datetimepicker.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/css/dashboard.css" rel="stylesheet">

    <style>
        .btn{
            font-size:10px;
        }
    </style>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="color: white;" href="${pageContext.request.contextPath}/orderAdd.jsp">Add Order</a>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-md-12">
            <h1 class="page-header">Dashboard</h1>

            <div class="row placeholders">
                <div class="col-xs-6 col-sm-3 placeholder">
                    <h4>Yesterday Revenue</h4>
                    <span class="text-muted" style="color: green"><fmt:formatNumber value="${pwk:getRevenue(day)}" type="currency" pattern="###,##0.00"/></span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <h4>Monthly Revenue</h4>
                    <span class="text-muted" style="color: purple"><fmt:formatNumber value="${pwk:getRevenue(month)}" type="currency" pattern="###,##0.00"/></span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <h4>Yearly Revenue (from 2015-07-29)</h4>
                    <span class="text-muted" style="color: red"><fmt:formatNumber value="${pwk:getRevenue(year)}" type="currency" pattern="###,##0.00"/></span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
                    <h4>Label</h4>
                    <span class="text-muted">Something else</span>
                </div>
            </div>

            <div class="navbar-form">
                <input type="text" class="form-control" id="keyword" placeholder="Search..." name="keyword" value="${param.keyword}">
                <input type="text" class="form-control" id="datetimepicker" placeholder="Select Start Date..." name="startDate" value="${param.startDate}"/>
                <input type="text" class="form-control" id="datetimepicker2" placeholder="Select End Date..." name="endDate" value="${param.endDate}"/>
                <a href="javascript:void(0)" class="btn btn-success" onclick="searchTransaction()">Search</a>
                <a href="javascript:void(0)" class="btn btn-default" onclick="resetTransaction()">Reset</a>
            </div>
            <div class="table-responsive">
                <table class="table table-striped" style="font-size: 10px;">
                    <thead>
                    <tr>
                        <th>Brand/Product/Quantity</th>
                        <th>Payment Status</th>
                        <th>Order Status</th>
                        <th>Name/SM</th>
                        <th>Operations</th>
                        <th>Pic</th>
                        <th>Price</th>
                        <th>Date</th>
                        <th>Type</th>
                        <th>Phone</th>
                        <th>Delivery Date</th>
                        <th>Remark</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pwk:getByKeywordAndDate(param.keyword, param.startDate, param.endDate, param.p, param.s)}" var="transaction">
                        <tr>
                            <td>
                                <table>
                                    <c:forEach items="${transaction.items}" var="item">
                                        <tr>
                                            <td>
                                                <span style="color:red">${item.brandName}</span>
                                                /
                                                <span style="color: green">${item.productName}</span>
                                                /
                                                <span style="color: blue">${item.count}</span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                            <td>
                                <a class="btn btn-warning" href="${pageContext.request.contextPath}/transaction/payStatus.do?id=${transaction.id}">${transaction.payStatus}</a>
                            </td>
                            <td>
                                <select id="orderStatus" onchange="orderStatus('${transaction.id}')">
                                    <option></option>
                                    <option value="1" ${transaction.orderStatus eq 1?'selected':''}>Waiting for order</option>
                                    <option value="2" ${transaction.orderStatus eq 2?'selected':''}>Lead Time</option>
                                    <option value="3" ${transaction.orderStatus eq 3?'selected':''}>Delivered</option>
                                    <option value="4" ${transaction.orderStatus eq 4?'selected':''}>Cancelled</option>
                                    <option value="4" ${transaction.orderStatus eq 5?'selected':''}>Refunded</option>
                                </select>
                            </td>
                            <td>${transaction.contactName}/${transaction.contactType}</td>
                            <td><a href="orderUpdate.jsp?id=${transaction.id}" class="btn btn-info">Update</a> <a href="javascript:if(confirm('confirm to delete?')){window.location.href='${pageContext.request.contextPath}/transaction/delete.do?id=${transaction.id}'}" class="btn btn-danger">Delete</a></td>
                            <td>
                                <c:forEach items="${transaction.picPath}" var="picPath" varStatus="vs">
                                    <a href="${pwk:getPicDomain()}/${picPath}" target="_blank">image${vs.count}</a>
                                </c:forEach>
                            </td>
                            <c:if test="${transaction.type eq 'FullPay'}">
                                <td><fmt:formatNumber value="${transaction.price}" type="currency" pattern="###,##0.00"/>/${transaction.bank}</td>
                            </c:if>
                            <c:if test="${transaction.type eq 'Deposit'||transaction.type eq 'Installment'}">
                                <td>
                                    <span style="color: blue"><fmt:formatNumber value="${transaction.price}" type="currency" pattern="###,##0.00"/></span>
                                    /
                                    <span style="color:salmon"><fmt:formatNumber value="${transaction.paid}" type="currency" pattern="###,##0.00"/></span>
                                    /
                                    <span style="color: green"><fmt:formatNumber value="${transaction.payable}" type="currency" pattern="###,##0.00"/></span>
                                    /${transaction.bank}
                                </td>
                            </c:if>
                            <td>
                                <input type="text" id="datetimepicker3" value="${transaction.createTime}" onfocus="$(this).datetimepicker({lang:'en',timepicker:false,format:'Y-m-d',formatDate:'Y-m-d',onSelect: function(dateText, inst){
            console.log(dateText);
    }});">
                                <a href="javascript:void(0)" class="btn btn-success" onclick="changeDate('${transaction.id}',$('#datetimepicker3').val());">Update</a>
                            </td>
                            <td>${transaction.type}</td>
                            <td>${transaction.phone}</td>
                            <td>${transaction.deliveryDate}</td>
                            <td>${transaction.remark}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <nav>
                <ul class="pager">
                    <li class="${param.p<=1 or empty param.p?'disabled':''}"><a href="${pageContext.request.contextPath}/orderList.jsp?keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}&p=${param.p-1}&s=30">Previous</a></li>
                    <li><a href="${pageContext.request.contextPath}/orderList.jsp?keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}&p=${param.p+1}&s=30">Next</a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/moment.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.datetimepicker.js"></script>
<script>
    function orderStatus(id){
        var orderStatus = $("#orderStatus").val();
        if (orderStatus != null&& orderStatus != ''){
            $.get('${pageContext.request.contextPath}/transaction/orderStatus.do',{'id':id,'orderStatus':orderStatus},function(data){
                alert("success");
            });
        }
    }
</script>
<script type="text/javascript">
    $(function () {
        $('#datetimepicker').datetimepicker({
            lang:'en',
            timepicker:false,
            format:'Y-m-d',
            formatDate:'Y-m-d'
        });
        $('#datetimepicker2').datetimepicker({
            lang:'en',
            timepicker:false,
            format:'Y-m-d',
            formatDate:'Y-m-d'
        });
    });

    function searchTransaction(){
        var keyword = $("#keyword").val();
        var startDate = $("#datetimepicker").val();
        var endDate = $("#datetimepicker2").val();
        window.location.href = "${pageContext.request.contextPath}/orderList.jsp?keyword="+keyword+"&startDate="+startDate+"&endDate="+endDate;
    }

    function resetTransaction(){
        window.location.href = "${pageContext.request.contextPath}/orderList.jsp";
    }

    function changeDate(id,date){
        $.post("${pageContext.request.contextPath}/transaction/updateDate.do",{'id':id,'date':date},function(data){
            if(data == 'ok')
            alert("success");
        });
    }
</script>
</body>
</html>
