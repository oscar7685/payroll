"use strict";
var dev_app_settings = {
    dev_page_loader: false// use false to disablle page loader    
};
if ($(".datepicker").length > 0)
    $(".datepicker").datetimepicker({format: "DD/MM/YYYY"});
// Start Smart Wizard
var smartWizard = {
    init: function () {

        if ($(".wizard").length > 0) {

//check count of steps in each wizard
            $(".wizard > ul").each(function () {
                $(this).addClass("steps_" + $(this).children("li").length);
            }); // ./end            

            // this is demo, can be removed if wizard is not using
            if ($("#wizard-validation").length > 0) {

                var validate = $("#wizard-validation").validate({
                    errorClass: "has-error",
                    validClass: "has-success",
                    errorElement: "span",
                    ignore: [],
                    errorPlacement: function (error, element) {
                        $(element).after(error);
                        $(element).parents(".form-group").addClass("has-error");
                    },
                    highlight: function (element, errorClass) {
                        $(element).parents(".form-group").removeClass("has-success").addClass(errorClass);
                        //dev_layout_alpha_content.init(dev_layout_alpha_settings);
                    },
                    unhighlight: function (element, errorClass, validClass) {
                        $(element).parents(".form-group").removeClass(errorClass).addClass(validClass);
                        // dev_layout_alpha_content.init(dev_layout_alpha_settings);
                    },
                    rules: {
                        cedula: {required: true},
                        fnacimiento: {required: true, date: true},
                        numerocuenta: {required: true},
                        banco: {required: true}
                    }
                });
            }// ./end of demo


// init wizard plugin
            $(".wizard").smartWizard({
// This part (using for wizard validation) of code can be removed FROM 
                onLeaveStep: function (obj) {
                    var wizard = obj.parents(".wizard");
                    if (wizard.hasClass("wizard-validation")) {

                        var valid = true;
                        $('input,textarea', $(obj.attr("href"))).each(function (i, v) {
                            valid = validate.element(v) && valid;
                        });
                        if (!valid) {
                            wizard.find(".stepContainer").removeAttr("style");
                            validate.focusInvalid();
                            return false;
                        }
                    }

// dev_layout_alpha_content.init(dev_layout_alpha_settings);

                    return true;
                }, // <-- TO
                //this is important part of wizard init
                onShowStep: function (obj) {
                    var wizard = obj.parents(".wizard");
                    if (wizard.hasClass("show-submit")) {

                        var step_num = obj.attr('rel');
                        var step_max = obj.parents(".anchor").find("li").length;
                        if (step_num == step_max) {
                            obj.parents(".wizard").find(".actionBar .btn-primary").css("display", "block");
                        }
                    }

                    // dev_layout_alpha_content.init(dev_layout_alpha_settings);

                    return true;
                }, // ./end

                onFinish: function onFinishCallback(objs, context) {
                    var isValid = true;
                    var ced = $('#cedula').val();
                    var fn = $('#fnacimiento').val();
                    var nc = $('#numerocuenta').val();
                    var ban = $('#banco').val();
                    if (!ced && ced.length <= 0) {
                        isValid = false;
                    }
                    if (!fn && fn.length <= 0) {
                        isValid = false;
                    }
                    if (!nc && nc.length <= 0) {
                        isValid = false;
                    }
                    if (!ban && ban.length <= 0) {
                        isValid = false;
                    }

                    if (isValid) {
                        $.ajax({
                            url: '/payroll/Validar',
                            data: $("#wizard-validation").serialize(),
                            type: 'post',
                            success: function (msg) {
                                if (msg !== "0") {
                                    $("#wizard-validation").submit();
                                } else {
                                    $('#myModal').modal('show');
                                }

                            }
                        });

                    }

                }

            }
            );
            /*
             $(".modal").on('show.bs.modal', function (e) {
             $(this).find(".wizard").smartWizard("fixHeight");                
             });*/
        }

    }
}; //./start smart wizard


$(function () {

    smartWizard.init();
});