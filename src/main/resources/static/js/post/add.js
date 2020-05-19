var add = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            history.back();
        });
        $('#addBtn').on('click', function(){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            var formData = new FormData();
            formData.append("title", $('input[name=title]').val());
            formData.append("content", $('textarea[name=content]').val());
            formData.append("file", $("input[name=uploadFile]")[0].files[0]);

            $.ajax({
                url : '/api/post/insert',
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                cache: false,
                beforeSend : function(xhr){
                    xhr.setRequestHeader(header, token);
               },
                success : function(){
                    alert('게시글 작성이 되었습니다.');
                    location.href='/';
                },
                error : function(err, state, xhr){
                    console.log(err, state, xhr);
                }
            });
        });
    }
};
add.eventInit();