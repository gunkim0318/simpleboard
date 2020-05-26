var modify = {
    eventInit : function(){
        $('#backBtn').on('click', function(){
            history.back();
        });
    }
};

modify.eventInit();