let productApp = new Vue({
    el: '#productApp',
    data: {
        product: {},
    },
    methods: {
        loadProduct: function () {
            let id = location.search;
            id = id.substring(1);
            $.ajax({
                url: '/portal/product/' + id,
                method: 'GET',
                success: function (p) {

                    // console.log("加載成功", p);
                    if (p.code === OK) {
                        productApp.product = p.data;
                    }
                    // console.log(productApp.product.img);
                }
            });
        }
    },
    created: function () {
        console.log('開始加載商品');
        this.loadProduct();
    }
});

$("#market").click(function () {
    $.ajax(
        {
            url: '/portal/paymarket/market',
            method: 'GET',
            data: {
                productId: productApp.product.id,
                productCount: num.innerText,
            },
            // success: function (p) {
            //     if (p == null) {
            //     } else {
            //         window.location(p.redirect);
            //     }
            // }
        }
    );
    marketsApp.loadMarkets();
    num.innerText = 1;
});

$("#increase").click(function () {
    num.innerText++;
});

$("#decrease").click(function () {
    num.innerText--;
    if (num.innerText == 0) {
        num.innerText = 1;
    }
});
