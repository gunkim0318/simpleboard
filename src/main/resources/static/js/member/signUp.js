var signUp = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            location.href="/";
        });
        $('#signUpBtn').on('click', function(){
            location.href="/member/signIn";
        });
    }
};
signUp.eventInit();