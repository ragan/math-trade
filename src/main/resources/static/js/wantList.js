//Want List Editor namespace
var Want = {};

Want.editedButton = null;
Want.editPanel = null;
Want.entriesList = null;

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

Want.templateResult = function (item) {
    if (item.loading) return item.text;

    return $('<div/>').attr({class: 'media'})
        .append($('<div/>').attr({class: 'media-left'})
            .append($('<a/>').attr({href: '#'})
                .append($('<img/>').attr({src: item.imgUrl, width: 100}))))
        .append($('<div/>').attr({class: 'media-body'})
            .append($('<h3/>').attr({class: 'media-heading'}).text(item.title)))
};

Want.sort = function () {
    Sortable.create($('#ourList')[0], {
        ghostClass: 'ghost',
        animation: 150
    });
};

Want.addGame = function (success) {
    $.ajax({
        url: '/wantList/findItemById',
        dataType: 'json',
        data: {
            id: $('#selector').val()
        },
        success: success
    });
};
/*
 to było na success
 function (data) {
 if (data)
 $('#ourList').append('<li data-id="' + data.wantTradeItemId + '" class="list-group-item">' + data.wantTradeItemTitle + '</li>');
 }
 */

Want.save = function (id, wantIds) {
    $.ajax({
            url: '/wantList/saveList.command',
            dataType: 'json',
            method: "POST",
            data: {
                'itemId': id, //$(editedButton).attr('id'),
                'wantIds': wantIds
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

Want.updateEditList = function (id) {
    $.ajax({
        url: '/wantList/entries',
        dataType: 'json',
        method: "GET",
        data: {
            'id': id
        },
        success: function (data) {
            $(Want.entriesList).empty();
            for (var i = 0; i < data.length; i++) {
                $('#ourList').append('<li id="' + data[i].wantTradeItemId + '" data-id="' + data[i].wantTradeItemId + '" class="list-group-item">' + data[i].wantTradeItemTitle + '<span class="badge" onclick="removeWantItem(' + data[i].wantTradeItemId + ')">X</span></li>');
            }
        },
        error: function (data, status, error) {
            alert(status + '  -  ' + data);
        }
    });
};

Want.removeWantItem = function (wantId) {
    $('#ourList').children("#" + wantId).remove();
};

Want.showTMtext = function (csrf) {
    $.ajax({
            url: '/wantList/getListTM.command',
            dataType: 'text',
            method: "POST",
            data: {
                'csrf': csrf
            },
            success: function (data) {
                alert(data);
            },
            error: function (data, status, error) {
                alert(status + '  -  ' + data);
            }
        }
    );
};