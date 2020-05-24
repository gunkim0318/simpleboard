var get = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            history.back();
        });
        $('#modiBtn').on('click', function(){
            $('#modiForm').submit();
        });
    }
};
get.eventInit();