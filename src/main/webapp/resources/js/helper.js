function handleDialogSubmit(xhr, status, args,updatePersonDlg) {
    if (args.error||args.validationFailed) {
    	updatePersonDlg.show();
    } else {
    	updatePersonDlg.hide();
    }
}
function handleAjaxSubmitAndShowDlg(xhr, status, args,dlg) {
    if (args.error||args.validationFailed) {
    	dlg.hide();
    	return false;
    } else {
    	dlg.show();
    	return true;
    }
}
function start() {
    PF('statusDialog').show();
}
function stop() {
	PF('statusDialog').hide();
}


function monthSelect(){
	console.log(args);
}