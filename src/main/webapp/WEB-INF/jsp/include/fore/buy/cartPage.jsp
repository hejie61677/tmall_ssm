<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    let deleteOrderItem = false;
    let deleteOrderItemid = 0;

    $(function() {
        $("a.deleteOrderItem").click(function() {
            deleteOrderItem = false;
            let oiid = $(this).attr("oiid");
            deleteOrderItemid = oiid;
            $("#deleteConfirmModal").modal('show');
        });

        $("button.deleteConfirmButton").click(function() {
            deleteOrderItem = true;
            $("#deleteConfirmModal").modal('hide');
        });

        $('#deleteConfirmModal').on('hidden.bs.modal', function () {
            if (deleteOrderItem) {
                const page = "fore_orderitem_delete";
                $.post(
                    page,
                    {"oiid": deleteOrderItemid},
                    function(result) {
                        if ("success" === result) {
                            $("tr.cartProductItemTR[oiid="+deleteOrderItemid+"]").hide();
                        } else {
                            location.href="loginPage";
                        }
                    }
                );
            }
        });

        $("img.cartProductItemIfSelected").click(function() {
            let selectit = $(this).attr("selectit");

            if("selectit" === selectit) {
                $(this).attr("src", "img/site/cartNotSelected.png");
                $(this).attr("selectit", "false");
                $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
            } else {
                $(this).attr("src", "img/site/cartSelected.png");
                $(this).attr("selectit", "selectit");
                $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
            }

            syncSelect();
            syncCreateOrderButton();
            calcCartSumPriceAndNumber();
        });

        $("img.selectAllItem").click(function() {
            let selectit = $(this).attr("selectit");

            if ("selectit" === selectit) {
                $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");
                $("img.selectAllItem").attr("selectit", "false");

                $(".cartProductItemIfSelected").each(function() {
                    $(this).attr("src","img/site/cartNotSelected.png");
                    $(this).attr("selectit","false");
                    $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
                });
            } else {
                $("img.selectAllItem").attr("src", "img/site/cartSelected.png");
                $("img.selectAllItem").attr("selectit", "selectit");

                $(".cartProductItemIfSelected").each(function() {
                    $(this).attr("src", "img/site/cartSelected.png");
                    $(this).attr("selectit", "selectit");
                    $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
                });
            }

            syncCreateOrderButton();
            calcCartSumPriceAndNumber();

        });

        $(".orderItemNumberSetting").keyup(function() {
            let pid = $(this).attr("pid");
            let stock = $("span.orderItemStock[pid=" + pid + "]").text();
            let price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
            let num= $(".orderItemNumberSetting[pid=" + pid + "]").val();
            num = parseInt(num);

            if (isNaN(num)) {
                num= 1;
            } else {
                if (num <= 0) {
                    num = 1;
                } else if (num > stock) {
                    num = stock;
                }
            }

            syncPrice(pid, num, price);
        });

        $(".numberPlus").click(function() {
            let pid = $(this).attr("pid");
            let stock = $("span.orderItemStock[pid=" + pid + "]").text();
            let price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
            let num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
            num++;

            if (num > stock) {
                num = stock;
            }

            syncPrice(pid, num, price);
        });

        $(".numberMinus").click(function() {
            let pid =$(this).attr("pid");
            let stock = $("span.orderItemStock[pid=" + pid + "]").text();
            let price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
            let num= $(".orderItemNumberSetting[pid="+pid+"]").val();
            --num;

            if (num <= 0) {
                num = 1;
            }

            syncPrice(pid, num, price);
        });

        $("button.createOrderButton").click(function() {
            let params = "";

            $(".cartProductItemIfSelected").each(function() {

                if("selectit" === $(this).attr("selectit")) {
                    let oiid = $(this).attr("oiid");
                    params += "&oiid=" + oiid;
                }
            });
            params = params.substring(1);
            location.href="fore_buy?" + params;
        });

    });

    function syncCreateOrderButton() {
        let selectAny = false;

        $(".cartProductItemIfSelected").each(function() {

            if ("selectit" === $(this).attr("selectit")) {
                selectAny = true;
            }
        });

        if (selectAny) {
            $("button.createOrderButton").css("background-color","#C40000");
            $("button.createOrderButton").removeAttr("disabled");
        } else {
            $("button.createOrderButton").css("background-color","#AAAAAA");
            $("button.createOrderButton").attr("disabled","disabled");
        }
    }

    function syncSelect() {
        let selectAll = true;

        $(".cartProductItemIfSelected").each(function(){

            if("false" === $(this).attr("selectit")){
                selectAll = false;
            }
        });

        if (selectAll) {
            $("img.selectAllItem").attr("src","img/site/cartSelected.png");
        } else {
            $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");
        }
    }

    function calcCartSumPriceAndNumber() {
        let sum = 0;
        let totalNumber = 0;

        $("img.cartProductItemIfSelected[selectit='selectit']").each(function() {
            let oiid = $(this).attr("oiid");
            let price = $(".cartProductItemSmallSumPrice[oiid=" + oiid + "]").text();
            price = price.replace(/,/g, "");
            price = price.replace(/￥/g, "");
            sum += Number(price);
            let num =$(".orderItemNumberSetting[oiid=" + oiid + "]").val();
            totalNumber += Number(num);

        });

        $("span.cartSumPrice").html("￥" + formatMoney(sum));
        $("span.cartTitlePrice").html("￥" + formatMoney(sum));
        $("span.cartSumNumber").html(totalNumber);
    }

    function syncPrice(pid, num, price) {
        $(".orderItemNumberSetting[pid=" + pid + "]").val(num);
        let cartProductItemSmallSumPrice = formatMoney(num * price);
        $(".cartProductItemSmallSumPrice[pid=" + pid + "]").html("￥" + cartProductItemSmallSumPrice);
        calcCartSumPriceAndNumber();

        const page = "fore_orderitem_change";
        $.post(
            page,
            {"pid": pid, "number": num},
            function(result) {
                if("success" !== result){
                    location.href="loginPage";
                }
            }
        );
    }
