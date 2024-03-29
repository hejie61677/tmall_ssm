<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    function showProductsAsideCategorys(cid) {
        $("div.eachCategory[cid="+cid+"]").css("background-color", "white");
        $("div.eachCategory[cid="+cid+"] a").css("color", "#87CEFA");
        $("div.productsAsideCategorys[cid=" + cid + "]").show();
    }

    function hideProductsAsideCategorys(cid) {
        $("div.eachCategory[cid="+cid+"]").css("background-color", "#e2e2e3");
        $("div.eachCategory[cid="+cid+"] a").css("color", "#000");
        $("div.productsAsideCategorys[cid=" + cid + "]").hide();
    }

    $(function() {
        $("div.eachCategory").mouseenter(function() {
            let cid = $(this).attr("cid");
            showProductsAsideCategorys(cid);
        });

        $("div.eachCategory").mouseleave(function() {
            let cid = $(this).attr("cid");
            hideProductsAsideCategorys(cid);
        });

        $("div.productsAsideCategorys").mouseenter(function() {
            let cid = $(this).attr("cid");
            showProductsAsideCategorys(cid);
        });

        $("div.productsAsideCategorys").mouseleave(function() {
            let cid = $(this).attr("cid");
            hideProductsAsideCategorys(cid);
        });

        $("div.rightMenu span").mouseenter(function() {
            let left = $(this).position().left;
            let top = $(this).position().top;
            let width = $(this).css("width");
            let destLeft = parseInt(left) + parseInt(width) / 2;
            $("img#catear").css("left", destLeft);
            $("img#catear").css("top", top - 0);
            $("img#catear").fadeIn(500);

        });
        $("div.rightMenu span").mouseleave(function() {
            $("img#catear").hide();
        });

        let left = $("div#carousel-of-product").offset().left;
        $("div.categoryMenu").css("left", left - 20);
        $("div.categoryWithCarousel div.head").css("margin-left", left);
        $("div.productsAsideCategorys").css("left", left - 20);
    })
</script>

<img src="img/site/catear.png" id="catear" class="catear"/>

<div class="categoryWithCarousel">

    <div class="headbar show1">
        <div class="head ">
            <span style="margin-left:10px" class="glyphicon glyphicon-th-list"></span>
            <span style="margin-left:10px" >商品分类</span>
        </div>

        <div class="rightMenu">
            <span><a href=""><img src="img/site/chaoshi.png"/></a></span>
            <span><a href=""><img src="img/site/guoji.png"/></a></span>
            <c:forEach items="${cs}" var="c" varStatus="st">
                <c:if test="${st.count <= 4}">
                <span>
                <a href="fore_category?cid=${c.id}">
                        ${c.name}
                </a></span>
                </c:if>
            </c:forEach>
        </div>

    </div>

    <div style="position: relative">
        <%@include file="categoryMenu.jsp"%>
    </div>

    <div style="position: relative;left: 0;top: 0;">
        <%@include file="productsAsideCategorys.jsp"%>
    </div>

    <%@include file="carousel.jsp"%>

    <div class="carouselBackgroundDiv">
    </div>
</div>
