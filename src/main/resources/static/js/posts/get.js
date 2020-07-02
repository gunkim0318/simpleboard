var get = {
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
                $.ajax({
                    url: '/reply/api/'+postsId,
                    type: 'GET',
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(json){
                        var replyList = $('#replyList');
                        var tags = "";
                        for(var i=0, item; item=json[i]; i++){
                            var tag = "<div style=' width: 70%;'>"
                                tag += "<div style='font-weight: 800; font-size: 2rem;'>"+item.writer+"<span style='float:right;'>"+item.creDatetime+"</span></div>";
                                tag += "<div class='panel panel-primary' style='padding: 15px;'>"+item.content+"</div>";
                                tag +="</div>";

                            tags +=tag;
                        }
                        replyList.html(tags);
                    },
                    error: function(req, stat, err){
                        console.log(req, stat, err);
                    }
                });
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
                    $.ajax({
                        url: '/reply/api/'+postsId,
                        type: 'GET',
                        contentType: 'application/json',
                        dataType: 'json',
                        success: function(json){
                            var replyList = $('#replyList');
                            var tags = "";
                            for(var i=0, item; item=json[i]; i++){
                                var tag = "<div style=' width: 70%;'>"
                                tag += "<div style='font-weight: 800; font-size: 2rem;'>"+item.writer+"<span style='float:right;'>"+item.creDatetime+"</span></div>";
                                tag += "<div class='panel panel-primary' style='padding: 15px;'>"+item.content+"</div>";
                                tag +="</div>";

                                tags +=tag;
                            }
                            replyList.html(tags);
                        }
                    });
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