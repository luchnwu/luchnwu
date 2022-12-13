let app = new Vue({
    el: '#app',
    data: {
        username: '',
        password: '',
        confirm: '',
        name: '',
        phone: '',
        message: '',
        hasError: false
    },
    methods: {
        register: function () {
            let data = {
                username: this.username,
                password: this.password,
                confirm: this.confirm,
                name: this.name,
                phone: this.phone
            };
            console.log(data);
            let _this=this;
            $.ajax({
                url: '/register',
                method: 'POST',
                data: data,
                success: function (r) {
                    // console.log(r.message)
                    _this.hasError = true;
                    _this.message = r.message;
                }
            });
        }
    }
});