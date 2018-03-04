app.factory('FriendService',function($http){
	var friendService={}
	var BASE_URL="http://localhost:8081/proj2middleware"
		
		
	friendService.getAllUsers=function(){
		return $http.get(BASE_URL + "/getAllUsers")
	}
	
	friendService.sendFriendRequest=function(username){
		return $http.put(BASE_URL + "/friendRequest/"+ username);
	}
	friendService.pendingRequests=function(){
		return $http.get(BASE_URL + "/pendingRequests")
	}
	
	friendService.updatependingrequest=function(fromusername,status){
		return $http.put(BASE_URL + "/updatependingrequest/"+fromusername+"/"+status)
	}
	
	friendService.getAllFriends=function(){
		return $http.get(BASE_URL +"/friendslist")
	}
	
	return friendService;
})