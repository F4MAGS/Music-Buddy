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
[This section will be completed in Unit 9]
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
   | invites           | User[] (array of users)  | friends an user has |  
   | friends           | User[] (array of users)  | friends an user has |  
   | createdAt      | DateTime     | date when User is created (default field) |
   | lastSwiped    | int               |  Highest userID seen |


User has swiped 3 users 
#### listUsers
| Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | int    | unique id for all users in this table (default field and auto) |
   | userId    | int| user id that it is in the User table |

#### userChat
| Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | int    | unique id for conversations (default increasing)|
   | userId1    | int| this is the id of the user that is going to send the message  |
   | userId2  | int| this is the id of the user that is going to get the message |
   | chat  | String| this is the actual content of the chat (for example 'hi, how are you') |
   |previousId| int | we will need this to link the message to the previous message, so we will have an order when displaying the chat
   | createdAt     | DateTime | date when User is created (default field) |

   


### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
