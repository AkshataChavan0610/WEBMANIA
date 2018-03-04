app.controller('FriendController',function($scope,$location,FriendService){
	
	
		
		FriendService.getAllUsers()
		.then(function(response){
			$scope.usersList=response.data
			var users=response.data
			console.log(users)
		},function(response){
			console.log(response.status)
		
		
		})
		
		$scope.friendRequest=function(username){
		FriendService.sendFriendRequest(username)
		.then(function(response){
			alert("Friend request has been sent to " + username)
			FriendService.getAllUsers()
			.then(function(response){
				$scope.usersList=response.data
				var users=response.data
				console.log(users)
			})
			$location.path("/getAllUsers")
		},function(response){
			console.log(response.status)
	
			
})
}

		FriendService.pendingRequests()
		.then(function(response){
			$scope.pendingLists=response.data;
		},function(response){
			console.log(response.status)
		})
		
		
		$scope.updatependingrequest=function(fromusername,status){
			FriendService.updatependingrequest(fromusername,status)
			.then(function(response){
				$scope.pendingLists=response.data;
				if(status=='A')
				alert('You have accepted the friend request')
				else
					alert('You have denied the friend request')
				$location.path('/pendingRequests')
			},function(response){
				console.log(response.status)
			})
		}
		$scope.friendslist=FriendService.getAllFriends()
		.then(function(response){
			$scope.friendslist=response.data
		},function(response){
			console.log(response.status)
		})
		
				
})
