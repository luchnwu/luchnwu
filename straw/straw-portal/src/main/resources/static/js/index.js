let questionsApp = new Vue({
    el: '#questionsApp',
    data: {
        questions: [],
        pageInfo: [],
    },
    methods: {
        loadQuestions: function (pageNum) {
            console.log('執行questions');
            if (!pageNum) {
                pageNum = 1;
            }
            $.ajax({
                url: '/v1/questions/my',
                method: 'GET',
                data: {
                    pageNum: pageNum
                },
                success: function (r) {
                    console.log("加載成功", r);
                    if (r.code === OK) {
                        questionsApp.questions = r.data.list;
                        questionsApp.pageInfo = r.data;

                        questionsApp.updateDuration();
                        questionsApp.updateTagImage();
                    }
                }
            });
        },
        updateTagImage: function () {
            let questions = this.questions;

            for (let i = 0; i < questions.length; i++) {
                let tags = questions[i].tags;
                if (tags) {
                    let tagImage = '/img/tags/' + (tags[0].id - 20) + '.jpg';
                    console.log(tagImage);
                    questions[i].tagImage = tagImage;
                }
            }
        },
        updateDuration: function () {
            let questions = this.questions;

            for (let i = 0; i < questions.length; i++) {
                let createtime = new Date(questions[i].gmtCreate).getTime();
                let now = new Date().getTime();
                let duration = now - createtime;

                if (duration < 1000 * 60) {
                    questions[i].duration = "剛剛";
                } else if (duration < 1000 * 60 * 60) {
                    questions[i].duration = (duration / 1000 / 60).toFixed(0) + "分鐘前";
                } else if (duration < 1000 * 60 * 60 * 24) {
                    questions[i].duration = (duration / 1000 / 60 / 60).toFixed(0) + "小時前";
                } else {
                    questions[i].duration = (duration / 1000 / 60 / 60 / 24).toFixed(0) + "天前";
                }
            }
        }
    },
    created: function () {
        console.log('開始加載Questions');
        this.loadQuestions(1);
    }
});
