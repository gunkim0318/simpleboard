var list = {
    eventInit : function(){
        $('.pagination li a').on("click", function(e){
            e.preventDefault();

            var pageNum = $(this).attr('href');

            location.href="/?pageNum="+pageNum;
        });
        $('#addBtn').on('click', function(){
            location.href='/posts/add';
        });
    }
};

list.eventInit();