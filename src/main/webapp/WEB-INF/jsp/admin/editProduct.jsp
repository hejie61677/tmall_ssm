<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<title>编辑产品</title>

<script>
    $(function() {
        $("#editForm").submit(function() {
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
    })
</script>

<div class="workingArea">

    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
        <li class="active">编辑产品</li>
    </ol>

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑产品</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_product_update">
                <table class="editTable">
                    <tr>
                        <td>产品名称</td>
                        <td>
                            <input id="name" name="name" value="${p.name}" type="text" class="form-control">
                        </td>
                    </tr>
                    <tr>
                        <td>产品小标题</td>
                        <td>
                            <input id="sub_title" name="sub_title" value="${p.sub_title}" type="text" class="form-control">
                        </td>
                    </tr>
                    <tr>
                        <td>原价格</td>
                        <td>
                            <input id="original_price" name="original_price" value="${p.original_price}" type="text" class="form-control">
                        </td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td>
                            <input id="promote_price" name="promote_price" value="${p.promote_price}" type="text" class="form-control">
                        </td>
                    </tr>
                    <tr>
                        <td>库存</td>
                        <td>
                            <input id="stock" name="stock" value="${p.stock}" type="text" class="form-control">
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${p.id}">
                            <input type="hidden" name="cid" value="${p.category.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>