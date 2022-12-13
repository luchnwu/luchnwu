let tagsApp = new Vue({
    el: '#tagsApp',
    data: {
        tags: []
    },
    methods: {
        loadTags: function () {
            console.log('開始加載tags');
            $.ajax({
                url: '/v1/tags',
                method: 'GET',
                success: function (r) {
                    console.log(r);
                    if (r.code === OK) {
                        console.log("加載成功")
                        console.log(r.data);
                        tagsApp.tags = r.data;
                    }
                }
            });
        }
    },
    created: function () {
        console.log('頁面加載以後立即加載tags');
        this.loadTags();
    }
});