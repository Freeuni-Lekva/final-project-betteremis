function makeHTMLElement(tag, classes, text, attributes) {
    var d = document.createElement(tag);
    $(d).addClass(classes);
    $(d).append(text);
    for(var prop in attributes) {
        $(d).attr(prop, attributes[prop]);
    }
    return d;
}

function buttonClicked(button){
    if(parseInt(button.id) % 2 == 1){
        document.getElementById(button.id).style.display = 'none';
        document.getElementById((parseInt(button.id) - 1).toString()).style.display='none';
    }else{
        document.getElementById(button.id).style.display = 'none';
        document.getElementById((parseInt(button.id) + 1).toString()).style.display='none';
    }
}