var signIn = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            location.href="/";
        });
        $('#signUpBtn').on('click', function(){
            location.href="/member/signUp";
        });
    }
};
signIn.eventInit();