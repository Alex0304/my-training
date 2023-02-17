var options = {
		  currentPage: 6,
		  totalPages: 30,
		  numberOfPages:5,
 		 itemTexts: function (type, page, current) {
             switch (type) {
	             case "first":
	                 //return "首页";
	                 return "<<";
	             case "prev":
	                 //return "上一页";
	                 return "<";
	             case "next":
	                 //return "下一页";
	                 return ">";
	             case "last":
	                 //return "尾页";
	                 return ">>";
	             case "page":
	                 return page;
             }
         },
         tooltipTitles: function (type, page, current){
        	 switch (type) {
	             case "first":
	                 return "第一页";
	             case "prev":
	                 return "上一页";
	             case "next":
	                 return "下一页";
	             case "last":
	                 return "最后一页";
	             case "page":
	                 return "第" +page + "页";
         	}
         },
         onPageClicked: function(event, originalEvent, type,page){
        	 // alert(page);
         }
         
         
 		  
};