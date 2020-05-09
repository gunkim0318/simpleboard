var modify = {
    eventInit : function(){
        $('#backBtn').on('click', function(){
            history.back();
        });
        $('#modiBtn').on('click', function(){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                url : '/api/post/update/'+$('input[name=id]').val(),
                type : 'put',
                contentType: 'application/json; charset=UTF-8',
                data : JSON.stringify({
                    title : $('input[name=title]').val(),
                    content : $('textarea[name=content]').val()
                }),
                beforeSend : function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success : function(){
                    alert('게시글이 수정되었습니다.');
                    location.href='/';
                },
                error : function(err, state, xhr){
                    console.log(err, state, xhr);
                }
            });
        });
    }
};

modify.eventInit();