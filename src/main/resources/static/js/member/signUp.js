var signUp = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            location.href="/";
        });
        $('#signUpBtn').on('click', function(){
            $('form').submit();
        });
    }
};
signUp.eventInit();