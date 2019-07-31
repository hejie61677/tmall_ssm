<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<div class="productReviewDiv" >
    <div class="productReviewTopPart">
        <a  href="#nowhere" class="productReviewTopPartSelectedLink">商品详情</a>
        <a  href="#nowhere" class="selected">累计评价 <span class="productReviewTopReviewLinkNumber">${p.reviews}</span> </a>
    </div>

    <div class="productReviewContentPart">
        <c:forEach items="${reviews}" var="r">
            <div class="productReviewItem">
                <div class="productReviewItemDesc">
                    <div class="productReviewItemContent">
                        ${r.content}
                    </div>
                    <div class="productReviewItemDate">
                        <fmt:formatDate value="${r.create_date}" pattern="yyyy-MM-dd"/>
                    </div>
                </div>
                <div class="productReviewItemUserInfo">
                    ${r.userExpand.anonymousName}<span class="userInfoGrayPart">（匿名）</span>
                </div>
                <div style="clear:both"></div>
            </div>
        </c:forEach>
    </div>

</div>
