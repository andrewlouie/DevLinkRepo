     $(function() { 
        $(".aabutton").click(function(event) {
        if (this.value == "Up") id = $(this).prev('input[type=hidden]').val();
        else id = $(this).prevAll('input[type=hidden]').val()
        var $self = $(this).siblings('.aascore');
        $.ajax({
        dataType: "json",
        method: "POST",
        url: "/ajaxvote",
        data: { linkid: id, vote: this.value }
        })
        .done(function( msg ) {
            $self.html(msg.score);
        });
        event.preventDefault();
        });
    });