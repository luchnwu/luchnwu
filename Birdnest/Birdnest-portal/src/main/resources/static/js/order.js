let orderApp = new Vue({
    el: '#orderApp',
    data: {
        pay_method: '',
        order_username: '',
        order_email: '',
        order_address: '',
        order_phone: '',
        recipient_username: '',
        recipient_email: '',
        recipient_address: '',
        recipient_phone: '',
        hasError: false
    },
    methods: {
        order: function () {
            console.log(this.pay_method)
            let data = {
                pay_method: this.pay_method,
                order_username: this.order_username,
                order_email: this.order_email,
                order_address: this.order_address,
                order_phone: this.order_phone,
                recipient_username: this.recipient_username,
                recipient_email: this.recipient_email,
                recipient_address: this.recipient_address,
                recipient_phone: this.recipient_phone,
            };
            console.log(data);
            let _this = this;
            $.ajax({
                url: '/portal/order',
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