</script>

<title>购物车</title>
<div class="cartDiv">
    <div class="cartTitle pull-right">
        <span>已选商品(不含运费)</span>
        <span class="cartTitlePrice">￥0.00</span>
        <button class="createOrderButton" disabled="disabled">结 算</button>
    </div>

    <div class="cartProductList">
        <table class="cartProductTable">
            <thead>
            <tr>
                <th class="selectAndImage">
                    <img selectit="false" class="selectAllItem" src="img/site/cartNotSelected.png">
                    全选
                </th>
                <th>商品信息</th>
                <th>单价</th>
                <th>数量</th>
                <th width="120px">金额</th>
                <th class="operation">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ois }" var="oi">
                <tr oiid="${oi.id}" class="cartProductItemTR">
                    <td>
                        <img selectit="false" oiid="${oi.id}" class="cartProductItemIfSelected" src="img/site/cartNotSelected.png">
                        <a style="display:none" href="#nowhere"><img src="img/site/cartSelected.png"></a>
                        <img class="cartProductImg"  src="img/productSingle_middle/${oi.productExpand.firstProductImage.id}.jpg">
                    </td>
                    <td>
                        <div class="cartProductLinkOutDiv">
                            <a href="foreproduct?pid=${oi.product.id}" class="cartProductLink">${oi.product.name}</a>
                            <div class="cartProductLinkInnerDiv">
                                <img src="img/site/creditcard.png" title="支持信用卡支付">
                                <img src="img/site/7day.png" title="消费者保障服务,承诺7天退货">
                                <img src="img/site/promise.png" title="消费者保障服务,承诺如实描述">
                            </div>
                        </div>
                    </td>
                    <td>
                        <span class="cartProductItemOringalPrice">￥${oi.product.original_price}</span>
                        <span  class="cartProductItemPromotionPrice">￥${oi.product.promote_price}</span>

                    </td>
                    <td>
                        <div class="cartProductChangeNumberDiv">
                            <span class="hidden orderItemStock " pid="${oi.product.id}">${oi.product.stock}</span>
                            <span class="hidden orderItemPromotePrice " pid="${oi.product.id}">${oi.product.promote_price}</span>
                            <a  pid="${oi.product.id}" class="numberMinus" href="#nowhere">-</a>
                            <input pid="${oi.product.id}" oiid="${oi.id}" class="orderItemNumberSetting" autocomplete="off" value="${oi.number}">
                            <a  stock="${oi.product.stock}" pid="${oi.product.id}" class="numberPlus" href="#nowhere">+</a>
                        </div>
                    </td>
                    <td>
                        <span class="cartProductItemSmallSumPrice" oiid="${oi.id}" pid="${oi.product.id}">
                            ￥<fmt:formatNumber type="number" value="${oi.product.promote_price * oi.number}" minFractionDigits="2"/>
                        </span>
                    </td>
                    <td>
                        <a class="deleteOrderItem" oiid="${oi.id}" href="#nowhere">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="cartFoot">
        <img selectit="false" class="selectAllItem" src="img/site/cartNotSelected.png">
        <span>全选</span>
        <!--         <a href="#">删除</a> -->
        <div class="pull-right">
            <span>已选商品 <span class="cartSumNumber">0</span> 件</span>
            <span>合计(不含运费): </span>
            <span class="cartSumPrice" >￥0.00</span>
            <button class="createOrderButton" disabled="disabled">结  算</button>
        </div>
    </div>
</div>