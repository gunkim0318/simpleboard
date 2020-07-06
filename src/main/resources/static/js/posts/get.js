var get = {
    showListPrint : function(postsId, pageNum){
        $.ajax({
            url: '/reply/api/'+postsId+'/'+pageNum,
            type: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            success: function(json){
                var list = json.list;
                var paging = json.paging;
                var sb = new StringBuffer();

                for(var i=0, item; item=list[i]; i++){
                    sb.append("<div class='replyItem'>");
                        sb.append("<div class='head'>"+item.writer+"<span>"+item.creDatetime+"</span></div>");
                        sb.append("<div class='body panel panel-primary'>");
                            sb.append("<div>"+item.content+"</div>");
                            sb.append('<div class="text-right" data-rno="'+item.rno+'">');
                                sb.append('<button id="replyUpdateBtn" class="btn btn-info">수정</button>');
                                sb.append('<button id="replyDeleteBtn" class="btn btn-danger">삭제</button>');
                            sb.append('</div>');
                        sb.append("</div>");
                    sb.append('</div>');
                }
                $('#replyList').html(sb.toString());

                sb.empty();

                util.printPagination('.pagination', paging);
            }
        });
    },
    eventInit : function() {
        var target = $('#actionForm');
        $('#backBtn').on('click', function(){
            history.back();
        });
        $('#modiBtn').on('click', function(){
            target.attr('action', '/posts/modify');
            target.attr('method', 'get');
            target.submit();
        });
        $('#delBtn').on('click', function(){
            target.attr('action', '/posts/delete');
            target.attr('method', 'post');
            target.submit();
        });
        $('#showReplyBtn').on('click', function(){
            var target = $("#replyBody");

            var isDisplay = target.css("display") !== "none"
            if (isDisplay){
                target.hide();
            }else{
                target.show();
                var postsId = $('#actionForm').find('input[name=postsNum]').val();
                get.showListPrint(postsId, 1);
            }
        });
        $('#replyAddBtn').on('click', function(){
            var postsId = target.find('input[name=postsNum]').val();
            var replyContent = $('#replyContent').val();
            $.ajax({
                url: '/reply/api/',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    'postsId' : postsId,
                    "content" : replyContent
                }),
                success: function(){
                    $('#replyContent').val('');
                    $.get('/reply/api/cnt/'+postsId,
                        function(data){
                            $('#showReplyBtn').html(data+" 댓글");
                        }
                    );
                    get.showListPrint(postsId, 1);
                },
                error: function(req){
                    var errorMsg = req.responseJSON.content;

                    if(errorMsg !== null){
                        alert(errorMsg);
                        $('#replyContent').focus();
                    }
                }
            });
        });
        $('.pagination').on('click', 'li a', function(e){
            e.preventDefault();
            var postsId = target.find('input[name=postsNum]').val();
            var pageNum = $(this).attr('href');
            get.showListPrint(postsId, pageNum);
        });
        $(document).on('click', '#replyUpdateBtn', function(){
            var rno = $(this).parent().data('rno');

            $.ajax({
                url : '/reply/api/'+rno,
                data : JSON.stringify({
                }),
                type : 'DELETE',
                contentType : 'application/json',
                success : function(result){
                    if(result === 'success'){

                    }
                },
                error : function(){
                    alert('댓글 수정에 실패하였습니다.');
                }
            });
        });
        $(document).on('click', '#replyDeleteBtn', function(){
            var rno = $(this).parent().data('rno');

            $.ajax({
                url : '/reply/api/'+rno,
                type : 'DELETE',
                contentType : 'application/json',
                success : function(result){
                    if(result === 'success'){
                        alert('댓글이 성공적으로 삭제되었습니다.');
                        var postsId = target.find('input[name=postsNum]').val();
                        get.showListPrint(postsId, 1);
                    }
                },
                error : function(){
                    alert('댓글 삭제에 실패하였습니다.');
                }
            });
        });
    }
};

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});
get.eventInit();