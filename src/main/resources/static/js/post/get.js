var get = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            history.back();
        });
        $('#modiBtn').on('click', function(){
            $('#modiForm').submit();
        });
        $('#delBtn').on('click', function(){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var postNum = $('input[name=postNum]').val();
            $.ajax({
                url : '/api/post/delete/'+postNum,
                type : 'delete',
                beforeSend : function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                success : function(){
                    alert('게시글 삭제되었습니다.');
                    location.href='/';
                },
                error : function(err, state, xhr){
                    console.log(err, state, xhr);
                }
            });
        });
    }
};
get.eventInit();