//Want List Editor namespace
var Want = {};

Want.editedButton = null;
Want.editPanel = null;
Want.entriesList = null;
Want.sortableList = null;

Want.templateResult = function (item) {
    if (item.loading) return item.text;

    return $('<div/>').attr({class: 'media'})
        .append($('<div/>').attr({class: 'media-left'})
            .append($('<a/>').attr({href: '#'})
                .append($('<img/>').attr({src: item.imgUrl, width: 100}))))
        .append($('<div/>').attr({class: 'media-body'})
            .append($('<h3/>').attr({class: 'media-heading'}).text(item.title)))
};

$('#selector').select2({
    ajax: {
        url: "/searchOnTradeList",
        dataType: 'json',
        delay: 250,
        data: function (param) {
            var tit = param.term;
            if (!tit)
                tit = '';

            return {
                title: (param.term ? param.term : '')
            };
        },
        processResults: function (data) {
            return {
                results: data
            };
        },
        cache: true
    },
    templateResult: Want.templateResult,
    templateSelection: function (item) {
        return item.title;
    },
    theme: "bootstrap"
});


Want.enableSorting = function () {
    Want.sortableList = Sortable.create($(Want.entriesList)[0], {
        ghostClass: 'ghost',
        animation: 150
    });
};

Want.addGameToList = function(data){
    if (data)
        $(Want.entriesList)
            .append('<li id="' + data.id + '" data-id="' + data.id + '" class="list-group-item">' + data.title + '<span class="badge" onclick="removeWantItem(' + data.id + ')">X</span></li>');
};

Want.addGame = function () {
    $.ajax({
        url: '/wantList/items',
        dataType: 'json',
        method: "GET",
        data: {
            id: $('#selector').val()
        },
        success: Want.addGameToList
    });
};

Want.save = function () {
    $.ajax({
            url: '/wantList/entries',
            dataType: 'json',
            method: "PUT",
            data: {
                'itemId': $(Want.editedButton).attr('id'),
                'wantIds': Want.sortableList.toArray()
            },
            success: function (data, status) {
                location.reload();
            },
            error: function (data, status, error) {
                alert(status + ' - ' + error);
            }
        }
    );
};

Want.edit = function (button) {
    if (Want.editedButton == button)
        Want.stopEdit();
    else
        Want.startEdit(button);
};

Want.startEdit = function (button) {
    if (Want.editedButton != null)
        $(Want.editedButton).removeClass('active');

    $(button).addClass('active');
    Want.editPanel.collapse('show');
    $('html, body').animate({
            scrollTop: Want.editPanel.offset().top
        },
        500);
    Want.editedButton = button;

    Want.updateEditList(Want.editedButton.id);
};

Want.stopEdit = function () {
    $(Want.editedButton).removeClass('active');
    $('html, body').animate({
            scrollTop: 0
        },
        500);
    Want.editPanel.collapse('hide');
    Want.editedButton = null;
};

Want.onGetEntries = function (data) {
    if(data == null)
        return;

    $(Want.entriesList).empty();
    for (var i = 0; i < data.length; i++) {
        $(Want.entriesList)
            .append('<li id="' + data[i].id + '" data-id="' + data[i].id + '" class="list-group-item">' + data[i].title + '<span class="badge" onclick="Want.removeWantItem(' + data[i].id + ')">X</span></li>');
    }
}

Want.updateEditList = function (id) {
    $.ajax({
            url: '/wantList/entries',
            dataType: 'json',
            method: "GET",
            data: {
                'id': id
            },
            success: function (data) {
                Want.onGetEntries(data);
            },
            error: function (data, status, error) {
                alert(status + ' - ' + error);
            }
        }
    );
};

Want.removeWantItem = function (wantId) {
    $(Want.entriesList).children("#" + wantId).remove();
};

Want.showTMtext = function () {
    $.ajax({
            url: '/wantList/tradeMaximizer',
            dataType: 'text',
            method: "GET",
            success: function (data) {
                alert(data);
            },
            error: function (data, status, error) {
                alert(status + '  -  ' + data);
            }
        }
    );
};