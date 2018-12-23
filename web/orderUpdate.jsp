<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.pwk.com" prefix="pwk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${empty pageContext.session.getAttribute('admin')}">
  <c:redirect url="login.jsp"/>
</c:if>
<c:set value="${pwk:getTransactionById(param.id)}" var="transaction"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Order Update</title>

  <!-- Bootstrap core CSS -->
  <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

  <link href="${pageContext.request.contextPath}/js/uploadify/uploadify.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>

<body>

<div class="container">

  <form class="form-signin" id="form" method="post" action="${pageContext.request.contextPath}/transaction/update.do">

    <input type="hidden" name="id" id="id" value="${transaction.id}">

    <div id="products">
      <c:forEach items="${transaction.items}" var="item">
        <div>
          <input type="hidden" name="ids" value="${item.id}">
            ${item.brandName}/${item.productName}/${item.count}
          <br>
          <a href='javascript:void(0)' class='btn btn-success' onclick='getProduct(this,"${item.id}","${transaction.id}")'>Update</a>
          <a href='javascript:void(0)' class='btn btn-danger' onclick='deleteProduct(this,"${item.id}","${transaction.id}")'>Delete</a>
          <br>
        </div>
      </c:forEach>
    </div>
    <div id="productContent">

    </div>

    <input type="radio" name="payType" value="FullPay" ${transaction.type eq 'FullPay'?'checked':''}>Full Pay
    <input type="radio" name="payType" value="Deposit" ${transaction.type eq 'Deposit'?'checked':''}>Deposit
    <input type="radio" name="payType" value="Installment" ${transaction.type eq 'Installment'?'checked':''}>Installment

    <label for="bank" class="sr-only">Bank</label>
    <select name="bank" id="bank" class="form-control">
      <option value=""></option>
      <option value="Maybank" ${transaction.bank eq 'Maybank'?'selected':''}>Maybank</option>
      <option value="Public Bank" ${transaction.bank eq 'Public Bank'?'selected':''}>Public Bank</option>
      <option value="Hongleong Bank" ${transaction.bank eq 'Hongleong Bank'?'selected':''}>Hongleong Bank</option>
      <option value="Ambank" ${transaction.bank eq 'Ambank'?'selected':''}>Ambank</option>
      <option value="PayPal" ${transaction.bank eq 'PayPal'?'selected':''}>PayPal</option>
      <option value="RHB" ${transaction.bank eq 'RHB'?'selected':''}>RHB</option>
      <option value="CIMB" ${transaction.bank eq 'CIMB'?'selected':''}>CIMB</option>
      <option value="HSBC" ${transaction.bank eq 'HSBC'?'selected':''}>HSBC</option>
      <option value="Others" ${transaction.bank eq 'Others'?'selected':''}>Others</option>
    </select>

    <label for="price" class="sr-only">Price</label>
    <input type="number" id="price" name="price" class="form-control" placeholder="Price" required onblur="copyPrice()" value="${transaction.price}">

    <label for="paid" class="sr-only">Paid</label>
    <input type="number" id="paid" name="paid" class="form-control " placeholder="Paid" value="${transaction.paid}">

    <label for="contact_type">Contact Type</label>
    <input type="radio" name="contact" value="Wechat" id="contact_type" ${transaction.contactType eq 'Wechat'?'checked':''}>Wechat
    <input type="radio" name="contact" value="Facebook" ${transaction.contactType eq 'Facebook'?'checked':''}>Facebook
    <input type="radio" name="contact" value="Whatsapp" ${transaction.contactType eq 'Whatsapp'?'checked':''}>Whatsapp
    <input type="radio" name="contact" value="Line" ${transaction.contactType eq 'Line'?'checked':''}>Line
    <input type="radio" name="contact" value="Phone/SMS" ${transaction.contactType eq 'Phone/SMS'?'checked':''}>Phone/SMS
    <input type="radio" name="contact" value="Others" ${transaction.contactType eq 'Others'?'checked':''}>Others

    <label for="name" class="sr-only">Name</label>
    <input type="text" id="name" name="name" class="form-control" placeholder="Contact Name" required value="${transaction.contactName}">

    <label for="phone" class="sr-only">Phone</label>
    <input type="text" id="phone" name="phone" class="form-control" placeholder="Contact Phone" value="${transaction.phone}">

    <label for="address" class="sr-only">Address</label>
    <textarea name="address" id="address" class="form-control" placeholder="Address">${transaction.address}</textarea>

    <label for="remark" class="sr-only">Remark</label>
    <textarea name="remark" id="remark" class="form-control" placeholder="Remark">${transaction.remark}</textarea>

    <c:forEach items="${transaction.picPath}" var="pic">
      <img src="${pwk:getPicDomain()}/${pic}" style="max-width: 200px;">
    </c:forEach>
    <span class="btn btn-success fileinput-button">
        <i class="glyphicon glyphicon-plus"></i>
        <span>Add files...</span>
        <input id="fileupload" type="file" name="uploadImages" multiple>
    </span>
    <br>
    <br>
    <div id="progress" class="progress">
      <div class="progress-bar progress-bar-success"></div>
    </div>
    <div id="files" class="files"></div>
    <br>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
  </form>

