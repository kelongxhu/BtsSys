jQuery(function() {


    $( ".box-title .icon.chevron" ).click(function() {
        $( this ).toggleClass( "icon-chevron-up" ).toggleClass( "icon-chevron-down" );
        $( this ).parents( ".box:first" ).find( ".box-content" ).toggle('fast');
    });

});