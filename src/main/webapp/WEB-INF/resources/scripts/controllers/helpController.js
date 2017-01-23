var adminApp=angular.module('sbAdminApp',[]);

adminApp.controller('HelpController',function($rootScope,$scope,$http){
	
	$scope.helpContents=[{
			"question":"Q1: How about changing my password?",
			"answer":"To change your CRO account password, sign in and then click the arrow next to your username (in the top corner) Next choose change password.<br/><br/> While changing the password need to provide a current password of account, new password and reenter the new password. Finally Click save, to change the password.<br/><br/> From next time onwards you have to provide a new password to login.",
		},{
			"question":"Q2: How I can report issues?",
			"answer":"Reports from customers on CRO help us identify and fix problems when something's not working. To report a problem: <br/><br/>1. Click report issue on left side menu. <br/><br/>Giving more detail Feature,Title and Description <b>(ex: adding a screenshot and description)</b> helps us find the problem.<br/><br/> Reporting issues when they happen helps make CRO better, and we appreciate the time it takes to give us this information.",
		},{
			"question":"Q3: How can I change my subscription settings?",
			"answer":"Your settings let you control, ease of records search and export. <br/><br/>You can view or change your account settings in the Settings page. To change a settings:<br/><br/> 1. Click settings on left side menu Under settings, <br/><br/>we have two types. 1. Lookup preference 2. Export Preference <br/><br/><b>1. Lookup preference</b> <br/><br/>It's used for records search <br/>- Default is subscribed counties <br/>- For preferred counties select counties and save. <br/><br/> <b>2. Export Preference</b> <br/><br/>It's used for export excel <br/>- Default is standard fields <br/>- For custom fields drag and drop fields and save.",
		}];

	if($rootScope.isAdmin==2||$rootScope.isAdmin==3){
		$scope.helpContents=[{
			"question":"Q1: How about changing my password?",
			"answer":"To change your CRO account password, sign in and then click the arrow next to your username (in the top corner) Next choose change password.<br/><br/> While changing the password need to provide a current password of account, new password and reenter the new password. Finally Click save, to change the password.<br/><br/> From next time onwards you have to provide a new password to login.",
		},{
			"question":"Q2: How I can report issues?",
			"answer":"Reports from customers on CRO help us identify and fix problems when something's not working. To report a problem: <br/><br/>1. Click report issue on left side menu. <br/><br/>Giving more detail Feature,Title and Description <b>(ex: adding a screenshot and description)</b> helps us find the problem.<br/><br/> Reporting issues when they happen helps make CRO better, and we appreciate the time it takes to give us this information.",
		},{
			"question":"Q3: How can I change my subscription settings?",
			"answer":"Your settings let you control, ease of records search and export. <br/><br/>You can view or change your account settings in the Settings page. To change a settings:<br/><br/> 1. Click settings on left side menu Under settings, <br/><br/>we have two types. 1. Lookup preference 2. Export Preference <br/><br/><b>1. Lookup preference</b> <br/><br/>It's used for records search <br/>- Default is subscribed counties <br/>- For preferred counties select counties and save. <br/><br/> <b>2. Export Preference</b> <br/><br/>It's used for export excel <br/>- Default is standard fields <br/>- For custom fields drag and drop fields and save.",
		}];
	}else if($rootScope.isAdmin==4||$rootScope.isAdmin==5){
		$scope.helpContents=[{
			"question":"Q1: How about changing my password?",
			"answer":"To change your CRO account password, sign in and then click the arrow next to your username (in the top corner) Next choose change password.<br/><br/> While changing the password need to provide a current password of account, new password and reenter the new password. Finally Click save, to change the password.<br/><br/> From next time onwards you have to provide a new password to login.",
		},{
			"question":"Q2: How I can report issues?",
			"answer":"Reports from customers on CRO help us identify and fix problems when something's not working. To report a problem: <br/><br/>1. Click report issue on left side menu. <br/><br/>Giving more detail Feature,Title and Description <b>(ex: adding a screenshot and description)</b> helps us find the problem.<br/><br/> Reporting issues when they happen helps make CRO better, and we appreciate the time it takes to give us this information.",
		},{
			"question":"Q3: How can I change my subscription settings?",
			"answer":"Your settings let you control, ease of records search and export. <br/><br/>You can view or change your account settings by <b>contact admin</b>.",
		}];
	}
	 
});

//html filter (render text as html)
adminApp.filter('html', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    };
}]);