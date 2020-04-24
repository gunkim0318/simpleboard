var signUp = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            history.back();
        });
    }
};
signUp.eventInit();