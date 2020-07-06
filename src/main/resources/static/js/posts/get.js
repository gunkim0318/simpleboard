const postsId = $('#actionForm').find('input[name=postsNum]').val();
const get = {
    showListPrint: function (postsId, pageNum) {
        $.ajax({
            url: '/reply/api/' + postsId + '/' + pageNum,
            type: 'GET',
            contentType: 'application/json',
            dataType: 'json',
            success: function (json) {
                const list = json.list;
                const paging = json.paging;
                const sb = new StringBuffer();

                for (let i = 0, item; item = list[i]; i++) {
                    sb.append("<div class='replyItem' data-rno='" + item.rno + "'>");
                    sb.append("<div class='head'>" + item.writer + "<span>" + item.creDatetime + "</span></div>");
                    sb.append("<div class='body panel panel-primary'>");
                    sb.append("<div class='content'>" + item.content + "</div>");
                    sb.append("</div>");
                    sb.append('</div>');
                }
                $('#replyList').html(sb.toString());

                util.printPagination('.pagination', paging);
            }
        });
    },
    eventInit: function () {
        $('#backBtn').on('click', function () {
            history.back();
        });
        $('#modiBtn').on('click', function () {
            const target = $('#actionForm');
            target.attr('action', '/posts/modify');
            target.attr('method', 'get');
            target.submit();
        });
        $('#delBtn').on('click', function () {
            const target = $('#actionForm');
            target.attr('action', '/posts/delete');
            target.attr('method', 'post');
            target.submit();
        });
        $('#showReplyBtn').on('click', function () {
            var target = $("#replyBody");

            var isDisplay = target.css("display") !== "none"
            if (isDisplay) {
                target.hide();
            } else {
                target.show();
                get.showListPrint(postsId, 1);
            }
        });
        $('#replyAddBtn').on('click', function () {
            var replyContent = $('#replyContent').val();
            $.ajax({
                url: '/reply/api/',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    'postsId': postsId,
                    "content": replyContent
                }),
                success: function () {
                    alert('댓글이 작성되었습니다!');
                    $('#replyContent').val('');
                    $.get('/reply/api/cnt/' + postsId,
                        function (data) {
                            $('#showReplyBtn').html(data + " 댓글");
                        }
                    );
                    get.showListPrint(postsId, 1);
                },
                error: function (req) {
                    var errorMsg = req.responseJSON.content;

                    if (errorMsg !== null) {
                        alert(errorMsg);
                        $('#replyContent').focus();
                    }
                }
            });
        });
        $('.pagination').on('click', 'li a', function (e) {
            e.preventDefault();
            const pageNum = $(this).attr('href');
            get.showListPrint(postsId, pageNum);
        });
        $(document).on('click', '.replyItem', function () {
            const rno = $(this).data('rno');
            const replyContent = $(this).find('.content').html();

            const modalBody = $('.modal-body');
            modalBody.find('input').val(rno);
            modalBody.find('textarea').val(replyContent);

            $('#myModal').modal('show');
        });
        $('#replyUpdateBtn').on('click', function () {
            const modalBody = $('.modal-body');
            const rno = modalBody.find('input').val();
            const replyContent = modalBody.find('textarea').val();

            $.ajax({
                url: '/reply/api/' + rno,
                type: 'PUT',
                data: JSON.stringify({
                    content : replyContent
                }),
                contentType: 'application/json',
                success: function (result) {
                    if(result === 'success'){
                        alert('댓글 수정에 성공하였습니다!');

                        get.showListPrint(postsId, 1);
                        $.get('/reply/api/cnt/' + postsId,
                            function (data) {
                                $('#showReplyBtn').html(data + " 댓글");
                            }
                        );
                        $('#myModal').modal('hide');
                    }
                },
                error: function (req) {
                    var errorMsg = req.responseJSON.content;

                    if (errorMsg !== null) {
                        alert(errorMsg);
                        modalBody.find('textarea').focus();
                    }
                }
            });
        });
        $('#replyDeleteBtn').on('click', function () {
            var rno = $('.modal-body').find('input').val();
            $.ajax({
                url: '/reply/api/' + rno,
                type: 'DELETE',
                contentType: 'application/json',
                success: function (result) {
                    if (result === 'success') {
                        alert('댓글이 성공적으로 삭제되었습니다.');
                        get.showListPrint(postsId, 1);
                        $.get('/reply/api/cnt/' + postsId,
                            function (data) {
                                $('#showReplyBtn').html(data + " 댓글");
                            }
                        );
                        $('#myModal').modal('hide');
                    }
                },
                error: function () {
                    alert('댓글 삭제에 실패하였습니다.');
                }
            });
        });
    }
};

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function (e, xhr, options) {
    xhr.setRequestHeader(header, token);
});
get.eventInit();