var get = {
    eventInit : function() {
        var target = $('#actionForm');
        $('#backBtn').on('click', function(){
            history.back();
        });
        $('#modiBtn').on('click', function(){
            target.attr('action', '/posts/modify');
            target.attr('method', 'get');
            target.submit();
        });
        $('#delBtn').on('click', function(){
            target.attr('action', '/posts/delete');
            target.attr('method', 'post');
            target.submit();
        });
    }
};
get.eventInit();