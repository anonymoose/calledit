$(document).ready(function() {
    // If there is a date picker, then set it up.
    if ($(".datepicker").datepicker) {
        $(".datepicker").datepicker({format: 'yyyy-mm-dd'});
    }


    // validate search on index.html
    if ($('#find').length) {
        $('#find').validate({
            errorLabelContainer: '#errors', 
            rules: {
                email: {
                    required: true,
                    email: true
                }
            }
        });
    }

    // validate edit form on call.edit.html
    if ($('#edit').length) {
        $('#edit').validate({
            errorLabelContainer: '#errors', 
            rules: {
                email: {
                    required: true,
                    email: true
                },
                prediction: {
                    required: true
                }
            },
            messages: {
                email: "You sure that email is valid?",
                prediction: "You have to predict something.  Otherwise what's the point?",
            }
        });
    }
});


