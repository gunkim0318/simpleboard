var add = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            history.back();
        });
    }
};
add.eventInit();