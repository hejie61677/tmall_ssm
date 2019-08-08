<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="searchProducts">
    <c:forEach items="${ps}" var="p">
        <div class="productUnit" price="${p.promote_price}">
            <a href="fore_product?pid=${p.id}">
                <img class="productImage" src="img/productSingle/${p.firstProductImage.id}.jpg">
            </a>
            <span class="productPrice">¥<fmt:formatNumber type="number" value="${p.promote_price}" minFractionDigits="2"/></span>
            <a class="productLink" href="fore_product?pid=${p.id}">
                    ${fn:substring(p.name, 0, 50)}
            </a>

            <a class="tmallLink" href="fore_product?pid=${p.id}">天猫专卖</a>

            <div class="productInfo">
                <span class="monthDeal ">月成交<span class="productDealNumber">${p.sales}笔</span></span>
                <span class="productReview">评价<span class="productReviewNumber">${p.reviews}</span></span>
                <span class="wangwang"><img src="img/site/wangwang.png"></span>
            </div>
        </div>
    </c:forEach>
    <c:if test="${empty ps}">
        <div class="noMatch">没有满足条件的产品</div>
    </c:if>
    <div style="clear:both"></div>
</div>