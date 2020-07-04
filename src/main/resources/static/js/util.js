var StringBuffer = function(){
    this.buffer = new Array();
}
StringBuffer.prototype.append = function(str){
    this.buffer[this.buffer.length] = str;
}
StringBuffer.prototype.toString = function(){
    return this.buffer.join("");
}
StringBuffer.prototype.empty = function(){
    this.buffer = new Array();
}

var util = {
    printPagination : function(selector, paging){
        var target = $(selector);

        var sb = new StringBuffer();

        if(paging.prev){
            sb.append('<li><a href="'+paging.prevPageNum+'" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
        }
        for(var i = 0, item; item = paging.pagingList[i]; i++){
            sb.append('<li class="'+item.active+'"><a href="'+item.idx+'">'+item.idx+'</a></li>');
        }
        if(paging.next){
            sb.append('<li><a href="'+paging.nextPageNum+'" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
        }
        target.html(sb.toString());
    }
};