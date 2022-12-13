$(document).ready(function () {
    $('#summernote').summernote({
        height: 300,
        tabsize: 2,
        lang: 'zh-CN',
        placeholder: '请输入问题的详细描述...',
        callbacks: {
            onImageUpload: function (files) {
                let file = files[0];
                let form = new FormData();
                form.append("imageFile", file);
                console.log(file);
                $.ajax({
                    url: '/upload/image',
                    method: 'POST',
                    data: form,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (r) {
                        console.log(r);
                        if (r.code === OK) {
                            let img = new Image();
                            img.src = r.message;
                            $('#summernote').summernote("insertNode", img);
                        } else {
                            alert(r.message);
                        }
                    }
                });
            }
        }
    });
    $('select').select2({placeholder: '请选择...'});
});

Vue.component('v-select', VueSelect.VueSelect);
let createQuestionApp = new Vue({
    el: '#createQuestionApp',
    data: {
        title: '',
        tags: [],
        teachers: [],
        selectedTags: [],
        selectedTeachers: []
    },
    methods: {
        loadTags: function () {
            console.log("loadTags");
            $.ajax({
                url: '/v1/tags',
                method: 'GET',
                success: function (r) {
                    console.log(r);
                    if (r.code === OK) {
                        let tagNames = [];
                        let tags = r.data;
                        for (let i = 0; i < tags.length; i++) {
                            tagNames.push(tags[i].name);
                        }
                        console.log(tagNames);
                        createQuestionApp.tags = tagNames;
                    } else {
                        console.log('失敗');
                        console.log(r.message);
                    }
                }
            });
        },
        loadTeachers: function () {
            console.log("loadTeachers");
            $.ajax({
                url: '/v1/users/masters',
                method: 'GET',
                success: function (r) {
                    console.log(r);
                    if (r.code === OK) {
                        let list = r.data;
                        let teachers = [];
                        for (let i = 0; i < list.length; i++) {
                            teachers.push(list[i].nickName);
                        }
                        console.log(teachers);
                        createQuestionApp.teachers = teachers;
                    } else {
                        console.log("加載失敗");
                        console.log(r.message);
                    }
                }
            });
        },
        createQuestion: function () {
            let content = $('#summernote').val();
            console.log(content);
            //data 对象，与服务器端QuestionVo的属性对应
            let data = {
                title: this.title,
                tagNames: this.selectedTags,
                teacherNicknames: this.selectedTeachers,
                content: content
            };
            console.log(data);
            $.ajax({
                url: '/v1/questions',
                traditional: true,  //采用传统数组编码方式，SpringMVC才能接收
                method: 'POST',
                data: data,
                success: function (r) {
                    console.log(r);
                    if (r.code === OK) {
                        console.log(r.message);
                        location.href = '../index.html';
                    } else {
                        console.log(r.message);
                    }
                }
            });
        }
    },
    created: function () {
        this.loadTags();
        this.loadTeachers();
    }
});