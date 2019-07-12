<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<script>
    $(function(){

        $("#addForm").submit( function() {
            if (!checkEmpty("name", "产品名称")) {
                return false;
            } else if (!checkEmpty("sub_title", "小标题")) {
                return false;
            } else if (!checkNumber("original_price", "原价格")) {
                return false;
            } else if (!checkNumber("promote_price", "优惠价格")) {
                return false;
            } else if (!checkEmpty("stock", "库存")) {
                return false;
            } else {
                return true;
            }
        });
    });

</script>

<title>产品管理</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${c.id}">${c.name}</a></li>
        <li class="active">产品管理</li>
    </ol>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
                <tr class="success">
                    <th>ID</th>
                    <th>图片</th>
                    <th>产品名称</th>
                    <th>产品小标题</th>
                    <th>原价格</th>
                    <th>优惠价格</th>
                    <th>库存数量</th>
                    <th>图片管理</th>
                    <th>设置属性</th>
                    <th>编辑</th>
                    <th>删除</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ps}" var="p">
                    <tr>
                        <td>${p.id}</td>
                        <td>
                            <c:if test="${!empty p.firstProductImage}">
                                <img width="40px" src="img/productSingle/${p.firstProductImage.id}.jpg">
                            </c:if>
                        </td>
                        <td>${p.name}</td>
                        <td>${p.sub_title}</td>
                        <td>${p.original_price}</td>
                        <td>${p.promote_price}</td>
                        <td>${p.stock}</td>
                        <td><a href="admin_productImage_list?pid=${p.id}"><span class="glyphicon glyphicon-picture"></span></a></td>
                        <td><a href="admin_propertyValue_edit?pid=${p.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>
                        <td><a href="admin_product_edit?id=${p.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                        <td><a deleteLink="true" href="admin_product_delete?id=${p.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                    </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>

    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增产品</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="admin_product_add">
                <table class="addTable">
                    <tr>
                        <td>产品名称</td>
                        <td><input  id="name" name="name" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>产品小标题</td>
                        <td><input  id="sub_title" name="sub_title" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>原价格</td>
                        <td><input  id="original_price" name="original_price" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td><input  id="promote_price" name="promote_price" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>库存</td>
                        <td><input  id="stock" name="stock" type="number" class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${c.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>