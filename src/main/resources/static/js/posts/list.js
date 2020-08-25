var list = {
    eventInit : function(){
        $('.pagination').on("click", 'button', function(e){
            e.preventDefault();

            var pageNum = $(this).data("no");

            location.href="/?pageNum="+pageNum;
        });
        $('#addBtn').on('click', function(){
            location.href='/posts/add';
        });
    }
};
list.eventInit();