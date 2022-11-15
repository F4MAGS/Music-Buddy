# Music Buddy

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
	Music Buddy is a social networking app designed to help users find and make friends based on their music taste!

### Description
	Music Buddy utilizes Spotify API to analyze user data and connect users based on similar music taste. 


### App Evaluation
- **Category: Social/Music**
- **Mobile: Android**
- **Story: Make friends through music**
- **Market: Social media users**
- **Habit: Match, and get to know more users with the same music taste**
- **Scope: Create an app which allows users to create new connections, chat with friends, and see what artists their friends are listening to**

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
* User must be able to login with Oauth Spotify
* User can see potential new matches in the main feed with the option to add or decline
* Have a friends page with people matched 
* User can see friends’s top 3 artist
* Have a Profile page showing more details about the user
* Have a chat list with displaying a list of friends
* Users are able to chat with friends they have matched with 
* Settings page where they can set their bio, approximate location, and age

**Optional Nice-to-have Stories**
* More detailed location setting
* Have notifications for new messages
* Matched friends are sorted by unmet first
* Add activity to see trending artists/songs and which friends are listening  
* Listen along with friends if they are listening to something currently
* User can set their own profile picture instead of their spotify one in their preferences
* User can log out
* Have Sync chat
* Open youtube videos inside chat


### 2. Screen Archetypes

* Login
   * User must be able to login with Oauth Spotify
* Home
   * User can see potential new matches in the main feed with the option to add or decline
* Friends
   * Have a friends page with people matched 
   * User can see friends’s top 3 artist
   * [Optional] Add activity to see trending artists/songs and which friends are listening
   * [Optional] Listen along with friends if they are listening to something currently
* Profile
   * Have a Profile page showing more details about the user
   * [Optional] User can log out
* Settings
   *  Settings page where they can set their bio, approximate location, and age
   * [Optional] More detailed location setting
   * [Optional] User can set their own profile picture instead of their spotify one in their preferences
* Chat list
   * Have a chat list with displaying a list of friends
   * [Optional] Matched friends are sorted by unmet first
* Chat Message
   * Users are able to chat with friends they have matched with 
   * [Optional] Have notifications for new messages
   * [Optional] Have Sync chat
   * [Optional] Open youtube videos inside chat

### 3. Navigation

**Tab Navigation** (Tab to Screen)
* Home
* Friends
* Chat List

** Flow Navigation** (Screen to Screen)
* Login
	* Preferences
	* Home
* Friends 
	* Profile
	* Chat Message
* Chat List
	* Chat Message
* Profile
	* Settings

## Wireframes
### Low Fidelity Wireframe
<img src="Lo-Fi.png" width=1000>

### High Fidelity Wireframe
<img src="Hi-Fi.png" width=1000>

### Interactive Prototype
<img src='MusicBuddyInteractive.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Schema 
### Models
#### User
  | Property          | Type     | Description |
   | -------------       | -------- | ------------|
   | userID            | int       | unique id for the user (default field and auto) |
   | username      | String  | name the user chooses from spotify or create in the app when first sign in|
   | userAge        | int        | user age |
   | location         | String   | the state where the user is located|
   | profilePic       | File      | user profile pic from spotify or uploaded himself/herself|
   | profileDescription  | String   | User bio/description |
   | invites           | User[] (array of userID)  | invites an user has |  
   | friends           | User[] (array of userID)  | friends an user has |  
   | createdAt      | DateTime     | date when User is created (default field) |
   | lastSwiped    | int               |  Highest userID seen |

#### userChat
| Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | int    | unique id for conversations (default increasing)|
   | userId1    | int| this is the id of the user that is going to send the message  |
   | userId2  | int| this is the id of the user that is going to get the message |
   | chat  | String| this is the actual content of the chat (for example 'hi, how are you') |
   |previousId	| int | we will need this to link the message to the previous message, so we will have an order when displaying the chat
   | createdAt    | DateTime | date when User is created (default field) |



