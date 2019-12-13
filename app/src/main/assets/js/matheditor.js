function MathEditor(id){
    this.isMobile = true;
    this.MQ=null;
    jq = window.jQuery;
    this.tabEnabled = true;
    this.answerMathField = ((typeof this.answerMathField != 'undefined')? this.answerMathField : {});
    this.answerSpan = ((typeof this.answerSpan != 'undefined')? this.answerSpan : {});
    this.topElements = {
        wrapper: null,
        toolbar: null,
        buttons: null
    }
    mathed_tmp = ((typeof mathed_tmp != 'undefined')? mathed_tmp : {});
    this.template = 'default';
    this.default_toolbar_buttons = default_toolbar_buttons_array;
    this.default_toolbar_tabs = ["General","Symbols","Trigonometry"];
    button_meta = toolbar_button_latex_definitions;
keyboard_keys = keyboard_buttons;
    this.MQ = MathQuill.getInterface(2);
    this.answerSpan = document.getElementById(id);
    var config = {
        handlers: {
          edit: function() {},
          enter: function() {},
        }
    };
    this.answerMathField= this.MQ.MathField(this.answerSpan, config);
    setToolbar(this.default_toolbar_buttons,this.answerSpan,this.answerMathField,this.topElements,this.default_toolbar_tabs,this.tabEnabled,this.isMobile);
    // basicStyling(this.answerSpan,this.topElements);
}

MathEditor.prototype.getValue = function(){
    return this.answerMathField.latex();
};

MathEditor.prototype.getLatex = function(){
    return this.answerMathField.latex();
};

MathEditor.prototype.setLatex = function(latex){
    this.answerMathField.latex(latex);  
};

MathEditor.prototype.getPrintableValue = function(){
    return "$$"+this.answerMathField.latex()+"$$";
};

MathEditor.prototype.addButtons = function(btns){
    this.default_toolbar_buttons = btns;
    setToolbar(this.default_toolbar_buttons,this.answerSpan,this.answerMathField,this.topElements,this.default_toolbar_tabs,this.tabEnabled);
};

MathEditor.prototype.removeButtons = function(btns){
    var default_toolbar_buttons = this.default_toolbar_buttons;
    btns.forEach(function(o){
        var index = default_toolbar_buttons.indexOf(o);
        if(index>=0)
            default_toolbar_buttons.splice(index, 1);
    });
    setToolbar(default_toolbar_buttons,this.answerSpan,this.answerMathField,this.topElements,this.default_toolbar_tabs,this.tabEnabled);
};

MathEditor.prototype.styleMe = function(options){
    jq(this.answerSpan).css('background',options.textarea_background).css('color',options.textarea_foreground).css('border-color',options.textarea_border).css('width',options.width).css('min-width',options.width).css('min-height',options.height).css('height',options.height);
    answerSpanWidth = jq(this.answerSpan).width();
    this.topElements.wrapper.css('width',parseInt(options.width)+10).css('min-width',parseInt(options.width)+10);
    this.topElements.toolbar.css('background',options.toolbar_background).css('color',options.toolbar_foreground).css('border-color',options.toolbar_border).css('min-width',options.width).css('width',parseInt(options.width));
    this.topElements.buttons.css('background',options.button_background).css('border-color',options.button_border);
};

MathEditor.prototype.setTemplate = function(name){
    if (name=='floating-toolbar'){
        editor_id = jq(this.answerSpan).attr('id')
        this.tabEnabled = false;
        this.template = 'floating-toolbar'
        setToolbar(this.default_toolbar_buttons,this.answerSpan,this.answerMathField,this.topElements,this.default_toolbar_tabs,this.tabEnabled);
        setCloseButton(this.topElements, this.answerSpan);
        jq(this.answerSpan).css('position','absolute');
        this.topElements.toolbar.css('position','absolute').css('min-width','315').css('width',jq(this.answerSpan).width()).css('z-index',999999);
        answerSpanHeight = jq(this.answerSpan).height();
        this.topElements.toolbar.css('margin-top',answerSpanHeight+13).hide();
        this.topElements.buttons.css('margin-right','5').css('margin-bottom','5');
        mathed_tmp[editor_id] = this.topElements
        jq(this.answerSpan).focusin(function(o){
            jq.each(mathed_tmp, function(k,v){
                v.toolbar.hide();
            });
            mathed_tmp[$(o.currentTarget).attr('id')].toolbar.show();
        });
    }else{
        console.warn("MathEditor: "+name+" is an invalid template name");
    }
};

MathEditor.prototype.noKeyboard = function(){
    editor_id = jq(this.answerSpan).attr('id');
    jq(this.answerSpan).find('textarea').removeAttr('readonly')
    jq('#keys-'+editor_id).remove();
}