</div> <!-- /container -->
<script src="${pageContext.request.contextPath}/backLogin/js/jquery-1.8.2.min.js"></script>
<script src="fileupload/js/jquery.ui.widget.js"></script>
<script src="fileupload/js/load-image.all.min.js"></script>
<script src="fileupload/js/canvas-to-blob.min.js"></script>
<script src="fileupload/js/jquery.iframe-transport.js"></script>
<script src="fileupload/js/jquery.fileupload.js"></script>
<script src="fileupload/js/jquery.fileupload-process.js"></script>
<script src="fileupload/js/jquery.fileupload-image.js"></script>
<script src="fileupload/js/jquery.fileupload-validate.js"></script>
<script>
  /*jslint unparam: true, regexp: true */
  /*global window, $ */
  $(function () {
    'use strict';
    // Change this to the location of your server-side upload handler:
    var url = '${pageContext.request.contextPath}/image/uploadPic.do?type=transaction',
            uploadButton = $('<button/>')
                    .addClass('btn btn-primary')
                    .prop('disabled', true)
                    .text('Processing...')
                    .on('click', function () {
                      var $this = $(this),
                              data = $this.data();
                      $this
                              .off('click')
                              .text('Abort')
                              .on('click', function () {
                                $this.remove();
                                data.abort();
                              });
                      data.submit().always(function () {
                        $this.remove();
                      });
                    });
    $('#fileupload').fileupload({
      url: url,
      dataType: 'json',
      autoUpload: true,
      acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
      maxFileSize: 999000,
      // Enable image resizing, except for Android and Opera,
      // which actually support image resizing, but fail to
      // send Blob objects via XHR requests:
      disableImageResize: /Android(?!.*Chrome)|Opera/
              .test(window.navigator.userAgent),
      previewMaxWidth: 100,
      previewMaxHeight: 100,
      previewCrop: true
    }).on('fileuploadadd', function (e, data) {
      data.context = $('<div/>').appendTo('#files');
      $.each(data.files, function (index, file) {
        var node = $('<p/>')
                .append($('<span/>').text(file.name));
        node.appendTo(data.context);
      });
    }).on('fileuploadprocessalways', function (e, data) {
      var index = data.index,
              file = data.files[index],
              node = $(data.context.children()[index]);
      if (file.preview) {
        node
                .prepend('<br>')
                .prepend(file.preview);
      }
      if (file.error) {
        node
                .append('<br>')
                .append($('<span class="text-danger"/>').text(file.error));
      }
      if (index + 1 === data.files.length) {
        data.context.find('button')
                .text('Upload')
                .prop('disabled', !!data.files.error);
      }
    }).on('fileuploadprogressall', function (e, data) {
      var progress = parseInt(data.loaded / data.total * 100, 10);
      $('#progress .progress-bar').css(
              'width',
              progress + '%'
      );
    }).on('fileuploaddone', function (e, data) {
      console.log(data.result);
      $("#form").append("<input type='hidden' name='pic' value='"+data.result.picPath+"'/>");
    }).on('fileuploadfail', function (e, data) {
      console.log(data.result);
      alert("Upload Image Fail!");
    }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
  });

  function copyPrice(){
    var type = $('input[name=payType]:checked');
    if(type == null){
      alert("please select type");
    }else{
      if(type.val()=='FullPay'){
        $("#paid").attr("value",$("#price").val());
      }else{
        $("#paid").attr("value","");
      }
    }
  }

  function getProduct(obj,iId,tId){
    $.post("${pageContext.request.contextPath}/item/getItemHtml.do",{'id':iId},function(data){
      var productContent = $("#productContent");
      productContent.empty();
      productContent.append(data);
    },"html");
    $(obj).parent().empty();
  }

  function updateProduct(){
    var tId = $("#id").val();
    var itemId = $("#itemId").val();
    var brand = $("#brand").val();
    var product = $("#product").val();
    var quantity = $("#quantity").val();
    $.post("${pageContext.request.contextPath}/item/update.do",{'id':itemId,'tId':tId,'brandName':brand,'product':product,'quantity':quantity},function(data){
      $("#productContent").empty();
      var products = $("#products");
      products.append(brand+"/"+product+"/"+quantity+"<br>");
      products.append("<a href='javascript:void(0)' class='btn btn-success' onclick='getProduct(this,\""+itemId+"\",\""+tId+"\")'>Update</a>");
      products.append("<a href='javascript:void(0)' class='btn btn-danger' onclick='deleteProduct(this,\""+itemId+"\",\""+tId+"\")'>Delete</a>");
    },'json');
  }

  function addProduct(obj){
    obj.remove();
    $("#product").attr("value","");
    $("#quantity").attr("value","");
    $("#productContent").show();
  }

  function deleteProduct(obj,iId,tId){
    if(confirm("confirm to delete this product?")){
      $.post("${pageContext.request.contextPath}/item/delete.do",{"id":iId,"tId":tId},function(data){
        if(data == "ok"){
          $(obj).parent().remove();
        }else{
          alert("delete product fail, please try again");
        }
      });
    }
  }

</script>
</body>
</html>
