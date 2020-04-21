var get = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            history.back();
        });
    }
};
get.eventInit();