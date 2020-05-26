var add = {
    eventInit : function() {
        $('#backBtn').on('click', function(){
            history.back();
        });
        $('#addForm').submit(function(e){
            var inTitle = $(this).find('input[name=title]');
            var inContent = $(this).find('textarea[name=content]');

            var isEmptyTitle = $.trim(inTitle.val()) === "";
            var isEmptyContent = $.trim(inContent.val()) === "";

            if(isEmptyTitle || isEmptyContent){
                if(isEmptyTitle){
                    inTitle.focus();
                    alert("제목을 입력해주세요.");
                }else if(isEmptyContent){
                    inContent.focus();
                    alert("내용을 입력해주세요.");
                }
                e.preventDefault();
            }
        });
    }
};
add.eventInit();