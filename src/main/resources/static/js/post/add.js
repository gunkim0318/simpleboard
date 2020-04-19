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
                data : {
                    title : $('input[name=title]').val(),
                    content : $('textarea[name=title]').val(),
                    writer : $('input[name=writer]').val()
                },
                success : function(){
                    alert('성공!');
                },
                error : function(err, state, xhr){
                    console.log(err, state, xhr);
                }
            });
        });
    }
};
add.eventInit();