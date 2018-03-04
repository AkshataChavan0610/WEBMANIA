/**
 * Angular Js Module
 */
var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/register',{
		templateUrl:'views/registrationform.html',
		controller:'UserController'
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserController'
	})
	.when('/edituserprofile',{
		templateUrl:'views/userprofile.html',
		controller:'UserController'
	})
	.when('/addjob',{//Data is from jobform.html to Controller 
		templateUrl:'views/jobform.html',
		controller:'JobController'
	})
	.when('/alljobs',{ // from Controller to View [$scope.persons=[]]
		templateUrl:'views/jobslist.html', 
		controller:'JobController'
	})
	.when('/addblog',{
		templateUrl:'views/blogform.html', //V to Controller
		controller:'BlogPostController'
	})
	.when('/getblogs',{
		templateUrl:'views/blogslist.html',//Controller to V
		controller:'BlogPostController'
	})
	.when('/admin/getblog/:id',{
		templateUrl:'views/approvalform.html',
		controller:'BlogPostDetailsController'
	})
	.when('/getblog/:id',{
		templateUrl:'views/blogdetails.html',
		controller:'BlogPostDetailsController'
	})
	
	.when('/friendslist',{
		templateUrl:'views/friendlist.html',//Controller to V
		controller:'FriendController'
	})
	.when('/pendingRequests',{
		templateUrl:'views/pendingRequests.html',//Controller to V
		controller:'FriendController'
	})
	.when('/getAllUsers',{
		templateUrl:'views/userslist.html',//Controller to V
		controller:'FriendController'
	})
	
	.when('/chat',{
		templateUrl:'views/chat.html',
		controller:'ChatCtrl'
	})
	.otherwise({templateUrl:'views/home.html'})
})
app.run(function($rootScope,$cookieStore,UserService,$location){
	/*alert($cookieStore.get('currentUser'))*/
	if($rootScope.currentUser==undefined)
		$rootScope.currentUser=$cookieStore.get('currentUser')
		
	$rootScope.logout=function(){
		/*
		 * Call middleware logout url -> Middleware - remove HttpSession attribute,update online status to false
		 * on success - in frontend , remove cookieStore attribute currentUser, delete $rootScope.
		 */
		UserService.logout().then(function(response){
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser')
			$location.path('/login')
			
		},function(response){
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser')
			console.log(response.status)
			$location.path('/login')
		})
	}
})
