var add = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            history.back();
        });
        $('#addBtn').on('click', function(){
            $.ajax({
                url : '/api/post/insert',
                type : 'post',
                contentType: 'application/json; charset=UTF-8',
                data : JSON.stringify({
                    title : $('input[name=title]').val(),
                    content : $('textarea[name=content]').val(),
                    writer : $('input[name=writer]').val()
                }),
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