setVirtualKeyboard = function(top_elements,answer_span,field,keyboard_type,count){
    editor_id = jq(answer_span).attr('id')
    jq('#keys-'+editor_id).remove();
    html = keyboardButtons(keyboard_type,editor_id); 
    jq(html).insertAfter(answer_span);
    jq(answer_span).find('textarea').attr('readonly','readonly');
    jq(answer_span).focusin(function(o){
        jq("[id^=keys-]").find('a').css('width',(jq(window).width()/count) - 5);
        jq("[id^=keys-]").find('.too_long').css('width',(jq(window).width()/count)*5 - 8);
        jq("[id^=keys-]").find('.long').css('width',(jq(window).width()/count)*3/2 - 5);
        jq("[id^=keys-]").find('.long1').css('width',(jq(window).width()/count)*2 - 6);
        jq('#keys-'+jq(this).attr('id')).slideDown();
    });
    jq("[id^=keys-]").find('a').css('width',(jq(window).width()/count) - 5);
    jq("[id^=keys-]").find('.too_long').css('width',(jq(window).width()/count)*5 - 8);
    jq("[id^=keys-]").find('.long').css('width',(jq(window).width()/count)*3/2 - 5);
    jq("[id^=keys-]").find('.long1').css('width',(jq(window).width()/count)*2 - 6);
    keyboardAction(top_elements,answer_span,field);
}

keyboardButtons = function(type,editor_id){
    var html = "<div id='keys-"+editor_id+"'><div class='keyboard-"+type+"-"+editor_id+"'>";
    keyboard_keys[type].forEach(function(obj){
        html+="<a data-type='"+obj.type+"' data-value='"+obj.value+"' class='"+obj.class+"'>"+obj.display+"</a>";
        if(obj.new_line){html+="<br/>"};
    });
    html+="</div></div>"
    return html;
}

keyboardAction = function(top_elements,answer_span,field){
    caps = false;
    editor_id = jq(answer_span).attr('id')
    jq('#keys-'+editor_id).find('a').on('click', function(o){
        editor_id = jq(this).parent().parent().attr('id').split('-')[1];
        type = jq(this).data('type');
        value = jq(this).data('value');
        if(type=='keystroke'){
            field.keystroke(value);
            field.focus();
        }else if(type=='write'){
            if(typeof value == 'string'){if(caps){value = value.toUpperCase()}else{value = value.toLowerCase()}}
            field.write(value);
            field.focus();
		}else if(type=='cmd'){
			field.cmd(value);
			field.focus();
        }else if(type=='custom'){
            if(value=='CapsLock'){
                caps = !caps;
                if (caps){
                    jq(a).css('background','#bbbbbb');
                    jq(a).css('color','#428bca');
                    jq('.ks').css('text-transform','uppercase')
                }else{
                    jq(a).css('background','#f5f5f5');
                    jq(a).css('color','#000000');
                    jq('.ks').css('text-transform','lowercase');
                }
                field.focus();
            }else if(value=='numpad'){
                jq('.keyboard-letters-'+editor_id).remove();
                setVirtualKeyboard(top_elements,answer_span,field,"numbers",6);
                jq('.keyboard-numbers-'+editor_id).parent().show();
            }else if(value=='letters'){
                jq('.keyboard-numbers-'+editor_id).remove();
                setVirtualKeyboard(top_elements,answer_span,field,"letters",10);
                jq('.keyboard-letters-'+editor_id).parent().show();
            }else if(value=='close'){
                jq(a).parent().parent().slideUp();
            }
        }
    });
}

resizeFunction = function() {
	basicStyling(this.answerSpan, this.answerMathField);
    setVirtualKeyboard(this.topElements, this.answerSpan, this.answerMathField, "numbers",6);
};

setCloseButton = function(top_elements, answer_span){
    editor_id = jq(answer_span).attr('id')
    btnhtml = "<div class='close-btn'><span id='close-btn-"+editor_id+"'>X</span></div>"
    jq(btnhtml).insertBefore(top_elements.wrapper.find('.matheditor-btn-span:first'));
    jq('#close-btn-'+editor_id).on('click',function(e){
        top_elements.toolbar.hide();
    });
}

