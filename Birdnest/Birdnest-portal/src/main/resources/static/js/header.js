// let n = num.innerText;
// console.log(n);
let marketsApp = new Vue({
    el: '#headmarketsApp',
    data: {
        markets: []
    },
    methods: {
        loadMarkets: function () {
            $.ajax({
                url: '/portal/paymarket/markets',
                method: 'GET',
                success: function (p) {
                    console.log("加載成功", p);
                    if (p.code === OK) {
                        marketsApp.markets = p.data;
                    }
                }
            });
        }
    },
    created: function () {
        console.log('開始加載購物車');
        this.loadMarkets();
    }
});


$("#increase").click(function () {
    num.innerText++;
    num.innerText = num.innerText;
    // console.log(num.innerText);
    total.innerText = price.innerText * num.innerText;
});

$("#decrease").click(function () {
    num.innerText--;
    if (num.innerText == 0) {
        num.innerText = 1;
    }
    num.innerText = num.innerText;
    // console.log(num.innerText);
    total.innerText = price.innerText * num.innerText;
});

$("#del").click(function () {
    $("#market").remove();
});

// function increase() {
//     num.innerText++;
//     num.innerText = num.innerText;
//     total.innerText = price.innerText * num.innerText;
// }
//
// function decrease() {
//     num.innerText--;
//     if (num.innerText == 0) {
//         num.innerText = 1;
//     }
//     num.innerText = num.innerText;
//     total.innerText = price.innerText * num.innerText;
// }