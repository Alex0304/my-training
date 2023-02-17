/**
 * bootstrap-paginator.js v0.5
 * --
 * Copyright 2013 Yun Lai <lyonlai1984@gmail.com>
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

(function ($) {


        var numberData='';//获取每页条数
        var totalSize='';
    "use strict"; // jshint ;_;


    /* Paginator PUBLIC CLASS DEFINITION
     * ================================= */

    /**
     * Boostrap Paginator Constructor
     *
     * @param element element of the paginator
     * @param options the options to config the paginator
     *
     * */
    var BootstrapPaginator = function (element, options) {
        this.init(element, options);
    },
        old = null;


    BootstrapPaginator.prototype = {

        /**
         * Initialization function of the paginator, accepting an element and the options as parameters
         *
         * @param element element of the paginator
         * @param options the options to config the paginator
         *
         * */
        init: function (element, options) {

            this.$element = $(element);

            var version = (options && options.bootstrapMajorVersion) ? options.bootstrapMajorVersion : $.fn.bootstrapPaginator.defaults.bootstrapMajorVersion,
                id = this.$element.attr("id");

            if (version === 2 && !this.$element.is("div")) {

                throw "in Bootstrap version 2 the pagination must be a div element. Or if you are using Bootstrap pagination 3. Please specify it in bootstrapMajorVersion in the option";
            } else if (version > 2 && !this.$element.is("ul")) {
                throw "in Bootstrap version 3 the pagination root item must be an ul element."
            }



            this.currentPage = 1;

            this.lastPage = 1;

            this.setOptions(options);

            this.initialized = true;
        },

        /**
         * Update the properties of the paginator element
         *
         * @param options options to config the paginator
         * */
        setOptions: function (options) {

            this.options = $.extend({}, (this.options || $.fn.bootstrapPaginator.defaults), options);

            this.totalPages = parseInt(this.options.totalPages, 10);  //setup the total pages property.
            this.numberOfPages = parseInt(this.options.numberOfPages, 10); //setup the numberOfPages to be shown

            //move the set current page after the setting of total pages. otherwise it will cause out of page exception.
            if (options && typeof (options.currentPage)  !== 'undefined') {
                this.setCurrentPage(options.currentPage);
            }
            this.listen();

            //render the paginator
            this.render();
            
            if (!this.initialized && this.lastPage !== this.currentPage) {
                this.$element.trigger("page-changed", [this.lastPage, this.currentPage]);

            }

        },

        /**
         * Sets up the events listeners. Currently the pageclicked and pagechanged events are linked if available.
         *
         * */
        listen: function () {

            this.$element.off("page-clicked");

            this.$element.off("page-changed");// unload the events for the element

            if (typeof (this.options.onPageClicked) === "function") {
                this.$element.bind("page-clicked", this.options.onPageClicked);
            }

            if (typeof (this.options.onPageChanged) === "function") {
                this.$element.on("page-changed", this.options.onPageChanged);
            }

            this.$element.bind("page-clicked", this.onPageClicked);
        },


        /**
         *
         *  Destroys the paginator element, it unload the event first, then empty the content inside.
         *
         * */
        destroy: function () {

            this.$element.off("page-clicked");

            this.$element.off("page-changed");

            this.$element.removeData('bootstrapPaginator');

            this.$element.empty();

        },

        /**
         * Shows the page
         *
         * */
        show: function (page) {

            this.setCurrentPage(page);

            this.render();

            if (this.lastPage !== this.currentPage) {
                this.$element.trigger("page-changed", [this.lastPage, this.currentPage]);


            }
        },

        /**
         * Shows the next page
         *
         * */
        showNext: function () {
            var pages = this.getPages();

            if (pages.next) {
                this.show(pages.next);
            }

        },

        /**
         * Shows the previous page
         *
         * */
        showPrevious: function () {
            var pages = this.getPages();

            if (pages.prev) {
                this.show(pages.prev);
            }

        },

        /**
         * Shows the first page
         *
         * */
        showFirst: function () {
            var pages = this.getPages();

            if (pages.first) {
                this.show(pages.first);
            }

        },

        /**
         * Shows the last page
         *
         * */
        showLast: function () {
            var pages = this.getPages();

            if (pages.last) {
                this.show(pages.last);
            }

        },

        /**
         * Internal on page item click handler, when the page item is clicked, change the current page to the corresponding page and
         * trigger the pageclick event for the listeners.
         *
         *
         * */
        onPageItemClicked: function (event) {

            var type = event.data.type,
                page = event.data.page;

            this.$element.trigger("page-clicked", [event, type, page]);

        },

        onPageClicked: function (event, originalEvent, type, page) {

            //show the corresponding page and retrieve the newly built item related to the page clicked before for the event return

            var currentTarget = $(event.currentTarget);

            switch (type) {
                case "first":
                currentTarget.bootstrapPaginator("showFirst");
                break;
            case "prev":
                currentTarget.bootstrapPaginator("showPrevious");
                break;
            case "next":
                currentTarget.bootstrapPaginator("showNext");
                break;
            case "last":
                currentTarget.bootstrapPaginator("showLast");
                break;
            case "page":
                currentTarget.bootstrapPaginator("show", page);
                break;
            }

        },
        
        onPageSelected: function (event) {
        },

        /**
         * Renders the paginator according to the internal properties and the settings.
         *
         *
         * */
        render: function () {
            //fetch the container class and add them to the container
            var containerClass = this.getValueFromOption(this.options.containerClass, this.$element),
                size = this.options.size || "normal",
                alignment = this.options.alignment || "left",
                pages = this.getPages(),
                listContainer = this.options.bootstrapMajorVersion === 2 ? $("<ul></ul>") : this.$element,
                listContainerClass = this.options.bootstrapMajorVersion === 2 ? this.getValueFromOption(this.options.listContainerClass, listContainer) : null,
                first = null,
                prev = null,
                next = null,
                last = null,
                p = null,
                i = 0;
            this.$element.prop("class", "");

            this.$element.addClass("pagination");

            switch (size.toLowerCase()) {
            case "large":
            case "small":
            case "mini":
                this.$element.addClass($.fn.bootstrapPaginator.sizeArray[this.options.bootstrapMajorVersion][size.toLowerCase()]);
                break;
            default:
                break;
            }
            if (this.options.bootstrapMajorVersion === 2) {
                switch (alignment.toLowerCase()) {
                case "center":
                    this.$element.addClass("pagination-centered");
                    break;
                case "right":
                    this.$element.addClass("pagination-right");
                    break;
                default:
                    break;
                }
            }


            this.$element.addClass(containerClass);

            //empty the outter most container then add the listContainer inside.
            this.$element.empty();

            if (this.options.bootstrapMajorVersion === 2) {
                this.$element.append(listContainer);

                listContainer.addClass(listContainerClass);
            }

            //update the page element reference
            this.pageRef = [];
            var goPage = this.buildPageItem("goPage", "");
            if(!this.options.newType)listContainer.append(goPage);
           // 2016.10.19 liming
            var xianshipage = this.buildPageItemPage("xianshipage", "");
            xianshipage.addClass('xianshipage');
            listContainer.append(xianshipage);
            if(this.options.newType){
                if (pages.prev) {//if the there is previous page element

                    prev = this.buildPageItem("prev", pages.prev);

                    if (prev) {
                        listContainer.append(prev);
                    }

                }
                if (pages.next) {//if there is next page

                    next = this.buildPageItem("next", pages.next);

                    if (next) {
                        listContainer.append(next);
                    }
                }
            }else{
                if (pages.first) {//if the there is first page element
                    first = this.buildPageItem("first", pages.first);
                    if (first) {
                        listContainer.append(first);
                    }
                }
                if (pages.prev) {//if the there is previous page element

                    prev = this.buildPageItem("prev", pages.prev);

                    if (prev) {
                        listContainer.append(prev);
                    }

                }


                /* for (i = 0; i < pages.length; i = i + 1) {//fill the numeric pages.

                 p = this.buildPageItem("page", pages[i]);

                 if (p) {
                 listContainer.append(p);
                 }
                 }*/


                if (pages.next) {//if there is next page

                    next = this.buildPageItem("next", pages.next);

                    if (next) {
                        listContainer.append(next);
                    }
                }

                if (pages.last) {//if there is last page

                    last = this.buildPageItem("last", pages.last);

                    if (last) {
                        listContainer.append(last);
                    }
                }
                var xianshi = this.buildPageItem("page", this.currentPage+"/"+this.totalPages);
                // pagestart=this.currentPage;

                if (xianshi) {
                    listContainer.append(xianshi);
                }
                if(this.totalPages != 1 && this.options.goOffPage == true){
                    var jumpPage = this.buildPageItem("jumpPage", "");
                    listContainer.append(jumpPage);
                }
            }
        },

        /**
         *
         * Creates a page item base on the type and page number given.
         *
         * @param page page number
         * @param type type of the page, whether it is the first, prev, page, next, last
         *
         * @return Object the constructed page element
         * */
        buildPageItemPage: function (type, page) {
            var itemContainer = $("<li></li>"),//creates the item container
                itemContent = $("<span id='cursor' style='cursor: default;color: #444444'></span>"),//creates the item content
                text = "",
                title = "",
                itemContainerClass = this.options.itemContainerClass(type, page, this.currentPage),
                itemContainerxianshiClass = this.options.itemContainerxianshiClass(type, page, this.currentPage),//liming
                itemContentClass = this.getValueFromOption(this.options.itemContentClass, type, page, this.currentPage),
                tooltipOpts = null;
            $('.disabled').eq(0).find("a").css("borderBottomLeftRadius","4px");
            $('.disabled').eq(0).find("a").css("borderTopLeftRadius","4px");
            switch (type) {

                case "first":
                    //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                    text = this.options.itemTexts(type, page, this.currentPage);
                    title = this.options.tooltipTitles(type, page, this.currentPage);

                    break;
                case "last":
                    //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                    text = this.options.itemTexts(type, page, this.currentPage);
                    title = this.options.tooltipTitles(type, page, this.currentPage);
                    break;
                case "prev":
                    //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                    text = this.options.itemTexts(type, page, this.currentPage);
                    title = this.options.tooltipTitles(type, page, this.currentPage);
                    break;
                case "next":
                    //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                    text = this.options.itemTexts(type, page, this.currentPage);
                    title = this.options.tooltipTitles(type, page, this.currentPage);
                    // pagestart=parseInt(this.currentPage);
                    break;
                case "page":
                    //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                    text = this.options.itemTexts(type, page, this.currentPage);
                    title = this.options.tooltipTitles(type, page, this.currentPage);
                    break;
                case "goPage":
                    //edit at 2015.6.3 by fy with textTemp's span(className)
                    //var textTemp = "<span class='myj-pageSpan'>每页<div class='myj-pageDiv'></div><select name='pageselct' style='cursor: pointer' >";
                    var textTemp ="<span style='color:#444444;'>每页</span>&nbsp;&nbsp;<select name='pageselct' style='cursor: pointer;margin-top:-1px;' >";
                    for(var i = 0,num=(this.options.numberOfPagesArr).length;i<num;i++){
                        if(parseInt(this.options.numberOfPages, 10) === parseInt(this.options.numberOfPagesArr[i], 10)){
                            textTemp = textTemp + "<option selected='selected' value='"+parseInt(this.options.numberOfPagesArr[i], 10)+"'>"+parseInt(this.options.numberOfPagesArr[i], 10)+"条</option>";
                            numberData=parseInt(this.options.numberOfPagesArr[i], 10);//liming 获取每页多少条数据
                        }else{
                            textTemp = textTemp + "<option value='"+parseInt(this.options.numberOfPagesArr[i], 10)+"'>"+parseInt(this.options.numberOfPagesArr[i], 10)+"条</option>";

                        }
                    }
                    textTemp = textTemp + "</select>&nbsp;<span style='color:#444444;'></span></span>";
                    text = $(textTemp);
                    // console.log(title)
                    break;
                case 'xianshipage':
                    var pagestartnum=this.currentPage;
                    var pagestart=this.currentPage;
                        totalSize=this.options.totalSize;
                    var oarrdate=pagestartnum*numberData;
                    if (pagestart==1){
                        if (totalSize==0){
                            pagestart=0
                        }else {
                            pagestart=1
                        }

                    }else {
                        pagestart=numberData*(pagestart-1)+1
                    }
                    if (oarrdate>totalSize){
                        oarrdate=parseInt(totalSize)
                    }else {
                        oarrdate=pagestartnum*numberData
                    };


                    // letter-spacing和文本间隔 word-spacing
                    // var textTemp='<li style="float: left;width: 118px;height:32px;font-size: 13px;line-height: 32px;background-color: #00a0e9"><span id="listyuyuy">'+'第'+pagestart+'-'+oarrdate+'条'+'</span>&nbsp;&nbsp;<span id="arrList">'+'共'+totalSize+'条'+'</span></li>'
                    var sss = this.options.itemTexts(type, page, this.currentPage);
                    var textTemp='';
                    if (typeof(totalSize)=='undefined'){
                           textTemp=''
                    }else {
                        // var textTemp ='<span style=\'width: 145px;height:12px;display: inline-block;text-align:left; position: absolute;right: 0px\'>'+'第'+pagestart+'-'+oarrdate+'条'+'，'+'共'+totalSize+'条'+'</span>';
                        var textTemp ='第'+pagestart+'-'+oarrdate+'条'+'，'+'共'+totalSize+'条';
                       };
                    text =textTemp;
                    break;
                case "jumpPage":
                    if(this.totalPages == 1){
                        break;
                    }else{
                        //var textTemp = "<button name='goPageName' style='padding: 0 6px;line-height: 14px;font-size: 12px;background-color: #fff;border: 1px solid #fff;'>Go》</button>";
                        var textTemp = "<button name='goPageName' type='button' class='btn btn-primary' style='padding: 6px 12px;line-height: 18px;font-size: 13px;margin-left:5px;'>确定</button>";
                        text = $(textTemp);
                        break;
                    }
            }
            itemContainer.addClass(itemContainerClass).append(itemContent);
            if( type==="goPage"){
                    itemContent.addClass(itemContentClass).append(text);
                    var selFunction = this.options.onPageSelected;
                    var optionThat = this;
                    text.on("change",function(event){
                        selFunction(event);
                    });       
               
            }else if(type === "xianshipage"){
                itemContent.addClass(itemContainerxianshiClass).append(text);
                itemContent.css({"border":"0px","hover":"default"});

            }else if(type === "jumpPage" && this.options.goOffPage == true){
                if(this.totalPages != 1){
                    /*var qita = "<span style='color:#444444;'>跳转到</span>&nbsp;";
                     var qita1 =	$("<input type='text' style='width: 40px;height: 18px;border-radius: 4px;border: 1px solid #ccc;padding: 0 6px;font-size: 12px;' zdyPageInput='zdy' value='' />");
                     var qita2 = "&nbsp;<span style='color:#444444;'>页</span>";
                     itemContent.addClass(itemContentClass).append(qita).append(qita1).append(qita2).append(text);*/
                    //edit at 2016.10.10 修改跳转的显示样式 fuyi
                    var qita ="<a class='dropdown' href='javascript:' data-toggle='dropdown'>跳转</a>" +
                        "<ul class='dropdown-menu pull-right' style='top:75%' role='menu'>" +
                        "<li style='padding:12px 24px;width:240px;'>跳转到<input type='text' class='tup' style='width: 50px;height: 32px;border-radius: 4px;border: 1px solid #ccc;padding:6px;font-size: 12px;margin:0 5px;' zdyPageInput='zdy' value='' />页" +
                        "</li></ul>";
                    var qitaObj = $(qita);
                    var qita1 =	qitaObj.find("input[zdyPageInput='zdy']");
                    qitaObj.find('span.iptObj').append(qita1);
                    qitaObj.find('li:first').append(text);
                    itemContent.addClass(itemContentClass).addClass('dropdown').append(qitaObj);
                    //edit end
                    var jumpFunction = this.options.onPageJump;
                    text.on("click",function(event){
                        jumpFunction(event);
                        //event..stopPropagation();
                        //jumpFunction($(this).closest('li'));
                    });
                    qita1.on("keyup",function(event){//edit at 2016.10.10 添加回车事件 fuyi
                        var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
                        //console.log(keyCode);
                        if (keyCode == 13) {
                            $(this).closest('li').find('.btn').click();
                        }else{
                            if(/[^0-9]/g.test($(this).val())){//替换非数字字符
                                var temp_amount=$(this).val().replace(/[^0-9]/g,'');
                                $(this).val(temp_amount);
                            }
                        }
                    });
                }
            }else if(type === "page"){
                itemContent.addClass(itemContentClass).html(text);
            }else{
                if(parseInt(this.options.currentPage, 10) === parseInt(page, 10)){
                    itemContent.addClass(itemContentClass).html(text);
                }else{
                    itemContent.addClass(itemContentClass).html(text).on("click", null, {type: type, page: page}, $.proxy(this.onPageItemClicked, this));
                }
            }

            if (this.options.pageUrl) {
                itemContent.attr("href", this.getValueFromOption(this.options.pageUrl, type, page, this.currentPage));
            }

            if (this.options.useBootstrapTooltip) {
                tooltipOpts = $.extend({}, this.options.bootstrapTooltipOptions, {title: title});
                itemContent.tooltip(tooltipOpts);
            } else {
                itemContent.attr("title", title);
            }

            return itemContainer;

        },//显示第1-100条，共340条
        buildPageItem: function (type, page) {
            var itemContainer = $("<li></li>"),//creates the item container
                itemContent = $("<a></a>"),//creates the item content
                text = "",
                title = "",
                itemContainerClass = this.options.itemContainerClass(type, page, this.currentPage),
                itemContainerxianshiClass = this.options.itemContainerxianshiClass(type, page, this.currentPage),//liming
                itemContentClass = this.getValueFromOption(this.options.itemContentClass, type, page, this.currentPage),
                tooltipOpts = null;
            $('#downPage .disabled').eq(0).find("a").css("borderBottomLeftRadius","4px");
            $('#downPage .disabled').eq(0).find("a").css("borderTopLeftRadius","4px");

            switch (type) {

            case "first":
                //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                text = this.options.itemTexts(type, page, this.currentPage);
                title = this.options.tooltipTitles(type, page, this.currentPage);

                break;
            case "last":
                //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                text = this.options.itemTexts(type, page, this.currentPage);
                title = this.options.tooltipTitles(type, page, this.currentPage);
                break;
            case "prev":
                //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                text = this.options.itemTexts(type, page, this.currentPage);
                title = this.options.tooltipTitles(type, page, this.currentPage);

                break;
            case "next":
                //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                text = this.options.itemTexts(type, page, this.currentPage);
                title = this.options.tooltipTitles(type, page, this.currentPage);
                // pagestart=parseInt(this.currentPage);
                break;
            case "page":
                //if (!this.getValueFromOption(this.options.shouldShowPage, type, page, this.currentPage)) { return; }
                text = this.options.itemTexts(type, page, this.currentPage);
                title = this.options.tooltipTitles(type, page, this.currentPage);

                break;
            case "goPage":
                    //edit at 2015.6.3 by fy with textTemp's span(className)
                    //var textTemp = "<span class='myj-pageSpan'>每页<div class='myj-pageDiv'></div><select name='pageselct' style='cursor: pointer' >";
                    var textTemp ="<span style='color:#444444;'>每页</span>&nbsp;&nbsp;<select name='pageselct' style='cursor: pointer;margin-top:-1px;' >";
                    for(var i = 0,num=(this.options.numberOfPagesArr).length;i<num;i++){
                        if(parseInt(this.options.numberOfPages, 10) === parseInt(this.options.numberOfPagesArr[i], 10)){
                            textTemp = textTemp + "<option selected='selected' value='"+parseInt(this.options.numberOfPagesArr[i], 10)+"'>"+parseInt(this.options.numberOfPagesArr[i], 10)+"条</option>";
                            numberData=parseInt(this.options.numberOfPagesArr[i], 10);//liming 获取每页多少条数据
                        }else{
                            textTemp = textTemp + "<option value='"+parseInt(this.options.numberOfPagesArr[i], 10)+"'>"+parseInt(this.options.numberOfPagesArr[i], 10)+"条</option>";

                        }
                    }
                    textTemp = textTemp + "</select>&nbsp;<span style='color:#444444;'></span></span>";
                    text = $(textTemp);
                    break;
            case "jumpPage":
            	if(this.totalPages == 1){
                    break;
            	}else{
            		//var textTemp = "<button name='goPageName' style='padding: 0 6px;line-height: 14px;font-size: 12px;background-color: #fff;border: 1px solid #fff;'>Go》</button>";
            		var textTemp = "<button name='goPageName' type='button' class='btn btn-primary' style='padding: 6px 12px;line-height: 18px;font-size: 13px;margin-left:5px;'>确定</button>";
                	text = $(textTemp);
                    break;
            	}
            }
            itemContainer.addClass(itemContainerClass).append(itemContent);
            // if(type==="xianshipage"){
            //     itemContent.addClass(itemContentClass).append(text);
            // }
            if( type==="goPage"){
                    itemContent.addClass(itemContentClass).append(text);
                    var selFunction = this.options.onPageSelected;
                    var optionThat = this;
                    text.on("change",function(event){
                        selFunction(event);
                    });
            }else if(type === "xianshipage"){
                itemContent.addClass(itemContainerxianshiClass).append(text);
                itemContent.css({color:'#444444'})
            }else if(type === "jumpPage" && this.options.goOffPage == true){
            	if(this.totalPages != 1){
            		/*var qita = "<span style='color:#444444;'>跳转到</span>&nbsp;";
            		var qita1 =	$("<input type='text' style='width: 40px;height: 18px;border-radius: 4px;border: 1px solid #ccc;padding: 0 6px;font-size: 12px;' zdyPageInput='zdy' value='' />");
            		var qita2 = "&nbsp;<span style='color:#444444;'>页</span>";
            		itemContent.addClass(itemContentClass).append(qita).append(qita1).append(qita2).append(text);*/
                    //edit at 2016.10.10 修改跳转的显示样式 fuyi
                    var qita ="<a class='dropdown' href='javascript:' data-toggle='dropdown'>跳转</a>" +
                        "<ul class='dropdown-menu pull-right' style='top:75%' role='menu'>" +
                        "<li style='padding:12px 24px;width:240px;'>跳转到<input type='text' class='tup' style='width: 50px;height: 32px;border-radius: 4px;border: 1px solid #ccc;padding:6px;font-size: 12px;margin:0 5px;' zdyPageInput='zdy' value='' />页" +
                        "</li></ul>";
                    var qitaObj = $(qita);
            		var qita1 =	qitaObj.find("input[zdyPageInput='zdy']");
                    qitaObj.find('span.iptObj').append(qita1);
                    qitaObj.find('li:first').append(text);
                        // 2016 10.19 添加input focus()
                    $('.dropdown').mouseover(function () {
                        $('.tup').focus()
                    });
                    itemContent.addClass(itemContentClass).addClass('dropdown').append(qitaObj);
                    //edit end
                	var jumpFunction = this.options.onPageJump;
                	text.on("click",function(event){
                		jumpFunction(event);
                        //event..stopPropagation();
                		//jumpFunction($(this).closest('li'));
                	});
                         qita1.on("keyup",function(event){//edit at 2016.10.10 添加回车事件 fuyi
                        var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
                        //console.log(keyCode);
                        if (keyCode == 13) {
                            $(this).closest('li').find('.btn').click();
                        }else{
                            if(/[^0-9]/g.test($(this).val())){//替换非数字字符
                                var temp_amount=$(this).val().replace(/[^0-9]/g,'');
                                $(this).val(temp_amount);
                             }
                        }
                	});
            	}
            }else if(type === "page"){
            	itemContent.addClass(itemContentClass).html(text);
            }else{
                if(parseInt(this.options.currentPage, 10) === parseInt(page, 10)){
                	itemContent.addClass(itemContentClass).html(text);
                }else{
                	itemContent.addClass(itemContentClass).html(text).on("click", null, {type: type, page: page}, $.proxy(this.onPageItemClicked, this));
                }
            }

            if (this.options.pageUrl) {
                itemContent.attr("href", this.getValueFromOption(this.options.pageUrl, type, page, this.currentPage));
            }

            if (this.options.useBootstrapTooltip) {
                tooltipOpts = $.extend({}, this.options.bootstrapTooltipOptions, {title: title});
                itemContent.tooltip(tooltipOpts);
            } else {
                itemContent.attr("title", title);
            }

            return itemContainer;

        },
        setCurrentPage: function (page) {
            if (page > this.totalPages || page < 1) {// if the current page is out of range, throw exception.

                throw "Page out of range";

            }

            this.lastPage = this.currentPage;

            this.currentPage = parseInt(page, 10);

        },
        /**
         * Gets an array that represents the current status of the page object. Numeric pages can be access via array mode. length attributes describes how many numeric pages are there. First, previous, next and last page can be accessed via attributes first, prev, next and last. Current attribute marks the current page within the pages.
         *
         * @return object output objects that has first, prev, next, last and also the number of pages in between.
         * */
        getPages: function () {

            var totalPages = this.totalPages,// get or calculate the total pages via the total records
                pageStart = (this.currentPage % this.numberOfPages === 0) ? (parseInt(this.currentPage / this.numberOfPages, 10) - 1) * this.numberOfPages + 1 : parseInt(this.currentPage / this.numberOfPages, 10) * this.numberOfPages + 1,//calculates the start page.
                output = [],
                i = 0,
                counter = 0;

            pageStart = pageStart < 1 ? 1 : pageStart;//check the range of the page start to see if its less than 1.

            for (i = pageStart, counter = 0; counter < this.numberOfPages && i <= totalPages; i = i + 1, counter = counter + 1) {//fill the pages
                output.push(i);
            }

            output.first = 1;//add the first when the current page leaves the 1st page.

            if (this.currentPage > 1) {// add the previous when the current page leaves the 1st page
                output.prev = this.currentPage - 1;
            } else {
                output.prev = 1;
            }

            if (this.currentPage < totalPages) {// add the next page when the current page doesn't reach the last page
                output.next = this.currentPage + 1;
            } else {
                output.next = totalPages;
            }

            output.last = totalPages;// add the last page when the current page doesn't reach the last page

            output.current = this.currentPage;//mark the current page.

            output.total = totalPages;

            output.numberOfPages = this.options.numberOfPages;

            return output;

        },

        /**
         * Gets the value from the options, this is made to handle the situation where value is the return value of a function.
         *
         * @return mixed value that depends on the type of parameters, if the given parameter is a function, then the evaluated result is returned. Otherwise the parameter itself will get returned.
         * */
        getValueFromOption: function (value) {
            var output = null,
                args = Array.prototype.slice.call(arguments, 1);

            if (typeof value === 'function') {
                output = value.apply(this, args);
            } else {
                output = value;
            }

            return output;

        }

    };
    /* TYPEAHEAD PLUGIN DEFINITION
     * =========================== */

    old = $.fn.bootstrapPaginator;

    $.fn.bootstrapPaginator = function (option) {
        var args = arguments,
            result = null;

        $(this).each(function (index, item) {
            var $this = $(item),
                data = $this.data('bootstrapPaginator'),
                options = (typeof option !== 'object') ? null : option;

            if (!data) {
                data = new BootstrapPaginator(this, options);

                $this = $(data.$element);

                $this.data('bootstrapPaginator', data);
                return;
            }
            if (typeof option === 'string') {
                if (data[option]) {
                    result = data[option].apply(data, Array.prototype.slice.call(args, 1));
                } else {
                    throw "Method " + option + " does not exist";
                }

            } else {
                result = data.setOptions(option);
            }
        });

        return result;

    };

    $.fn.bootstrapPaginator.sizeArray = {

        "2": {
            "large": "pagination-large",
            "small": "pagination-small",
            "mini": "pagination-mini"
        },
        "3": {
            "large": "pagination-lg",
            "small": "pagination-sm",
            "mini": ""
        }

    };

    $.fn.bootstrapPaginator.defaults = {
        containerClass: "",
        size: "normal",
        alignment: "left",
        bootstrapMajorVersion: 3,
        listContainerClass: "",
        itemContainerClass: function (type, page, current) {
            return (page === current) ? "disabled" : ""; //��ʽ����
        },
        itemContentClass: function (type, page, current) {
            return "";
        },
        itemContainerxianshiClass: function (type, page, current) {
            return "";
        },
        currentPage: 1,
        numberOfPages: 5,
        totalPages: 1,
        numberOfPagesArr: [30,50,100],
        goOffPage : false,
        pageUrl: function (type, page, current) {
            return null;
        },
        onPageClicked: null,
        onPageChanged: null,
        onPageSelected:null,
        useBootstrapTooltip: false,
        shouldShowPage: function (type, page, current) {

            var result = true;

            switch (type) {
            case "first":
                result = (current !== 1);
                break;
            case "prev":
                result = (current !== 1);
                break;
            case "next":
                result = (current !== this.totalPages);
                break;
            case "last":
                result = (current !== this.totalPages);
                break;
            case "page":
                result = true;
                break;
            }

            return result;

        },
        itemTexts: function (type, page, current) {
            switch (type) {
            case "first":
                return "&lt;&lt;";
            case "prev":
                return "&lt;";
            case "next":
                return "&gt;";
            case "last":
                return "&gt;&gt;";
            case "page":
                return page;
            }
        },
        tooltipTitles: function (type, page, current) {

            switch (type) {
            case "first":
                return "Go to first page";
            case "prev":
                return "Go to previous page";
            case "next":
                return "Go to next page";
            case "last":
                return "Go to last page";
            case "page":
                return (page === current) ? "Current page is " + page : "Go to page " + page;
            }
        },
        bootstrapTooltipOptions: {
            animation: true,
            html: true,
            placement: 'top',
            selector: false,
            title: "",
            container: false
        }
    };
    $.fn.bootstrapPaginator.Constructor = BootstrapPaginator;
}(window.jQuery));
