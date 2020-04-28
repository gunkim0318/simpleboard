var signUp = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            location.href="/";
        });
    }
};
signUp.eventInit();