$('#create-new-list-command').click(function () {
    $.post("/admin/newList.command", function () {
        location.reload();
    });
});

$('#open-list-command').click(function () {
    $.post("/admin/openList.command", function () {
        location.reload();
    })
});

$('#close-list-command').click(function () {
    $.post("/admin/closeList.command", function () {
        location.reload();
    });
});