setToolbar = function(btns,answer_span,answer_math_field,top_elements,tabs,tabEnabled,isMobile){
    if (answer_span && top_elements.toolbar){
        jq(answer_span).unwrap();
        top_elements.toolbar.remove();
    }
    required_buttons = getUniq(btns);
    required_tabs = getUniq(tabs);
    editor_id = jq(answer_span).attr('id')
    wrapper_html = "<div class='matheditor-wrapper-"+editor_id+"'></div>";
    html = "<div class='matheditor-toolbar-"+editor_id+"'>";
    if(tabEnabled){
        html += "<ul class='tabs-"+editor_id+"'>";
        required_tabs.forEach(function(o,idx){
            if(idx==0){
                html += "<li class='tab-link current' data-wrapperid='"+editor_id+"' data-tab='tab-"+(idx+1).toString()+"-"+editor_id+"'>"+o+"</li>";
            }else{
                html += "<li class='tab-link' data-wrapperid='"+editor_id+"' data-tab='tab-"+(idx+1).toString()+"-"+editor_id+"'>"+o+"</li>";
            }
        });
        html += "</ul>";
        required_tabs.forEach(function(o,idx){
            if(idx==0){
                html += "<div id='tab-"+(idx+1).toString()+"-"+editor_id+"' class='tab-content-me current'>";
            }else{
                html += "<div id='tab-"+(idx+1).toString()+"-"+editor_id+"' class='tab-content-me'>";
            }
            required_buttons.forEach(function(b){
                if(button_meta[b].tab == idx+1){
                    if(button_meta[b]){
                        html+="<span class='matheditor-btn-span'><i title='"+b.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();}).replace('_',' ')+"' data-latex='"+button_meta[b].latex+"' data-moveto='"+button_meta[b].moveto+"' data-movefor='"+button_meta[b].movefor+"' id='matheditor-btn-"+b+"' class='op-btn'><span id='selectable-"+b+"-"+editor_id+"' class='op-btn-icon'>"+button_meta[b].icon+"</span></i></span>";
                    }else{
                        console.warn("MathEditor: '"+b+"' is an invalid button");
                    }
                }
            });
            html+="</div>"
        });

    }else{
        required_buttons.forEach(function(b){
            if(button_meta[b]){
                html+="<span class='matheditor-btn-span'><a title='"+b.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();}).replace('_',' ')+"' data-latex='"+button_meta[b].latex+"' data-moveto='"+button_meta[b].moveto+"' data-movefor='"+button_meta[b].movefor+"' id='matheditor-btn-"+b+"' class='op-btn'><span id='selectable-"+b+"-"+editor_id+"' class='op-btn-icon'>"+button_meta[b].icon+"</span></a></span>";
            }else{
                console.warn("MathEditor: '"+b+"' is an invalid button");
            }
        });

    }
    html+="</div>"
    jq(answer_span).wrap(wrapper_html);
    jq(html).insertAfter(answer_span);

    top_elements.wrapper = jq(answer_span.parentElement);
    top_elements.toolbar = jq(answer_span.parentElement.children[1]);
    top_elements.buttons = top_elements.toolbar.find('.op-btn');
    button_task(answer_math_field,top_elements);

    MQN = MathQuill.getInterface(2);
    required_buttons.forEach(function(b,idx){
        if(button_meta[b]){
            var problemSpan = document.getElementById('selectable-'+b+'-'+editor_id);
            MQN.StaticMath(problemSpan);
        }
    });
    jq('ul.tabs-'+editor_id+' li').click(function(){
        var tab_id = jq(this).attr('data-tab');
        var wraper_id = jq(this).attr('data-wrapperid');

        jq('ul.tabs-'+wraper_id+' li').removeClass('current');
        jq('.matheditor-toolbar-'+wraper_id+' .tab-content-me').removeClass('current');

        jq(this).addClass('current');
        jq("#"+tab_id).addClass('current');
    });
    if(isMobile){
        setVirtualKeyboard(top_elements,answer_span,answer_math_field,'numbers',6);
    }
    basicStyling(answer_span,top_elements);

};

button_task = function(field,top_elements){
    top_elements.buttons.on('click', function(o){
        latex = jq(this).data('latex');
        field.write(latex);
		field.focus();
		switch(latex){
			case '\\sin' :
			case '\\cos' :
			case '\\tan' :
			case '\\cot' :
			case '\\sec' :
			case '\\cosec' :
			case '\\log' :
			case '\\ln' :
			case '\\log_{}' :
				field.cmd('(');
				field.focus();
		}
        for(var i=1; i<=jq(this).data('movefor'); i++){
            field.keystroke(jq(this).data('moveto'));
        }
    });
};

getUniq = function(arr){
    var uniqueNames = [];
    jq.each(arr, function(i, el){
        if(jq.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
    });
    return uniqueNames;
};

removeFromArray = function(arr) {
    arr.forEach(function(o){
        var index = this.default_toolbar_buttons.indexOf(o);
        if(o>=0)
            this.default_toolbar_buttons.splice(index, 1);
    });
};

basicStyling = function(answer_span,top_elements){
    jq(answer_span).css('white-space', 'nowrap');
    jq(answer_span).css('overflow-x', 'auto');
    jq(answer_span).css('min-height', 40);
    jq(answer_span).css('width', jq(window).width() - 25);
    jq(answer_span).css('padding', 5);
    jq(answer_span).css('background', '#fbfafa');
    jq(answer_span).css('font-size', '24pt');
    jq('#math_input').css('height', jq(window).height() - jq('.matheditor-toolbar-math_input').height() - jq('#keys-math_input').height() - 40);
    jq('#math_input').css('max-height', jq(window).height() - jq('.matheditor-toolbar-math_input').height() - jq('#keys-math_input').height() - 40);
	answerSpanWidth = jq(answer_span).width();
    jq('.matheditor-toolbar-math_input').css('width', jq(window).width() - 25);
};