### Networking
#### List of network requests by screen
   - Home
		- (Read/Get) Query all users user hasn’t matched with 
			 ```java
			ParseQuery<UserID> query = ParseQuery.getQuery("UserList");
			query.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<User> UserList, ParseException e) {
					if (e == null) {
						Log.d("score", "Retrieved " + userList.size() + " users");
					} else {
						Log.d("score", "Error: " + e.getMessage());
					}
				}
			});

			```
		- (Create/Post) Add userID in User invite array
			 ```java
				Code here
			```
		- (Create/Post) Remove userID in User invite array
			 ```java
				Code here
			```
		- (Create/Post) Add userID in User friends array
			 ```java
				Code here
			```
									
   - Settings
		- (Read/Get) Create picture for profile
			 ```java
			let query = PFQuery(className:"username")
			query.whereKey("id", equalTo: currentUser.id())
			query.findObjectsInBackground { (user: [profilePic]?, error: Error?) in
				if let error = error { 
					print(error.localizedDescription)
				} else if let user =user {
					print("Successfully retrieved \user.")
				}
			}
			```
		- (Create/POST) Create description for profile
			 ```java
			ParseObject profileDescription   = new ParseObject("profileDescription ");
			profileDescription.put("content", "myBio.");
			profileDescription .saveInBackground();
			 ```
   - Profile
		- (Read/Get) Query user description
			 ```java
			val query = ParseQuery<ParseObject>("User")
			query.whereMatches(currentUser.id)
			query.findInBackground {description, e: ParseException? ->
				if (e == null) {
					Log.d(Companion.TAG, "Objects: description")
				} else {
					og.e(Companion.TAG, "Parse Error: ", e)
				}
			}
			```
		- (Read/Get) Create picture for profile
			 ```java
				let query = PFQuery(className:"user")
				query.whereKey("id", equalTo: currentUser.id())
				query.findObjectsInBackground { (user: [profilePic]?, error: Error?) in
				if let error = error { 
					print(error.localizedDescription)
				}	else if let user =user {
					print("Successfully retrieved \user.")
				}
			}
			```
		- (Read/Get) Query username for profile
			 ```java
			val query = ParseQuery<ParseObject>("Profile")
			query.selectKeys(java.util.List.of("userID"))
			query.findInBackground { objects: List<ParseObject>, e: ParseException? ->
				if (e == null) {
					Log.d(Companion.TAG, "Objects: $objects")
					Log.d(Companion.TAG, "Object name: " + objects[0]["name"])
				} else {
					Log.e(Companion.TAG, "Parse Error: ", e)
				}
			}
			```
   - Friends
		- (Read/Get) List of users friends
			 ```java
			val query = ParseUser.getQuery()
			query.findInBackground {friends, e ->
				if (e == null) {
					// The query was successful.
				} else {
					// Something went wrong.
				}
			}
			```
   - Chat List
		- (Read/Get) List of users friends
			 ```java
			val query = ParseUser.getQuery()
			query.findInBackground {friends, e ->
				if (e == null) {
					// The query was successful.
				} else {
					// Something went wrong.
				}
			}
			```
		- (Read/Get) Query username for friend
			 ```java
			let query = PFQuery(className:"user")
			query.whereKey("id", equalTo:friend.id())
			query.findObjectsInBackground { (user: [username]?, error: Error?) in
				if let error = error { 
					print(error.localizedDescription)
				} else if let user =user {
					print("Successfully retrieved \user.")
					// TODO: Do something.
				}
			}
			```
		- (Read/Get) Query pic for friend
			 ```java
			let query = PFQuery(className:"user")
			query.whereKey("id", equalTo:friend.id())
			query.findObjectsInBackground { (user: [username]?, error: Error?) in
				if let error = error { 
					print(error.localizedDescription)
				} else if let user =user {
					print("Successfully retrieved \user.")
					// TODO: Do something.
				}
			}
			```
   - Chat Message
		- (Read/Get) Query messages between two userID
			 ```java
			let query = PFQuery(className:"userChat")
			query.whereKey("userid1", equalTo: currentUser.id || userid2.id) &&
			query.whereKey("userid1", equalTo: currentUser.id || userid2.id)
			query.order(byDescending: "previousId")
			query.findObjectsInBackground { (userChat: [id]?, error: Error?) in
				if let error = error { 
					print(error.localizedDescription)
				} else if let userChat =userChat {
					print("Successfully retrieved \userChat.")
					// TODO: Do something.
				}
			}
			```
		- (Read/Get) Query username for user
			 ```java
			let query = PFQuery(className:"user")
			query.whereKey("id", equalTo:user.id())
			query.findObjectsInBackground { (user: [username]?, error: Error?) in
				if let error = error { 
					print(error.localizedDescription)
				} else if let user =user {
					print("Successfully retrieved \user.")
					// TODO: Do something.
				}
			}
			```
		- (Read/Get) Query profile for user
			 ```java
			let query = PFQuery(className:"user")
			query.whereKey("id", equalTo: userId2.id())
			query.findObjectsInBackground { (user: [profilePic]?, error: Error?) in
				if let error = error { 
					print(error.localizedDescription)
				} else if let user =user {
					print("Successfully retrieved user.")
				}
			}

			```

#### API Endpoints
##### Spotify API
- Base URL - [https://api.spotify.com/v1](https://api.spotify.com/v1)

   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | /me/top/type | Get User's Top Items
    `GET`    | /users/user_id  | Get User's Profile
    `GET`    | /me  | Get Current User's Profile
    `GET`    | me/player | Get Playback State
    `GET`    | me/following | Get Followed Artists
    `GET`    | me/following/contains | Check If User Follows Artists or Users
