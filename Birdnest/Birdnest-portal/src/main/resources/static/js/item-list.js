let productsApp = new Vue({
    el: '#productsApp',
    data: {
        products: [],
        pageInfo: []
    },
    methods: {
        loadProducts: function (pageNum) {
            if (!pageNum) {
                pageNum = 1;
            }
            $.ajax({
                url: '/portal/product/products',
                method: 'GET',
                data: {
                    pageNum: pageNum
                },
                success: function (p) {
                    console.log("加載成功", p);
                    if (p.code === OK) {
                        productsApp.products = p.data.list;
                        productsApp.pageInfo = p.data;
                    }
                }
            });
        }
    },
    created: function () {
        console.log('開始加載商品');
        this.loadProducts(1);
    }
});