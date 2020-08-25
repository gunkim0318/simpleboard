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
            sb.append(`<button data-no="${paging.prevPageNum}" class="mui-btn mui-btn--fab mui-btn--primary">&laquo;</button>`);
        }
        for(var i = 0, item; item = paging.pagingList[i]; i++){
            sb.append(`<button data-no="${item.idx}" class="mui-btn mui-btn--fab" ${item.active==='active'?'disabled':''}>${item.idx}</button>`);
        }
        if(paging.next){
            sb.append(`<button data-no="${paging.nextPageNum}" class="mui-btn mui-btn--fab mui-btn--primary">&raquo;</button>`);
        }
        target.html(sb.toString());
    }
};