/**
 * UserService
 */
app.factory('UserService',function($http){
	var BASE_URL="http://localhost:8081/proj2middleware"
	
	var userService={}
	
	userService.registerUser=function(user){
		
		console.log(user)
		return $http.post(BASE_URL + "/registeruser",user)//4
		//8
	}
	
	userService.login=function(user){
		return $http.post(BASE_URL + "/login",user)
	}
	
	userService.logout=function(){
		
		return $http.get(BASE_URL + "/logout")
	}
	
	userService.getUserByUsername=function(){
		return $http.get(BASE_URL + "/getuserbyusername")
	}
	
	userService.updateUser=function(){
		return $http.put(BASE_URL + "/updateuser")
	}
	
	return userService;
})