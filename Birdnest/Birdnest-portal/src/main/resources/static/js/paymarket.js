// let n = num.innerText;
// console.log(n);
var sum = 0;
let marketsApp = new Vue({
    el: '#marketsApp',
    data: {
        markets: [],
        sum:''
    },
    methods: {
        loadMarkets: function () {
            $.ajax({
                url: '/portal/paymarket/markets',
                method: 'GET',
                success: function (p) {
                    // console.log("加載成功", p);
                    if (p.code === OK) {
                        marketsApp.markets = p.data;
                        headMarketsApp.markets = p.data;
                        console.log(marketsApp.markets);

                        for (let i = 0; i < marketsApp.markets.length; i++) {
                            sum += marketsApp.markets[i].price * marketsApp.markets[i].productCount;
                        }
                        console.log(sum);
                        marketsApp.sum = sum;
                    }
                }
            });
        },
        increase: function (id) {
            // console.log(id)
            $.ajax(
                {
                    url: '/portal/paymarket/increase/' + id,
                    method: 'GET',
                }
            );
        },
        decrease: function (id) {
            // console.log(id)
            $.ajax(
                {
                    url: '/portal/paymarket/decrease/' + id,
                    method: 'GET',
                }
            );
        },
        del: function (id) {
            // console.log(id)
            $.ajax(
                {
                    url: '/portal/paymarket/del/' + id,
                    method: 'GET',
                }
            );
        }
    },
    created: function () {
        console.log('開始加載購物車');
        this.loadMarkets();
    }
});

// function sum() {
//     var sum = 0;
//     console.log(marketsApp.markets[0].price);
//     for (let i = 0; i < marketsApp.markets.length; i++) {
//         sum += marketsApp.markets[i].price * marketsApp.markets[i].productCount;
//     }
//     console.log(sum);
// }


let headMarketsApp = new Vue({
    el: '#headMarketsApp',
    data: {
        markets: []
    },
    methods: {
        increase: function (id) {
            marketsApp.increase(id);
            marketsApp.loadMarkets();
        },
        decrease: function (id) {
            marketsApp.decrease(id);
            marketsApp.loadMarkets();
        },
        del: function (id) {
            marketsApp.del(id);
            marketsApp.loadMarkets();
        },
    }
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